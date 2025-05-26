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
package com.photowey.juc.in.action.mdc;

import org.slf4j.MDC;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * {@code Runnables}.
 *
 * @author 1.0.0
 * @since 2025/05/26
 */
public final class Runnables {

    private Runnables() {}

    private static final String TRACE_ID = "traceId";

    public static String traceId() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

    public static void setTraceIdIfAbsent() {
        if (MDC.get(TRACE_ID) == null) {
            MDC.put(TRACE_ID, traceId());
        }
    }

    public static <T> Callable<T> wrap(final Callable<T> callable) {
        Map<String, String> context = MDC.getCopyOfContextMap();

        return () -> {
            if (Objects.nonNull(context)) {
                MDC.setContextMap(context);
            }

            setTraceIdIfAbsent();

            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable) {
        Map<String, String> context = MDC.getCopyOfContextMap();

        return () -> {
            if (Objects.nonNull(context)) {
                MDC.setContextMap(context);
            }

            setTraceIdIfAbsent();

            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

}
