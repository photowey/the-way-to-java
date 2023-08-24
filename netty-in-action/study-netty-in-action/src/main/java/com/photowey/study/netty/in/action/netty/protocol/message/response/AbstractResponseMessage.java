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
package com.photowey.study.netty.in.action.netty.protocol.message.response;

import com.photowey.study.netty.in.action.netty.protocol.message.AbstractRpcMessage;

/**
 * {@code AbstractResponseMessage}
 *
 * @author photowey
 * @date 2022/04/17
 * @since 1.0.0
 */
public abstract class AbstractResponseMessage extends AbstractRpcMessage {

    private Integer status;
    private String code;
    private String message;

    public Integer getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Integer status() {
        return status;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AbstractResponseMessage status(Integer status) {
        this.status = status;
        return this;
    }

    public AbstractResponseMessage code(String code) {
        this.code = code;
        return this;
    }

    public AbstractResponseMessage message(String message) {
        this.message = message;
        return this;
    }
}
