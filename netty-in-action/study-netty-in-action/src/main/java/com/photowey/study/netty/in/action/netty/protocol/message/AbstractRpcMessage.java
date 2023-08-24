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
package com.photowey.study.netty.in.action.netty.protocol.message;

import com.photowey.study.netty.in.action.netty.protocol.message.request.RpcRequestMessage;
import com.photowey.study.netty.in.action.netty.protocol.message.response.RpcResponseMessage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code AbstractRpcMessage}
 *
 * @author photowey
 * @date 2022/04/17
 * @since 1.0.0
 */
public abstract class AbstractRpcMessage implements Serializable, IRpcMessage {

    protected int requestId;
    protected int messageType;

    protected static final Map<Integer, Class<? extends AbstractRpcMessage>> MESSAGE_TYPE_CLASS_MAPPINGS = new HashMap<>();

    static {
        register(REQUEST, RpcRequestMessage.class);
        register(RESPONSE, RpcResponseMessage.class);
        register(PING, RpcPingMessage.class);
        register(PONG, RpcPongMessage.class);
    }

    public static void register(int messageType, Class<? extends AbstractRpcMessage> clazz) {
        MESSAGE_TYPE_CLASS_MAPPINGS.put(messageType, clazz);
    }

    public int getRequestId() {
        return requestId;
    }

    @Override
    public int getMessageType() {
        return messageType;
    }

    public int requestId() {
        return requestId;
    }

    public int messageType() {
        return messageType;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public AbstractRpcMessage requestId(int requestId) {
        this.requestId = requestId;
        return this;
    }

    public AbstractRpcMessage messageType(int messageType) {
        this.messageType = messageType;
        return this;
    }
}
