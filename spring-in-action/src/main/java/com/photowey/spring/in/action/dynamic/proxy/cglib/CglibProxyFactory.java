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
package com.photowey.spring.in.action.dynamic.proxy.cglib;

import com.photowey.spring.in.action.dynamic.proxy.ProxyFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * {@code CglibProxyFactory}
 *
 * @author photowey
 * @date 2021/11/10
 * @since 1.0.0
 */
public class CglibProxyFactory implements ProxyFactory {

    public static final String CGLIB_PROXY = "cglib";

    @Override
    public boolean supports(String type) {
        return CGLIB_PROXY.equals(type);
    }

    @Override
    public <T> T buildProxy(AnnotationAttributes annotationAttributes, Class<T> targetType, ApplicationContext applicationContext) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetType);
        enhancer.setCallback(new CglibInvocationHandler(targetType, annotationAttributes, applicationContext));

        return (T) enhancer.create();
    }
}
