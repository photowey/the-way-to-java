/*
 * Copyright © 2025 the original author or authors.
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
package io.github.photowey.jwt.authcenter.core.checker;

import io.github.photowey.jwt.authcenter.core.enums.ExceptionStatus;
import io.github.photowey.jwt.authcenter.core.exception.AuthenticatedException;
import io.github.photowey.jwt.authcenter.core.formatter.StringFormatter;

import java.util.Objects;

/**
 * {@code AbstractAuthenticatedExceptionChecker}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/24
 */
public abstract class AbstractAuthenticatedExceptionChecker {

    public static <T> T checkNotNull(T t, String message, Object... args) {
        if (Objects.nonNull(t)) {
            return t;
        }

        throw new AuthenticatedException(formatMessage(message, args));
    }

    public static <T> T checkNull(T t, String message, Object... args) {
        if (Objects.isNull(t)) {
            return t;
        }

        throw new AuthenticatedException(formatMessage(message, args));
    }

    // ----------------------------------------------------------------

    public static void checkTrue(boolean expression, String message, Object... args) {
        if (expression) {
            return;
        }

        throw new AuthenticatedException(formatMessage(message, args));
    }

    public static void checkFalse(boolean expression, String message, Object... args) {
        checkTrue(!expression, message, args);
    }

    public static <T> T throwUnchecked(ExceptionStatus status) {
        throw new AuthenticatedException(status);
    }

    public static <T> T throwUnchecked(ExceptionStatus status, String message, Object... args) {
        throw new AuthenticatedException(status, formatMessage(message, args));
    }

    public static <T> T throwUnchecked(String message, Object... args) {
        throw new AuthenticatedException(formatMessage(message, args));
    }

    private static String formatMessage(String message, Object... args) {
        if (determineIsStringFormatterPattern(message)) {
            return StringFormatter.format(message, args);
        }

        return String.format(message, args);
    }

    public static boolean determineIsStringFormatterPattern(String message) {
        if (Objects.isNull(message)) {
            return false;
        }

        return message.contains("{}");
    }
}

