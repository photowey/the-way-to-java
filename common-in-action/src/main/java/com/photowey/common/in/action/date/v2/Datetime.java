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
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * {@code Datetime}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/12/16
 */
public interface Datetime {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePatternConstants.yyyy_MM_dd_HH_mm_ss);

    DateTimeFormatter shortFormatter = DateTimeFormatter.ofPattern(DatePatternConstants.yyyy_MM_dd);

    // ----------------------------------------------------------------

    static DateTimeFormatter formatter() {
        return formatter;
    }

    static DateTimeFormatter shortFormatter() {
        return shortFormatter;
    }

    static DateTimeFormatter formatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    // ----------------------------------------------------------------

    static <T> boolean isNullOrEmpty(T object) {
        return ObjectUtils.isEmpty(object);
    }

    static <T> boolean isNotNullOrEmpty(T object) {
        return !isNullOrEmpty(object);
    }

    // ----------------------------------------------------------------

    static LocalDateTime now() {
        return LocalDateTime.now();
    }

    static String now(String pattern) {
        LocalDateTime now = LocalDateTime.now();

        return formatter(pattern).format(now);
    }

    // ----------------------------------------------------------------

    static boolean betweenNow(LocalDateTime start, LocalDateTime end) {
        LocalDateTime now = now();
        return start.isBefore(now) && end.isAfter(now);
    }

    // ----------------------------------------------------------------

    static LocalDateTime dayStart() {
        return dayStart(now());
    }

    static LocalDateTime dayStart(LocalDateTime dateTime) {
        if (isNullOrEmpty(dateTime)) {
            return null;
        }

        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIN);
    }

    // ----------------------------------------------------------------

    static LocalDateTime dayEnd() {
        return dayEnd(now());
    }

    static LocalDateTime dayEnd(LocalDateTime dateTime) {
        if (isNullOrEmpty(dateTime)) {
            return null;
        }

        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
    }

    // ----------------------------------------------------------------

    static LocalDateTime weekStart() {
        return weekStart(now());
    }

    static LocalDateTime weekStart(LocalDateTime dateTime) {
        if (isNullOrEmpty(dateTime)) {
            return null;
        }

        TemporalField week = WeekFields.of(Locale.CHINA).dayOfWeek();
        ZonedDateTime zdt = dateTime.atZone(ZoneId.systemDefault()).with(week, 2);
        return dayStart(zdt.toLocalDateTime());
    }

    // ----------------------------------------------------------------

    static LocalDateTime weekEnd() {
        return weekEnd(now());
    }

    static LocalDateTime weekEnd(LocalDateTime dateTime) {
        if (isNullOrEmpty(dateTime)) {
            return null;
        }

        TemporalField week = WeekFields.of(Locale.CHINA).dayOfWeek();
        ZonedDateTime zdt = dateTime.atZone(ZoneId.systemDefault()).with(week, 7);
        return dayEnd(zdt.toLocalDateTime().plusDays(1));
    }

    // ----------------------------------------------------------------

    static LocalDateTime monthStart() {
        return monthStart(now());
    }

    static LocalDateTime monthStart(LocalDateTime dateTime) {
        if (isNullOrEmpty(dateTime)) {
            return null;
        }

        ZonedDateTime zdt = dateTime.atZone(ZoneId.systemDefault()).with(TemporalAdjusters.firstDayOfMonth());
        return dayStart(zdt.toLocalDateTime());
    }

    // ----------------------------------------------------------------

    static LocalDateTime monthEnd() {
        return monthEnd(now());
    }

    static LocalDateTime monthEnd(LocalDateTime dateTime) {
        if (isNullOrEmpty(dateTime)) {
            return null;
        }

        ZonedDateTime zdt = dateTime.atZone(ZoneId.systemDefault()).with(TemporalAdjusters.lastDayOfMonth());
        return dayEnd(zdt.toLocalDateTime());
    }

    // ----------------------------------------------------------------

    static LocalDateTime yearStart() {
        return yearStart(now());
    }

    static LocalDateTime yearStart(LocalDateTime dateTime) {
        if (isNullOrEmpty(dateTime)) {
            return null;
        }

        ZonedDateTime zdt = dateTime.atZone(ZoneId.systemDefault()).with(TemporalAdjusters.firstDayOfYear());
        return dayStart(zdt.toLocalDateTime());
    }

    // ----------------------------------------------------------------

    static LocalDateTime yearEnd() {
        return yearEnd(now());
    }

    static LocalDateTime yearEnd(LocalDateTime dateTime) {
        if (isNullOrEmpty(dateTime)) {
            return null;
        }

        ZonedDateTime zdt = dateTime.atZone(ZoneId.systemDefault()).with(TemporalAdjusters.lastDayOfYear());
        return dayEnd(zdt.toLocalDateTime());
    }
}

