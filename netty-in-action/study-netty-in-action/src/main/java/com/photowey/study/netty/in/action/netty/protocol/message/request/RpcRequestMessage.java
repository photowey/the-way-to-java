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
package com.photowey.study.netty.in.action.netty.protocol.message.request;

import com.photowey.study.netty.in.action.netty.protocol.message.AbstractRpcMessage;

/**
 * {@code RpcRequestMessage}
 *
 * @author photowey
 * @date 2022/04/17
 * @since 1.0.0
 */
public class RpcRequestMessage extends AbstractRpcMessage {

    /**
     * 接口名
     */
    private String interfaze;
    /**
     * 方法名称
     */
    private String method;
    /**
     * 返回值类型
     */
    private Class<?> returnType;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 参数列表
     */
    private Object[] parameters;

    @Override
    public int getMessageType() {
        return REQUEST;
    }

    public String getInterfaze() {
        return interfaze;
    }

    public String getMethod() {
        return method;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public String interfaze() {
        return interfaze;
    }

    public String method() {
        return method;
    }

    public Class<?> returnType() {
        return returnType;
    }

    public Class<?>[] parameterTypes() {
        return parameterTypes;
    }

    public Object[] parameters() {
        return parameters;
    }

    public void setInterfaze(String interfaze) {
        this.interfaze = interfaze;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public RpcRequestMessage interfaze(String interfaze) {
        this.interfaze = interfaze;
        return this;
    }

    public RpcRequestMessage method(String method) {
        this.method = method;
        return this;
    }

    public RpcRequestMessage returnType(Class<?> returnType) {
        this.returnType = returnType;
        return this;
    }

    public RpcRequestMessage parameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    public RpcRequestMessage parameters(Object[] parameters) {
        this.parameters = parameters;
        return this;
    }
}
