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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.io.InputStream;

/**
 * {@code JacksonJsonConverter}
 *
 * @author weichangjun
 * @date 2023/12/06
 * @since 1.0.0
 */
public interface JacksonJsonConverter extends JsonConverter {

    ObjectMapper objectMapper();

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

    @Override
    default <T> List<T> parseArray(String body, Class<T> clazz) {
        return this.parseObject(body, new TypeReference<List<T>>() {});
    }

    @Override
    default <T> List<T> parseArray(byte[] body, Class<T> clazz) {
        return this.parseObject(body, new TypeReference<List<T>>() {});
    }

    @Override
    default <T> List<T> parseArray(InputStream body, Class<T> clazz) {
        return this.parseObject(body, new TypeReference<List<T>>() {});
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
}
