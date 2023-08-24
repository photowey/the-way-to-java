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
package com.photowey.wechat.sdk.core.date.formatter;

import com.photowey.wechat.sdk.core.date.constant.DatePatternConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * {@code RFC3339DateTimeFormatter}
 *
 * @author photowey
 * @date 2023/05/02
 * @since 1.0.0
 */
public interface RFC3339DateTimeFormatter {

    char RFC_3339_T = 'T';
    String RFC_3339_T_STRING = "'T'";
    String GMT_8 = "+08:00";

    String DEFAULT_ZONE_PATTERN = defaultZone();

    static String defaultZone() {
        return zonePattern(GMT_8);
    }

    static String zonePattern(String zone) {
        return new StringBuilder()
                .append(DatePatternConstants.yyyy_MM_dd)
                .append(RFC_3339_T_STRING)
                .append(DatePatternConstants.HH_mm_ss)
                .append(zone)
                .toString();
    }

    static DateTimeFormatter buildDefault() {
        return build(GMT_8);
    }

    static DateTimeFormatter build(String zone) {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral(RFC_3339_T)
                .append(DateTimeFormatter.ISO_LOCAL_TIME)
                .appendLiteral(zone)
                .toFormatter();
    }

    static String format() {
        return format(LocalDateTime.now());
    }

    static String format(LocalDateTime dateTime) {
        if (null == dateTime) {
            return null;
        }

        DateTimeFormatter formatter = buildDefault();
        return formatter.format(dateTime);
    }

    static LocalDateTime toLocalDateTime(String dateTime) {
        if (null == dateTime || dateTime.trim().length() == 0) {
            return null;
        }

        return LocalDateTime.parse(dateTime, buildDefault());
    }

}
