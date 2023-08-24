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
package com.photowey.commom.in.action.enums;

import com.photowey.commom.in.action.constant.SharedConstants;
import com.photowey.commom.in.action.date.DateUtils;

/**
 * {@code SystemClock}
 *
 * @author photowey
 * @date 2023/07/26
 * @since 1.0.0
 */
public enum SystemClock {

    ;

    public enum Strings {

        ;

        public static String now() {
            return DateUtils.nowStr();
        }

        public static java.time.LocalDateTime transfer(String dateTime) {
            return DateUtils.toLocalDateTime(dateTime);
        }

        public static java.time.LocalDateTime transfer(String dateTime, String pattern) {
            return DateUtils.toLocalDateTime(dateTime, pattern);
        }
    }

    public enum Timestamp {

        ;

        public static long now() {
            return System.currentTimeMillis() / SharedConstants.MILLIS_UNIT * SharedConstants.MILLIS_UNIT;
        }

        public static java.time.LocalDateTime transfer(Long ts) {
            if (null == ts || 0L == ts) {
                return null;
            }
            if (String.valueOf(ts).length() < SharedConstants.TIME_STAMP_LENGTH) {
                return transferShort(ts);
            }

            return DateUtils.toLocalDateTime(ts);
        }

        public static java.time.LocalDateTime transferShort(Long ts) {
            if (null == ts || 0L == ts) {
                return null;
            }
            if (String.valueOf(ts).length() >= SharedConstants.TIME_STAMP_LENGTH) {
                return transfer(ts);
            }

            return transfer(ts * SharedConstants.MILLIS_UNIT);
        }
    }

    public enum LocalDateTime {

        ;

        public static java.time.LocalDateTime now() {
            return java.time.LocalDateTime.now();
        }

        public static Long transfer(java.time.LocalDateTime time) {
            return DateUtils.toTimestamp(time);
        }

        public static String transfers(java.time.LocalDateTime time) {
            return DateUtils.format(time);
        }

        public static String transfers(java.time.LocalDateTime time, String pattern) {
            return DateUtils.format(time, pattern);
        }
    }
}
