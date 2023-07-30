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
package com.photowey.spring.in.action.infras.engine;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * {@code AbstractEngineAwareBeanPostProcessor}
 *
 * @author photowey
 * @date 2023/05/06
 * @since 1.0.0
 */
public abstract class AbstractEngineAwareBeanPostProcessor<E extends Engine, A extends Aware> implements EngineBeanPostProcessor {

    protected ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (this.determineInjects(bean, beanName)) {
            return this.inject(this.beanFactory, bean, beanName);
        }

        return bean;
    }

    public boolean determineInjects(Object bean, String beanName) {
        return false;
    }

    public Object inject(ConfigurableListableBeanFactory beanFactory, Object bean) {
        return bean;
    }

    public Object inject(ConfigurableListableBeanFactory beanFactory, Object bean, String beanName) {
        return this.inject(beanFactory, bean);
    }
}
