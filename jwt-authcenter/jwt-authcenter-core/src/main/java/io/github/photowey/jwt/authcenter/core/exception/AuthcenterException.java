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
package io.github.photowey.jwt.authcenter.core.exception;

import io.github.photowey.jwt.authcenter.core.enums.ExceptionStatus;
import io.github.photowey.jwt.authcenter.core.formatter.StringFormatter;

import java.util.Objects;

/**
 * {@code AuthcenterException}.
 * |- 认证中心自定义异常根异常
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
public class AuthcenterException extends PlatformException {

    public AuthcenterException() {
        this(ExceptionStatus.BAD_REQUEST);
    }

    public AuthcenterException(String message) {
        this(
            ExceptionStatus.BAD_REQUEST.status(),
            ExceptionStatus.BAD_REQUEST.code(),
            message
        );
    }

    public AuthcenterException(String message, Object... args) {
        this(
            ExceptionStatus.BAD_REQUEST.status(),
            ExceptionStatus.BAD_REQUEST.code(),
            message,
            args
        );
    }

    public AuthcenterException(int status, String message) {
        this(status, ExceptionStatus.resolve(status).code(), message);
    }

    public AuthcenterException(int status, String message, Object... args) {
        this(status, ExceptionStatus.resolve(status).code(), message, args);
    }

    public AuthcenterException(int status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public AuthcenterException(String code, String message, Object... args) {
        this(ExceptionStatus.INNER_ERROR.status(), code, message, args);
    }

    public AuthcenterException(int status, String code, String message, Object... args) {
        super(formatted(message, args));
        this.status = status;
        this.code = code;
        this.message = formatted(message, args);
    }

    public AuthcenterException(String message, Throwable cause) {
        super(message, cause);
        this.status = ExceptionStatus.INNER_ERROR.status();
        this.code = ExceptionStatus.INNER_ERROR.code();
        this.message = message;
    }

    public AuthcenterException(ExceptionStatus exceptionStatus) {
        this(exceptionStatus.status(), exceptionStatus.code(), exceptionStatus.message());
    }

    public AuthcenterException(ExceptionStatus exceptionStatus, String message, Object... args) {
        this(exceptionStatus.status(), exceptionStatus.code(), message, args);
    }

    public AuthcenterException(ExceptionStatus exceptionStatus, Throwable cause) {
        super(exceptionStatus.message(), cause);
        this.status = exceptionStatus.status();
        this.code = exceptionStatus.code();
        this.message = exceptionStatus.message();
    }

    public AuthcenterException(String code, String message, Throwable cause) {
        super(message, cause);
        this.status = ExceptionStatus.INNER_ERROR.status();
        this.code = code;
        this.message = message;
    }

    public AuthcenterException(Throwable cause) {
        this(ExceptionStatus.INNER_ERROR, cause);
    }

    public Integer getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    protected static String formatted(String message, Object... args) {
        return determineIsStringFormatterPattern(message)
            ? StringFormatter.format(message, args)
            : String.format(message, args);
    }

    public static <T extends Throwable> boolean predicateIsNotSelf(T throwable) {
        return !predicateIsSelf(throwable);
    }

    public static <T extends Throwable> boolean predicateIsSelf(T throwable) {
        return throwable instanceof AuthcenterException;
    }

    public static boolean determineIsStringFormatterPattern(String message) {
        if (Objects.isNull(message)) {
            return false;
        }
        return message.contains("{}");
    }
}

