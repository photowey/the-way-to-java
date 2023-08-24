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
package com.photowey.spring.in.action.hello.factorybean;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * {@code JungleFactoryBean}
 *
 * @author photowey
 * @date 2023/06/04
 * @since 1.0.0
 */
public class JungleFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> interfaceType;

    public JungleFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(
                this.interfaceType.getClassLoader(),
                new Class[]{this.interfaceType},
                this.createProxy(this.interfaceType)
        );
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceType;
    }

    private InvocationHandler createProxy(Class<T> interfaceType) {
        return ((proxy, method, args) -> {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }

            Class<?> returnType = method.getReturnType();
            // TODO 示例 直接 newInstance
            return returnType.getDeclaredConstructor().newInstance();
        });
    }
}
