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

import com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.runtime.factory.BeanLoadCostBeanFactory;
import com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.runtime.spring.async.AsyncInitBeanHolder;
import com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.runtime.spring.async.AsyncTaskExecutor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.PriorityOrdered;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

/**
 * @author qilong.zql
 * @author xuanbei
 * @since 2.6.0
 */
public class AsyncProxyBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware, InitializingBean, PriorityOrdered {

    private ApplicationContext applicationContext;

    private String moduleName;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        String methodName = AsyncInitBeanHolder.getAsyncInitMethodName(moduleName, beanName);
        if (methodName == null || methodName.length() == 0) {
            return bean;
        }

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTargetClass(bean.getClass());
        proxyFactory.setProxyTargetClass(true);
        AsyncInitializeBeanMethodInvoker asyncInitializeBeanMethodInvoker = new AsyncInitializeBeanMethodInvoker(bean, beanName, methodName);
        proxyFactory.addAdvice(asyncInitializeBeanMethodInvoker);

        return proxyFactory.getProxy();
    }

    @Override
    public void afterPropertiesSet() {
        ConfigurableListableBeanFactory beanFactory = ((AbstractApplicationContext) applicationContext).getBeanFactory();
        if (beanFactory instanceof BeanLoadCostBeanFactory) {
            moduleName = ((BeanLoadCostBeanFactory) beanFactory).getId();
        } else {
            moduleName = "RootApplicationContext";
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    class AsyncInitializeBeanMethodInvoker implements MethodInterceptor {

        private final Object targetObject;
        private final String asyncMethodName;
        private final String beanName;

        private final CountDownLatch initCountDownLatch = new CountDownLatch(1);

        // mark async-init method is during first invocation.
        private volatile boolean isAsyncCalling = false;
        // mark init-method is called.
        private volatile boolean isAsyncCalled = false;

        AsyncInitializeBeanMethodInvoker(Object targetObject, String beanName, String methodName) {
            this.targetObject = targetObject;
            this.beanName = beanName;
            this.asyncMethodName = methodName;
        }

        @Override
        public Object invoke(final MethodInvocation invocation) throws Throwable {
            // if the spring refreshing is finished
            if (AsyncTaskExecutor.isStarted()) {
                return invocation.getMethod().invoke(targetObject, invocation.getArguments());
            }

            Method method = invocation.getMethod();
            final String methodName = method.getName();
            if (!isAsyncCalled && methodName.equals(asyncMethodName)) {
                isAsyncCalled = true;
                isAsyncCalling = true;
                AsyncTaskExecutor.submitTask(applicationContext.getEnvironment(), () -> {
                    try {
                        invocation.getMethod().invoke(targetObject, invocation.getArguments());
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    } finally {
                        initCountDownLatch.countDown();
                        isAsyncCalling = false;
                    }
                });
                return null;
            }

            if (isAsyncCalling) {
                initCountDownLatch.await();
            }

            return invocation.getMethod().invoke(targetObject, invocation.getArguments());
        }
    }

}
