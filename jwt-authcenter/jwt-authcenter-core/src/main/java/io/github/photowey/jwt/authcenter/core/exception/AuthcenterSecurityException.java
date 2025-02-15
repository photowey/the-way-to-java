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

/**
 * {@code AuthcenterSecurityException}.
 * |- 和 {@code Security} 相关时,抛出该异常
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
public class AuthcenterSecurityException extends AuthcenterException {

    public AuthcenterSecurityException() {
        this(ExceptionStatus.BAD_REQUEST);
    }

    public AuthcenterSecurityException(String message) {
        this(ExceptionStatus.BAD_REQUEST.status(), ExceptionStatus.BAD_REQUEST.code(), message);
    }

    public AuthcenterSecurityException(String message, Object... args) {
        this(
            ExceptionStatus.BAD_REQUEST.status(),
            ExceptionStatus.BAD_REQUEST.code(),
            message,
            args
        );
    }

    public AuthcenterSecurityException(int status, String message) {
        this(status, ExceptionStatus.resolve(status).code(), message);
    }

    public AuthcenterSecurityException(int status, String message, Object... args) {
        this(status, ExceptionStatus.resolve(status).code(), message, args);
    }

    public AuthcenterSecurityException(int status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public AuthcenterSecurityException(String code, String message, Object... args) {
        this(ExceptionStatus.INNER_ERROR.status(), code, message, args);
    }

    public AuthcenterSecurityException(int status, String code, String message, Object... args) {
        super(formatted(message, args));
        this.status = status;
        this.code = code;
        this.message = formatted(message, args);
    }

    public AuthcenterSecurityException(String message, Throwable cause) {
        super(message, cause);
        this.status = ExceptionStatus.INNER_ERROR.status();
        this.code = ExceptionStatus.INNER_ERROR.code();
        this.message = message;
    }

    public AuthcenterSecurityException(ExceptionStatus exceptionStatus) {
        this(exceptionStatus.status(), exceptionStatus.code(), exceptionStatus.message());
    }

    public AuthcenterSecurityException(ExceptionStatus exceptionStatus, String message,
                                       Object... args) {
        this(exceptionStatus.status(), exceptionStatus.code(), message, args);
    }

    public AuthcenterSecurityException(ExceptionStatus exceptionStatus, Throwable cause) {
        super(exceptionStatus.message(), cause);
        this.status = exceptionStatus.status();
        this.code = exceptionStatus.code();
        this.message = exceptionStatus.message();
    }

    public AuthcenterSecurityException(String code, String message, Throwable cause) {
        super(message, cause);
        this.status = ExceptionStatus.INNER_ERROR.status();
        this.code = code;
        this.message = message;
    }

    public AuthcenterSecurityException(Throwable cause) {
        this(ExceptionStatus.INNER_ERROR, cause);
    }

    public static <T extends Throwable> boolean predicateIsSelf(T throwable) {
        return throwable instanceof AuthcenterSecurityException;
    }
}

