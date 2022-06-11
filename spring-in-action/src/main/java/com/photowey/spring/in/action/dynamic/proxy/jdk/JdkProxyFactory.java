/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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

import com.photowey.spring.in.action.dynamic.proxy.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Proxy;

/**
 * {@code JdkProxyFactory}
 *
 * @author photowey
 * @date 2021/11/11
 * @since 1.0.0
 */
public class JdkProxyFactory implements ProxyFactory {

    private static final String JDK_PROXY = "jdk";

    @Override
    public boolean supports(String type) {
        return JDK_PROXY.equalsIgnoreCase(type);
    }

    @Override
    public <T> T buildProxy(AnnotationAttributes annotationAttributes, Class<T> targetType, ApplicationContext applicationContext) {
        return (T) Proxy.newProxyInstance(
                ClassUtils.getDefaultClassLoader(),
                new Class[]{targetType},
                new JdkInvocationHandler(targetType, annotationAttributes, applicationContext)
        );
    }
}
