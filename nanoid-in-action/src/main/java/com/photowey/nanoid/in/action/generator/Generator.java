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
package com.photowey.nanoid.in.action.generator;

/**
 * {@code Generator}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public interface Generator {

    /**
     * 根据指定前缀
     * 生成随机串
     *
     * @param prefix 指定前缀
     * @return 随机串
     */
    String generate(String prefix);

    /**
     * 根据指定前缀和长度
     * 生成随机串
     *
     * @param prefix 指定前缀
     * @param size   指定长度
     * @return 随机串
     */
    String generate(String prefix, int size);

    /**
     * 根据指定字母表和长度
     * 生成随机串
     *
     * @param alphabet 指定字母表
     * @param size     指定长度
     * @return 随机串
     */
    String generate(char[] alphabet, int size);

    /**
     * 根据指定前缀, 字母表和长度
     * 生成随机串
     *
     * @param prefix   指定前缀
     * @param alphabet 指定字母表
     * @param size     指定长度
     * @return 随机串
     */
    String generate(String prefix, char[] alphabet, int size);
}
