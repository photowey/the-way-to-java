/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.boot.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author qilong.zql
 * @since 2.5.2
 */
public class PlaceHolderAnnotationInvocationHandler implements InvocationHandler {

    private final Annotation delegate;

    private final PlaceHolderBinder binder;

    private PlaceHolderAnnotationInvocationHandler(Annotation delegate, PlaceHolderBinder binder) {
        this.delegate = delegate;
        this.binder = binder;
    }

    private boolean isObjectMethod(Method method) {
        return method.getDeclaringClass().isAssignableFrom(Object.class);
    }

    private boolean isNotObjectMethod(Method method) {
        return !this.isObjectMethod(method);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(delegate, args);
        if (this.isNotObjectMethod(method) && this.isAttributeMethod(method)) {
            return this.resolvePlaceHolder(ret);
        }

        return ret;
    }

    private boolean isAttributeMethod(Method method) {
        return method != null && method.getParameterTypes().length == 0 && method.getReturnType() != void.class;
    }

    public Object resolvePlaceHolder(Object origin) {
        if (origin.getClass().isArray()) {
            int length = Array.getLength(origin);
            Object ret = Array.newInstance(origin.getClass().getComponentType(), length);
            for (int i = 0; i < length; ++i) {
                Array.set(ret, i, this.resolvePlaceHolder(Array.get(origin, i)));
            }
            return ret;
        } else {
            return this.doResolvePlaceHolder(origin);
        }
    }

    private Object doResolvePlaceHolder(Object origin) {
        if (origin instanceof String) {
            return binder.bind((String) origin);
        } else if (origin instanceof Annotation && !(origin instanceof AnnotationWrapper)) {
            return AnnotationWrapperBuilder.wrap((Annotation) origin).withBinder(binder).build();
        } else {
            return origin;
        }
    }

    public static class AnnotationWrapperBuilder<A extends Annotation> {

        private Annotation delegate;
        private PlaceHolderBinder binder;

        private AnnotationWrapperBuilder() {
        }

        public static <A extends Annotation> AnnotationWrapperBuilder<A> wrap(A annotation) {
            AnnotationWrapperBuilder<A> builder = new AnnotationWrapperBuilder<>();
            builder.delegate = annotation;
            return builder;
        }

        public AnnotationWrapperBuilder<A> withBinder(PlaceHolderBinder binder) {
            this.binder = binder;
            return this;
        }

        @SuppressWarnings("unchecked")
        public A build() {
            if (delegate != null) {
                ClassLoader classLoader = this.getClass().getClassLoader();
                Class<?>[] exposedInterface = {delegate.annotationType(), AnnotationWrapper.class};
                return (A) Proxy.newProxyInstance(classLoader, exposedInterface, new PlaceHolderAnnotationInvocationHandler(delegate, binder));
            }

            return null;
        }
    }
}
