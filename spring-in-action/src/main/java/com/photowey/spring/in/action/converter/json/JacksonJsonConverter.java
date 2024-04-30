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
package com.photowey.spring.in.action.converter.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.photowey.spring.in.action.ctx.getter.ObjectMapperGetter;

import java.io.InputStream;
import java.util.Map;

/**
 * {@code JacksonJsonConverter}
 *
 * @author photowey
 * @date 2023/12/06
 * @since 1.0.0
 */
public interface JacksonJsonConverter extends JsonConverter, ObjectMapperGetter {

    @Override
    default <P> String toJSONString(P payload) {
        try {
            return this.objectMapper().writeValueAsString(payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default <T> T parseObject(String body, Class<T> clazz) {
        try {
            return this.objectMapper().readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default <T> T parseObject(byte[] body, Class<T> clazz) {
        try {
            return this.objectMapper().readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default <T> T parseObject(InputStream body, Class<T> clazz) {
        try {
            return this.objectMapper().readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default <T> T parseObject(String body, TypeReference<T> clazz) {
        try {
            return this.objectMapper().readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default <T> T parseObject(byte[] body, TypeReference<T> clazz) {
        try {
            return this.objectMapper().readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default <T> T parseObject(InputStream body, TypeReference<T> clazz) {
        try {
            return this.objectMapper().readValue(body, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------------------------

    @Override
    default <T> T toObject(Map<String, Object> map, Class<T> targetClass) {
        try {
            return this.objectMapper().convertValue(map, targetClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default <T> Map<String, Object> toMap(T object) {
        try {
            return this.objectMapper().convertValue(object, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------------------------

    default <T> T parseArray(String body, TypeReference<T> typeRef) {
        return this.parseObject(body, typeRef);
    }

    default <T> T parseArray(byte[] body, TypeReference<T> typeRef) {
        return this.parseObject(body, typeRef);
    }

    default <T> T parseArray(InputStream body, TypeReference<T> typeRef) {
        return this.parseObject(body, typeRef);
    }
}
