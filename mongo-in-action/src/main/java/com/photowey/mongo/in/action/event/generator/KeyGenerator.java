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
package com.photowey.mongo.in.action.event.generator;

/**
 * {@code KeyGenerator}
 *
 * @author photowey
 * @date 2022/10/26
 * @since 1.0.0
 */
public interface KeyGenerator {

    /**
     * 数值类型标识
     * 主键标识
     *
     * @return 主键标识
     */
    long nextId();

    /**
     * 指定长度随机数
     * - 默认: 21 字长
     * 随机数标识
     *
     * @return 随机数
     */
    String nanoId();
}
