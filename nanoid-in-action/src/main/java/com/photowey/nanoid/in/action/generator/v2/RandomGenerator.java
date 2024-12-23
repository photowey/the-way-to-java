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
package com.photowey.nanoid.in.action.generator.v2;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.photowey.nanoid.in.action.random.NanoId;

/**
 * {@code RandomGenerator}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/12/16
 */
public interface RandomGenerator {

    String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 生成随机字符串
     * |- 默认: 21 位
     *
     * @return 随机字符串
     */
    default String random() {
        return this.random(ALPHABET, NanoIdUtils.DEFAULT_SIZE);
    }

    /**
     * 生成指定长度随机字符串
     *
     * @param length 指定长度
     * @return 随机字符串
     */
    default String random(int length) {
        return this.random(ALPHABET, length);
    }

    /**
     * 根据给定的字母表生成指定长度随机字符串
     *
     * @param alphabet 字母表
     * @param length   指定长度
     * @return 随机字符串
     */
    default String random(String alphabet, int length) {
        return NanoId.randomLowerNanoId(alphabet.toCharArray(), length);
    }
}

