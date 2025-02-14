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

import io.github.photowey.jwt.authcenter.core.domain.model.ExceptionBody;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * {@code PlatformException}.
 * |- 所有自定义异常的根异常
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class PlatformException extends RuntimeException {

    protected Integer status;
    protected String code;
    protected String message;

    public PlatformException() {
        super();
    }

    public PlatformException(String message) {
        super(message);
    }

    public PlatformException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformException(Throwable cause) {
        super(cause);
    }

    public PlatformException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExceptionBody toExceptionBody() {
        return ExceptionBody.builder()
            .code(this.code())
            .message(this.message())
            .build();
    }
}

