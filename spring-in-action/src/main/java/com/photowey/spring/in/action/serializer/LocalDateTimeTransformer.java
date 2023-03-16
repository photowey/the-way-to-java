/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * {@code LocalDateTimeTransformer}
 *
 * @author photowey
 * @date 2023/03/16
 * @since 1.0.0
 */
public interface LocalDateTimeTransformer {

    long MILLISECONDS = 1000L;

    static Long toTimestamp(LocalDateTime target) {
        if (null == target) {
            return null;
        }
        // 甩掉尾巴
        return target.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / MILLISECONDS * MILLISECONDS;
    }

    static LocalDateTime toLocalDateTime(Long timestamp) {
        if (null == timestamp) {
            return null;
        }

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }
}
