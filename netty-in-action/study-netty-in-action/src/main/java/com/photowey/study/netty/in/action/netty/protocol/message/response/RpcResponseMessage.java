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
package com.photowey.study.netty.in.action.netty.protocol.message.response;

/**
 * {@code RpcResponseMessage}
 *
 * @author photowey
 * @date 2022/04/17
 * @since 1.0.0
 */
public class RpcResponseMessage extends AbstractResponseMessage {

    /**
     * 返回值
     */
    protected Object response;
    /**
     * 执行异常
     */
    protected Throwable throwable;

    @Override
    public int getMessageType() {
        return RESPONSE;
    }

    public Object getResponse() {
        return response;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public Object response() {
        return response;
    }

    public Throwable throwable() {
        return throwable;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public RpcResponseMessage response(Object response) {
        this.response = response;
        return this;
    }

    public RpcResponseMessage throwable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }
}
