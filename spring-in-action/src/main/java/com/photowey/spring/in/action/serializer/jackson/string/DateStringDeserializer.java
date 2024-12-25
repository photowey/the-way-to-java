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
package com.photowey.spring.in.action.serializer.jackson.string;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.photowey.common.in.action.date.DatePatternConstants;
import com.photowey.common.in.action.date.v2.Datetime;
import com.photowey.common.in.action.date.v2.LocalDateTimeTransformer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * {@code DateStringDeserializer}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/12/16
 */
public class DateStringDeserializer extends JsonDeserializer<Date> implements LocalDateTimeTransformer {

    private static final int SHORT_FORMATTER_TEMPLATE_LENGTH = DatePatternConstants.yyyy_MM_dd.length();

    @Override
    public Date deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
        String datetime = p.getValueAsString();
        if (StringUtils.hasLength(datetime)) {
            // yyyy-MM-dd HH:mm:ss | 默认格式
            if (datetime.length() > SHORT_FORMATTER_TEMPLATE_LENGTH) {
                return LocalDateTimeTransformer.toDate(LocalDateTime.parse(datetime, Datetime.formatter()));
            }

            // yyyy-MM-dd
            return LocalDateTimeTransformer.toDate(LocalDateTime.parse(datetime, Datetime.shortFormatter()));
        }

        return null;
    }
}

