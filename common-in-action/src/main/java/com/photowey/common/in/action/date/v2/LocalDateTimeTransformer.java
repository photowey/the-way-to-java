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

import com.photowey.common.in.action.date.DatePatternConstants;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * {@code LocalDateTimeTransformer}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/12/16
 */
public interface LocalDateTimeTransformer {

    // ----------------------------------------------------------------

    static String format(LocalDateTime dateTime) {
        if (Datetime.isNullOrEmpty(dateTime)) {
            return null;
        }

        return format(dateTime, DatePatternConstants.yyyy_MM_dd_HH_mm_ss);
    }

    static String format(LocalDateTime dateTime, String pattern) {
        if (Datetime.isNullOrEmpty(dateTime)) {
            return null;
        }

        DateTimeFormatter formatter = Datetime.formatter(pattern);
        return formatter.format(dateTime);
    }

    static String format(LocalDate dateTime) {
        if (Datetime.isNullOrEmpty(dateTime)) {
            return null;
        }

        return format(dateTime, DatePatternConstants.yyyy_MM_dd);
    }

    static String format(LocalDate dateTime, String pattern) {
        if (Datetime.isNullOrEmpty(dateTime)) {
            return null;
        }

        DateTimeFormatter formatter = Datetime.formatter(pattern);
        return formatter.format(dateTime);
    }

    // ----------------------------------------------------------------

    static String format(LocalTime dateTime) {
        if (Datetime.isNullOrEmpty(dateTime)) {
            return null;
        }

        return format(dateTime, DatePatternConstants.HH_mm_ss);
    }

    static String format(LocalTime dateTime, String pattern) {
        if (Datetime.isNullOrEmpty(dateTime)) {
            return null;
        }

        DateTimeFormatter formatter = Datetime.formatter(pattern);
        return formatter.format(dateTime);
    }

    static String format(Date dateTime) {
        if (Datetime.isNullOrEmpty(dateTime)) {
            return null;
        }

        return format(dateTime, DatePatternConstants.yyyy_MM_dd_HH_mm_ss);
    }

    static String format(Date dateTime, String pattern) {
        if (Datetime.isNullOrEmpty(dateTime)) {
            return null;
        }

        return format(toLocalDateTime(dateTime), pattern);
    }

    // ----------------------------------------------------------------

    static Date toDate(LocalDate date) {
        if (Datetime.isNullOrEmpty(date)) {
            return null;
        }

        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    static Date toDate(LocalDateTime dateTime) {
        if (Datetime.isNullOrEmpty(dateTime)) {
            return null;
        }

        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    // ----------------------------------------------------------------

    static LocalDate toLocalDate(Date date) {
        if (Datetime.isNullOrEmpty(date)) {
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // ----------------------------------------------------------------

    static LocalDateTime toLocalDateTime(Date date) {
        if (Datetime.isNullOrEmpty(date)) {
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    static LocalDateTime toLocalDateTime(Long timestamp) {
        if (Datetime.isNullOrEmpty(timestamp)) {
            return null;
        }

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    // ----------------------------------------------------------------

    static LocalDateTime toLocalDateTime(String dateTime) {
        if (Datetime.isNullOrEmpty(dateTime)) {
            return null;
        }

        return LocalDateTime.parse(dateTime, Datetime.formatter());
    }

    static LocalDateTime toLocalDateTime(String dateTime, String pattern) {
        if (Datetime.isNullOrEmpty(dateTime)) {
            return null;
        }

        return LocalDateTime.parse(dateTime, Datetime.formatter(pattern));
    }

    // ----------------------------------------------------------------

    static Long toTimestamp(Date target) {
        if (Datetime.isNullOrEmpty(target)) {
            return null;
        }

        return target.getTime();
    }

    static Long toTimestamp(LocalDateTime target) {
        if (Datetime.isNullOrEmpty(target)) {
            return null;
        }

        return target.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
