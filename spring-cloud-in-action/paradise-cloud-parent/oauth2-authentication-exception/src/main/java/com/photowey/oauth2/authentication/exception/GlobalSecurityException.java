/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.oauth2.authentication.exception;

/**
 * {@code GlobalSecurityException}
 *
 * @author photowey
 * @date 2022/02/27
 * @since 1.0.0
 */
public class GlobalSecurityException extends RuntimeException {

    private int status;
    private int code;

    public GlobalSecurityException() {
        super();
        this.status = 500;
        this.code = 500;
    }

    public GlobalSecurityException(int status) {
        super();
        this.status = status;
        this.code = status;
    }

    public GlobalSecurityException(int status, int code) {
        super();
        this.status = status;
        this.code = code;
    }

    // ----------------------------------------------------------

    public GlobalSecurityException(String message) {
        super(message);
        this.status = 500;
        this.code = 500;
    }

    public GlobalSecurityException(int status, String message) {
        super(message);
        this.status = status;
        this.code = status;
    }

    public GlobalSecurityException(int status, int code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

    // ----------------------------------------------------------

    public GlobalSecurityException(String message, Throwable cause) {
        super(message, cause);
        this.status = 500;
        this.code = 500;
    }

    public GlobalSecurityException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.code = status;
    }

    public GlobalSecurityException(int status, int code, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.code = code;
    }

    // ----------------------------------------------------------

    public GlobalSecurityException(Throwable cause) {
        super(cause);
        this.status = 500;
        this.code = 500;
    }

    public GlobalSecurityException(int status, Throwable cause) {
        super(cause);
        this.status = status;
        this.code = status;
    }

    public GlobalSecurityException(int status, int code, Throwable cause) {
        super(cause);
        this.status = status;
        this.code = code;
    }

    protected GlobalSecurityException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = 500;
        this.code = 500;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
