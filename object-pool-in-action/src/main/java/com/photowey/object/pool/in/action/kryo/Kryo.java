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
package com.photowey.object.pool.in.action.kryo;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@code Kryo}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/07/10
 */
public class Kryo {

    // dummy kryo for test.

    private final ObjectMapper objectMapper;

    public Kryo(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> Input encode(T input) {
        try {
            return Input.builder().data(this.objectMapper.writeValueAsBytes(input)).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Output decode(Input input) {
        try {
            return Output.builder().data(input.getData()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}