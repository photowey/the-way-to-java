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
package com.photowey.mongo.in.action.document;

/**
 * {@code PrimaryKey}
 *
 * @author photowey
 * @date 2022/11/29
 * @since 1.0.0
 */
public interface PrimaryKey {

    /**
     * 是否支持自定义主键
     *
     * @return {@code boolean} 是否支持
     */
    default boolean supportCustoms() {
        return false;
    }

    /**
     * 获取
     * 自定义主键
     *
     * @return 自定义主键
     */
    default String customDefine() {
        return "";
    }
}
