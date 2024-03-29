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
package com.photowey.spring.in.action.dynamic.proxy;

import com.photowey.spring.in.action.dynamic.handler.DynamicInvokeHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * {@code AbstractInvocationHandler}
 *
 * @author photowey
 * @date 2021/11/11
 * @since 1.0.0
 */
public abstract class AbstractInvocationHandler {

    protected static final String TO_STRING = "toString";
    protected static final String HASH_CODE = "hashCode";
    protected static final String EQUALS = "equals";

    protected Class<?> targetType;
    protected AnnotationAttributes annotationAttributes;
    protected ApplicationContext applicationContext;

    public AbstractInvocationHandler(Class<?> targetType, AnnotationAttributes annotationAttributes, ApplicationContext applicationContext) {
        this.targetType = targetType;
        this.annotationAttributes = annotationAttributes;
        this.applicationContext = applicationContext;
    }

    public Object doInvoke(Object proxy, Method method, Object[] args) throws Throwable {
        Map<String, DynamicInvokeHandler> handlers = applicationContext.getBeansOfType(DynamicInvokeHandler.class);
        String action = Objects.requireNonNull(annotationAttributes).getString("value");
        String[] candidates = annotationAttributes.getStringArray("candidates");

        handlers.forEach((k, handler) -> {
            if (handler.supports(action)) {
                handler.invoke(proxy, method, args, targetType, candidates);
            }
        });

        return null;
    }
}
