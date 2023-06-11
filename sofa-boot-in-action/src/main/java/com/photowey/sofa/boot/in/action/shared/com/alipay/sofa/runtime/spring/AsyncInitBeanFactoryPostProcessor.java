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
package com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.runtime.spring;

import com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.boot.annotation.PlaceHolderAnnotationInvocationHandler;
import com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.boot.annotation.PlaceHolderBinder;
import com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.boot.util.BeanDefinitionUtil;
import com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.runtime.api.annotation.SofaAsyncInit;
import com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.runtime.factory.BeanLoadCostBeanFactory;
import com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.runtime.spring.async.AsyncInitBeanHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.StandardMethodMetadata;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author huzijie
 * @version AsyncInitBeanFactoryPostProcessor.java, v 0.1 2022年03月25日 2:08 PM huzijie Exp $
 */
public class AsyncInitBeanFactoryPostProcessor implements BeanFactoryPostProcessor, ApplicationContextAware, EnvironmentAware {

    public static final String ROOT_APPLICATION_CONTEXT = "RootApplicationContext";
    private final PlaceHolderBinder binder = new DefaultPlaceHolderBinder();

    private ConfigurableListableBeanFactory beanFactory;
    private Environment environment;
    private String moduleName;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        this.doScan();
    }

    private void doScan() {
        Arrays.stream(this.beanFactory.getBeanDefinitionNames())
                .collect(Collectors.toMap(Function.identity(), this.beanFactory::getBeanDefinition))
                .forEach(this::scanAsyncInitBeanDefinition);
    }

    /**
     * {@link ScannedGenericBeanDefinition}
     * {@link AnnotatedGenericBeanDefinition}
     * {@link GenericBeanDefinition}
     * {@link org.springframework.beans.factory.support.ChildBeanDefinition}
     * {@link org.springframework.beans.factory.support.RootBeanDefinition}
     */
    private void scanAsyncInitBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        if (BeanDefinitionUtil.isFromConfigurationSource(beanDefinition)) {
            this.scanAsyncInitBeanDefinitionOnMethod(beanId, (AnnotatedBeanDefinition) beanDefinition);
        } else {
            Class<?> beanClassType = BeanDefinitionUtil.resolveBeanClassType(beanDefinition);
            if (beanClassType == null) {
                return;
            }

            this.scanAsyncInitBeanDefinitionOnClass(beanId, beanClassType, beanDefinition);
        }
    }

    private void scanAsyncInitBeanDefinitionOnMethod(String beanId, AnnotatedBeanDefinition beanDefinition) {
        Class<?> returnType;
        Class<?> declaringClass;
        List<Method> candidateMethods = new ArrayList<>();

        MethodMetadata methodMetadata = beanDefinition.getFactoryMethodMetadata();
        try {
            returnType = ClassUtils.forName(methodMetadata.getReturnTypeName(), null);
            declaringClass = ClassUtils.forName(methodMetadata.getDeclaringClassName(), null);
        } catch (Throwable throwable) {
            // it's impossible to catch throwable here
            return;
        }
        if (methodMetadata instanceof StandardMethodMetadata) {
            candidateMethods.add(((StandardMethodMetadata) methodMetadata).getIntrospectedMethod());
        } else {
            for (Method m : declaringClass.getDeclaredMethods()) {
                // check methodName and return type
                if (!m.getName().equals(methodMetadata.getMethodName())
                        || !m.getReturnType().getTypeName().equals(methodMetadata.getReturnTypeName())) {
                    continue;
                }

                // check bean method
                if (!AnnotatedElementUtils.hasAnnotation(m, Bean.class)) {
                    continue;
                }

                Bean bean = m.getAnnotation(Bean.class);
                Set<String> beanNames = new HashSet<>();
                beanNames.add(m.getName());
                if (bean != null) {
                    beanNames.addAll(Arrays.asList(bean.name()));
                    beanNames.addAll(Arrays.asList(bean.value()));
                }

                // check bean name
                if (!beanNames.contains(beanId)) {
                    continue;
                }

                candidateMethods.add(m);
            }
        }

        if (candidateMethods.size() == 1) {
            SofaAsyncInit asyncInit = candidateMethods.get(0).getAnnotation(SofaAsyncInit.class);
            if (asyncInit == null) {
                asyncInit = returnType.getAnnotation(SofaAsyncInit.class);
            }

            this.registerAsyncInitBean(beanId, asyncInit, beanDefinition);
        } else if (candidateMethods.size() > 1) {
            for (Method m : candidateMethods) {
                if (AnnotatedElementUtils.hasAnnotation(m, SofaAsyncInit.class) || AnnotatedElementUtils.hasAnnotation(returnType, SofaAsyncInit.class)) {
                    throw new FatalBeanException("failed");
                }
            }
        }
    }

    private void scanAsyncInitBeanDefinitionOnClass(String beanId, Class<?> beanClass, BeanDefinition beanDefinition) {
        // See issue: https://github.com/sofastack/sofa-boot/issues/835
        SofaAsyncInit asyncInit = AnnotationUtils.findAnnotation(beanClass, SofaAsyncInit.class);
        this.registerAsyncInitBean(beanId, asyncInit, beanDefinition);
    }

    private void registerAsyncInitBean(String beanId, SofaAsyncInit asyncInit, BeanDefinition beanDefinition) {
        if (asyncInit == null) {
            return;
        }
        asyncInit = PlaceHolderAnnotationInvocationHandler.AnnotationWrapperBuilder.wrap(asyncInit).withBinder(binder).build();
        if (asyncInit.value()) {
            AsyncInitBeanHolder.registerAsyncInitBean(moduleName, beanId, beanDefinition.getInitMethodName());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.moduleName = getModuleName(applicationContext);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private String getModuleName(ApplicationContext applicationContext) {
        BeanFactory beanFactory = ((AbstractApplicationContext) applicationContext).getBeanFactory();
        if (beanFactory instanceof BeanLoadCostBeanFactory) {
            return ((BeanLoadCostBeanFactory) beanFactory).getId();
        }

        return ROOT_APPLICATION_CONTEXT;
    }

    class DefaultPlaceHolderBinder implements PlaceHolderBinder {

        @Override
        public String bind(String text) {
            return environment.resolvePlaceholders(text);
        }
    }

}
