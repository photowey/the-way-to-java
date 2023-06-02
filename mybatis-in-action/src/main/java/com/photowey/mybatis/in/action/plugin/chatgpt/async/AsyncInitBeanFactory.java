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
package com.photowey.mybatis.in.action.plugin.chatgpt.async;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.util.concurrent.CompletableFuture;

/**
 * {@code AsyncInitBeanFactory}
 *
 * @author photowey
 * @date 2023/06/03
 * @since 1.0.0
 */
public class AsyncInitBeanFactory extends DefaultListableBeanFactory {

    public AsyncInitBeanFactory() {
        super();
    }

    public AsyncInitBeanFactory(BeanFactory parentBeanFactory) {
        super(parentBeanFactory);
    }

    @Override
    protected Object initializeBean(String beanName, Object bean, RootBeanDefinition mbd) {
        Object wrappedBean = super.initializeBean(beanName, bean, mbd);

        // 异步调用invokeInitMethods方法
        asyncInvokeInitMethods(beanName, bean, mbd);

        return wrappedBean;
    }

    protected void asyncInvokeInitMethods(String beanName, Object bean) {
        this.asyncInvokeInitMethods(beanName, bean, null);
    }

    private void asyncInvokeInitMethods(String beanName, Object bean, RootBeanDefinition mbd) {
        // 使用异步方式调用invokeInitMethods方法，可以使用线程池或异步任务等方式实现异步执行
        // 注意处理异步操作的异常情况
        CompletableFuture.runAsync(() -> {
            try {
                invokeInitMethods(beanName, bean, mbd);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }
}