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
 * {@code MongoDocument}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
public interface MongoDocument extends PrimaryKey {

    /**
     * 设置主键标识
     *
     * @param id 主键标识
     */
    void setId(String id);

    String getId();

    /**
     * 设置
     * 业务主键标识
     *
     * @param businessId 业务主键标识
     */
    default void setBusinessId(String businessId) {

    }

    /**
     * 获取业务标识
     *
     * @return 业务标识 {@code bizId}
     */
    default String getBusinessId() {
        return "";
    }
}
