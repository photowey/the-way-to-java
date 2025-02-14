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
package io.github.photowey.jwt.authcenter.core.domain.model;

import io.github.photowey.jwt.authcenter.core.enums.ExceptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * {@code ExceptionBody}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionBody implements Serializable {

    @Serial
    private static final long serialVersionUID = -388499493949353516L;

    private String code;
    private String message;

    public ExceptionBody(ExceptionStatus status) {
        this.code = status.code();
        this.message = status.message();
    }

    public ExceptionBody(ExceptionStatus status, String message) {
        this.code = status.code();
        this.message = message;
    }

    // ----------------------------------------------------------------

    public String getMsg() {
        return message;
    }

    public String msg() {
        return message;
    }

    // ----------------------------------------------------------------

    public static ExceptionBody badRequest() {
        return new ExceptionBody(ExceptionStatus.BAD_REQUEST);
    }

    public static ExceptionBody badUnHandle() {
        return new ExceptionBody(ExceptionStatus.BAD_REQUEST, "请求错误,请稍后重试~");
    }

    public static ExceptionBody unauthorized() {
        return new ExceptionBody(ExceptionStatus.UNAUTHORIZED);
    }

    public static ExceptionBody unauthorized(String message) {
        return new ExceptionBody(ExceptionStatus.UNAUTHORIZED, message);
    }

    public static ExceptionBody forbidden() {
        return new ExceptionBody(ExceptionStatus.FORBIDDEN);
    }

    public static ExceptionBody forbidden(String message) {
        return new ExceptionBody(ExceptionStatus.FORBIDDEN, message);
    }

    public static ExceptionBody nullPointer() {
        return new ExceptionBody(ExceptionStatus.INNER_ERROR, "空指针异常");
    }

    public static ExceptionBody illegalArgument() {
        return new ExceptionBody(ExceptionStatus.INNER_ERROR, "参数错误");
    }

    public static ExceptionBody timeout() {
        return new ExceptionBody(ExceptionStatus.TIME_OUT);
    }
}


