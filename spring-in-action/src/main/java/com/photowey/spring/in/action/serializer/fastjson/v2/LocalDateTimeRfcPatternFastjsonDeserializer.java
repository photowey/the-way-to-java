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
package com.photowey.spring.in.action.serializer.fastjson.v2;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.photowey.spring.in.action.serializer.formatter.RFC3339DateTimeFormatter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@code LocalDateTimeRfcPatternFastjsonDeserializer}
 *
 * @author photowey
 * @date 2023/03/16
 * @since 1.0.0
 */
public class LocalDateTimeRfcPatternFastjsonDeserializer implements ObjectReader<LocalDateTime> {

    @Override
    public LocalDateTime readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
        String dateTime = jsonReader.read(String.class);
        DateTimeFormatter formatter = RFC3339DateTimeFormatter.buildDefault();
        return LocalDateTime.from(formatter.parse(dateTime));
    }
}

