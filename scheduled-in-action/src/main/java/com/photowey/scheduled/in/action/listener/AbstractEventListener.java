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
package com.photowey.scheduled.in.action.listener;

import com.photowey.scheduled.in.action.registry.ScheduledTaskRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * {@code AbstractEventListener}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
public abstract class AbstractEventListener<T extends ApplicationEvent>
    implements ApplicationListener<T>, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    public BeanFactory beanFactory() {
        return this.beanFactory;
    }

    public ListableBeanFactory listableBeanFactory() {
        return (ListableBeanFactory) this.beanFactory();
    }

    protected ScheduledTaskRegistry scheduledTaskRegistry() {
        return this.beanFactory().getBean(ScheduledTaskRegistry.class);
    }
}
