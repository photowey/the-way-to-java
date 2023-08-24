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
package com.photowey.spring.in.action.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@code LongListJsonSerializer}
 *
 * @author photowey
 * @date 2022/04/12
 * @since 1.0.0
 */
public class LongListJsonSerializer extends JsonSerializer<List<Long>> {

    @Override
    public void serialize(List<Long> value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        List<String> targets = Optional.ofNullable(value)
                .orElse(new ArrayList<>(0)).stream().map(String::valueOf).collect(Collectors.toList());
        jsonGenerator.writeStartArray();
        this.writeContents(jsonGenerator, targets);
        jsonGenerator.writeEndArray();
    }

    private void writeContents(JsonGenerator jsonGenerator, List<String> targets) throws IOException {
        for (String target : targets) {
            jsonGenerator.writeString(target);
        }
    }
}