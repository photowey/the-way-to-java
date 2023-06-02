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
package com.photowey.mybatis.in.action.plugin.chatgpt.reloader;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * {@code BeanRegistrar}
 *
 * @author photowey
 * @date 2023/06/03
 * @since 1.0.0
 */
@Component
@Accessors(fluent = true)
public class BeanRegistrar {

    @Getter
    private final ConfigurableApplicationContext applicationContext;
    @Getter
    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public BeanRegistrar(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.beanDefinitionRegistry = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
    }

    public void registerBean(String beanName, Object beanInstance) {
        BeanDefinition beanDefinition = new RootBeanDefinition(beanInstance.getClass());
        beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
        applicationContext.getBeanFactory().registerSingleton(beanName, beanInstance);
    }
}