/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.crypto.in.action.reader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * {@code ClassPathReader}
 * 读取 {@code classpath} 下面的文件
 *
 * <pre>
 * String data = ClassPathReader.joinRead("hello.txt")
 * String data = ClassPathReader.joinRead("key/hello.txt")
 * // 忽略 # 开头的注释行
 * String data = ClassPathReader.joinReade("key/hello.txt", "#")
 * </pre>
 *
 * @author photowey
 * @date 2022/03/03
 * @since 1.0.0
 */
public final class ClassPathReader {

    private ClassPathReader() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static String joinRead(String path) {
        return joinRead(path, "");
    }

    public static String joinRead(String path, CharSequence delimiter) {
        return joinReade(path, delimiter, "");
    }

    public static String joinReade(String path, String excludePrefix) {
        return joinReade(path, "", excludePrefix);
    }

    public static String joinReade(String path, CharSequence delimiter, String excludePrefix) {
        final Resource target = new ClassPathResource(path);
        try (
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(target.getInputStream(), StandardCharsets.UTF_8))
        ) {
            if (StringUtils.hasText(excludePrefix)) {
                return bufferedReader.lines().filter(line -> !line.startsWith(excludePrefix)).collect(Collectors.joining(delimiter));
            }
            return bufferedReader.lines().collect(Collectors.joining(delimiter));
        } catch (IOException e) {
            throw new SecurityException(e);
        }
    }

    public static InputStream read(String path) {
        try {
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }
}
