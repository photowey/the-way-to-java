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
package io.github.photowey.proguard.in.action.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.photowey.proguard.in.action.domain.entity.Hello;
import io.github.photowey.proguard.in.action.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code HelloServiceImpl}
 *
 * @author photowey
 * @date 2023/11/12
 * @since 1.0.0
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Integer calculate() {
        Hello hello = populateHello();

        return hello.getVariableInt() * 10;
    }

    @Override
    public String serializeObjectToString() {
        Hello hello = populateHello();
        try {
            return this.objectMapper.writeValueAsString(hello);
        } catch (Exception e) {
            throw new RuntimeException("failed");
        }
    }

    private static Hello populateHello() {
        Map<String, Object> map = new HashMap<>(4);
        map.put("hello", "world");
        map.put("tom", "jerry");

        Hello hello = Hello.builder()
                .variableString("hello.proguard")
                .variableInt(10)
                .variableLong(System.currentTimeMillis())
                .variableObject(map)
                .build();

        return hello;
    }
}
