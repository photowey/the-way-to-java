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
package com.photowey.wechat.sdk.core.date;

import com.photowey.wechat.sdk.core.date.constant.DatePatternConstants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * {@code LocalDateTimeConverter}
 *
 * @author photowey
 * @date 2023/05/02
 * @since 1.0.0
 */
public interface LocalDateTimeConverter {

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

    static DateTimeFormatter formatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    static LocalDateTime now() {
        return LocalDateTime.now();
    }

    static String now(String pattern) {
        return formatter(pattern).format(now());
    }

    static String today() {
        return formatter(DatePatternConstants.yyyyMMdd).format(now());
    }
}
