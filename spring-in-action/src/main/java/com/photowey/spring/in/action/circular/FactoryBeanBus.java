/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.spring.in.action.circular;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * {@code FactoryBeanBus}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Slf4j
@Component
public class FactoryBeanBus implements BeanFactoryAware, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        log.info("on application event:{}", ContextRefreshedEvent.class.getName());

        log.info("-->FactoryBeanA:name{}", this.beanFactory.getBean(FactoryBeanA.class).getName());
        log.info("-->FactoryBeanB:name{}", this.beanFactory.getBean(FactoryBeanB.class).getName());
        log.info("-->FactoryBeanC:name{}", this.beanFactory.getBean(FactoryBeanC.class).getName());

        // trigger NullPointerException
        // log.info("-->FactoryBeanA:name{}", this.beanFactory.getBean(FactoryBeanA.class).getFactoryBeanB().getName());
        // log.info("-->FactoryBeanB:name{}", this.beanFactory.getBean(FactoryBeanB.class).getFactoryBeanA().getName());
        // log.info("-->FactoryBeanC:name{}", this.beanFactory.getBean(FactoryBeanC.class).getCircularBeanD().getName());

    }
}
