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
package com.photowey.wechat.sdk.core.serializer.time.fastjson;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;
import com.photowey.wechat.sdk.core.date.formatter.RFC3339DateTimeFormatter;
import com.photowey.wechat.sdk.core.serializer.time.Cleaner;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * {@code LocalDateTimeRfcPatternFastjsonSerializer}
 *
 * @author photowey
 * @date 2023/05/02
 * @since 1.0.0
 */
public class LocalDateTimeRfcPatternFastjsonSerializer implements ObjectWriter<LocalDateTime> {

    // 遵循 defaultZone 标准格式, 格式为 yyyy-MM-DDTHH:mm:ss+TIMEZONE
    // 2015-05-20T13:29:35+08:00

    @Override
    public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
        if (null != object) {
            LocalDateTime dateTime = (LocalDateTime) object;
            String formatted = RFC3339DateTimeFormatter.format(Cleaner.trimTail(dateTime));
            jsonWriter.writeString(formatted);
        }
    }

}
