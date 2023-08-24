/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.wechat.sdk.core.resolver;

import com.photowey.wechat.sdk.core.thrower.AssertionErrorThrower;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code PlaceholderResolver}
 *
 * @author photowey
 * @date 2023/08/24
 * @since 1.0.0
 */
public final class PlaceholderResolver {

    private PlaceholderResolver() {
        // utility class; can't create
        AssertionErrorThrower.throwz(PlaceholderResolver.class);
    }

    private static final List<Character> PREFIX_TOKENS = Arrays.asList('$', '{');
    private static final int DOLLAR_TOKEN = '$' & 0xFF;
    private static final int PREFIX = '{' & 0xFF;
    private static final int SUFFIX = '}' & 0xFF;

    // ----------------------------------------------------------------

    /**
     * 解析 {@code {{xxx}}} 表达式
     *
     * @param candidate 待解析文本
     * @param context   参数上下文
     * @return 目标文本
     */
    public static String resolve(String candidate, Map<String, Object> context) {
        List<String> expressions = parseExpress(candidate);
        for (String expression : expressions) {
            String target = eval(expression, context);
            if (isNotBlank(target)) {
                candidate = resolve(candidate, expression, target);
                return resolve(candidate, context);
            }
        }

        return candidate;
    }

    // ---------------------------------------------------------------- $ resolve

    /**
     * 解析 {@code ${xxx}} 表达式
     *
     * @param candidate 待解析文本
     * @param context   参数上下文
     * @return 目标文本
     */
    public static String $resolve(String candidate, Map<String, Object> context) {
        List<String> expressions = parse$Express(candidate);
        for (String expression : expressions) {
            String target = eval(expression, context);
            if (isNotBlank(target)) {
                candidate = resolve(candidate, expression, target);
                return $resolve(candidate, context);
            }
        }

        return candidate;
    }

    // ----------------------------------------------------------------

    /**
     * {@code {{xxx}}} 抽取
     *
     * @param candidate 待抽取文本
     * @return 表达式列表
     */
    private static List<String> parseExpress(String candidate) {
        char[] chars = candidate.toCharArray();
        int startDeep = 0;
        int endDeep = 0;
        StringBuilder buf = new StringBuilder();
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            int prev = 0;
            int tail = 0;
            int token = chars[i] & 0xFF;
            if (i > 0) {
                prev = chars[i - 1] & 0xFF;
            }
            if (i <= chars.length - 1 - 1) {
                tail = chars[i + 1] & 0xFF;
            }

            // 单周期内: 首次出现 "{{" 清空缓存
            boolean cleanBuf = (token == PREFIX && tail == PREFIX);
            if (cleanBuf) {
                startDeep = 0;
                endDeep = 0;
                buf = new StringBuilder();
            }

            boolean isPrefix = (token == PREFIX && tail == PREFIX) || (token == PREFIX && prev == PREFIX);
            if (isPrefix) {
                startDeep++;
                continue;
            } else {
                if (startDeep == 2 && token != SUFFIX) {
                    buf.append(chars[i]);
                }
            }

            boolean isSuffix = (token == SUFFIX && tail == SUFFIX) || (token == SUFFIX && prev == SUFFIX);
            if (isSuffix) {
                endDeep++;
                if (endDeep == 2 && !buf.toString().isEmpty()) {
                    keys.add(buf.toString());
                    // 清空: 为下一个周期准备
                    startDeep = 0;
                    endDeep = 0;
                    buf = new StringBuilder();
                }
            }
        }

        return keys;
    }

    /**
     * {@code ${name}} 抽取
     *
     * @param candidate 待抽取文本
     * @return 表达式列表
     */
    private static List<String> parse$Express(String candidate) {
        char[] chars = candidate.toCharArray();
        int step = 0;
        StringBuilder buf = new StringBuilder();
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            int prev = 0;
            int tail = 0;
            int token = chars[i] & 0xFF;
            if (i > 0) {
                prev = chars[i - 1] & 0xFF;
            }
            if (i < chars.length - 1 - 1) {
                tail = chars[i + 1] & 0xFF;
            }
            // $->{
            boolean bracketToken = (token == DOLLAR_TOKEN && tail == PREFIX);
            // 单周期内: 首次出现 "${" 清空缓存
            // $<-{
            boolean isPrefix = (token == PREFIX && prev == DOLLAR_TOKEN);
            if (isPrefix) {
                buf = new StringBuilder();
            }
            boolean doClean = bracketToken || isPrefix;
            if (doClean) {
                step++;
                continue;
            } else {
                if (step == PREFIX_TOKENS.size() && token != SUFFIX) {
                    buf.append(chars[i]);
                }
            }
            if (token == SUFFIX && !buf.toString().isEmpty()) {
                keys.add(buf.toString());
                step = 0;
                buf = new StringBuilder();
            }
        }

        return keys;
    }

    // ----------------------------------------------------------------

    /**
     * 从上下文{@code context}中 - 解析 {@code expression} 表达式 的值
     * context 支持形如: {@code Map<String, Map<String, Map<String, ...>>>} 这样的无限嵌套属性
     *
     * @param expression 表达式
     * @param context    参数上下文
     * @return 解析结果
     */
    private static String eval(String expression, Map<String, Object> context) {
        List<String> keys = Stream.of(expression.split("\\.")).collect(Collectors.toList());
        Map<String, Object> ctx = context;
        String propValue = null;
        for (String key : keys) {
            Object candidate = ctx.get(key);
            if (null != candidate) {
                if (candidate instanceof Map) {
                    ctx = (Map<String, Object>) candidate;
                } else {
                    propValue = candidate.toString();
                    break;
                }
            }
        }

        return propValue;
    }

    // ----------------------------------------------------------------

    private static String resolve(String source, String expression, String target) {
        return source.replaceAll("\\$\\{" + expression + "}", target);
    }

    private static boolean isNotBlank(String source) {
        return null != source && source.trim().length() > 0;
    }

}
