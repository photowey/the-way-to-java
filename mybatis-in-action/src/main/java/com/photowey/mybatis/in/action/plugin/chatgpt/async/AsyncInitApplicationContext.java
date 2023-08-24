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
package com.photowey.mybatis.in.action.plugin.chatgpt.async;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@code AsyncInitApplicationContext}
 *
 * @author photowey
 * @date 2023/06/03
 * @since 1.0.0
 */
public class AsyncInitApplicationContext extends AnnotationConfigApplicationContext {

    public AsyncInitApplicationContext(Class<?>... annotatedClasses) {
        super(annotatedClasses);
    }

    public AsyncInitApplicationContext(String... basePackages) {
        super(basePackages);
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();

        this.customizeBeanFactory(getDefaultListableBeanFactory());

        try {
            invokeInitMethods();
        } catch (Throwable ex) {
            handleRefreshError(ex);
        }
    }

    protected void invokeInitMethods() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        if (beanFactory instanceof AsyncInitBeanFactory) {
            AsyncInitBeanFactory asyncInitBeanFactory = (AsyncInitBeanFactory) beanFactory;
            String[] beanNames = getBeanDefinitionNames();
            for (String beanName : beanNames) {
                Object bean = beanFactory.getSingleton(beanName);
                asyncInitBeanFactory.asyncInvokeInitMethods(beanName, bean);
            }
        }
    }

    protected void handleRefreshError(Throwable ex) {
        // 处理刷新错误的逻辑
        // 例如日志记录、发送通知等
    }

    protected void customizeBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // 自定义的BeanFactory配置逻辑
    }

    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        super.postProcessBeanFactory(beanFactory);
        // 自定义配置beanFactory的逻辑
    }
}