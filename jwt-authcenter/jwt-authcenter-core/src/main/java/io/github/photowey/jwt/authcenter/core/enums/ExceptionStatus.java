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
package io.github.photowey.jwt.authcenter.core.enums;

/**
 * {@code ExceptionStatus}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
public enum ExceptionStatus {

    // 通用异常类型

    OK("api.ok", 200,
        "200000",
        "请求成功"
    ),

    BAD_REQUEST("bad.request", 400,
        "400000",
        "请求错误"
    ),
    UNAUTHORIZED_INVALID_MISSING_REQUIRED_PARAMETER(
        "missing.required.parameter", 400,
        "400001",
        "请传入: 正确的参数"
    ),

    UNAUTHORIZED("unauthorized", 401,
        "401000",
        "未认证"
    ),
    UNAUTHORIZED_EXPIRED("token.expired", 401,
        "401001",
        "令牌过期"
    ),
    UNAUTHORIZED_INVALID_TOKEN_TYPE(
        "token.invalid.type", 401,
        "401002",
        "令牌类型错误"
    ),
    UNAUTHORIZED_INVALID_SIGNATURE(
        "token.invalid.signature", 401,
        "401003",
        "令牌验签失败"
    ),
    UNAUTHORIZED_DISTRUST_REQUEST(
        "token.distrust", 401,
        "401005",
        "不授信请求"
    ),
    UNAUTHORIZED_ACCOUNT_FORBIDDEN(
        "account.forbidden", 401,
        "401006",
        "账号已封禁"
    ),

    FORBIDDEN("request.forbidden", 403,
        "403000",
        "无权限"
    ),
    NOT_ACTIVATED_FORBIDDEN(
        "api.request.not.activated.forbidden", 403,
        "403001",
        "账号未激活"
    ),

    INNER_ERROR("api.request.inner.error", 500,
        "500000",
        "系统错误"
    ),
    TIME_OUT("api.request.timeout.error", 504,
        "504000",
        "请求超时"
    ),

    ;

    private final String alias;
    private final int status;
    private final String code;
    private final String message;

    ExceptionStatus(String alias, int status, String code, String message) {
        this.alias = alias;
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ExceptionStatus resolve(int status) {
        for (ExceptionStatus ex : values()) {
            if (status == ex.status()) {
                return ex;
            }
        }

        return INNER_ERROR;
    }

    public static ExceptionStatus resolve(String code) {
        for (ExceptionStatus ex : values()) {
            if (ex.code().equalsIgnoreCase(code)) {
                return ex;
            }
        }

        return INNER_ERROR;
    }

    public String alias() {
        return alias;
    }

    public int status() {
        return status;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
