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
package com.photowey.spring.in.action.dynamic.proxy.jdk;

import com.photowey.spring.in.action.dynamic.proxy.AbstractInvocationHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * {@code JdkInvocationHandler}
 *
 * @author photowey
 * @date 2021/11/11
 * @since 1.0.0
 */
public class JdkInvocationHandler extends AbstractInvocationHandler implements InvocationHandler {

    public JdkInvocationHandler(Class<?> targetType, AnnotationAttributes annotationAttributes, ApplicationContext applicationContext) {
        super(targetType, annotationAttributes, applicationContext);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.doInvoke(proxy, method, args);
    }
}