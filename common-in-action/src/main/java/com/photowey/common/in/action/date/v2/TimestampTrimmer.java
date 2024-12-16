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
package com.photowey.common.in.action.date.v2;

import com.photowey.common.in.action.constant.DatetimeConstants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * {@code TimestampTrimmer}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/12/16
 */
public interface TimestampTrimmer {

    static LocalDateTime trimTail(LocalDateTime dateTime) {
        Long ts = toTimestamp(dateTime);

        return toLocalDateTime(ts);
    }

    static Long trimTail(Long timestamp) {
        if (null == timestamp) {
            return timestamp;
        }

        return timestamp / DatetimeConstants.MILLIS_SECONDS * DatetimeConstants.MILLIS_SECONDS;
    }

    static Long toTimestamp(LocalDateTime target) {
        if (null == target) {
            return null;
        }

        return trimTail(target.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    static LocalDateTime toLocalDateTime(Long timestamp) {
        if (null == timestamp) {
            return null;
        }

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }
}

