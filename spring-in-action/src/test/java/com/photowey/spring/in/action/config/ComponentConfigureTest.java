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
package com.photowey.spring.in.action.config;

import com.photowey.spring.in.action.component.ComponentBean;
import com.photowey.spring.in.action.component.ComponentBeanRef;
import com.photowey.spring.in.action.component.ConfigurationBean;
import com.photowey.spring.in.action.component.ConfigurationBeanRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * {@code ComponentConfigureTest}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
@SpringBootTest
class ComponentConfigureTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testComponentBean() {
        ComponentBean componentBean = this.applicationContext.getBean(ComponentBean.class);
        Assertions.assertNotNull(componentBean);
        Assertions.assertEquals("Say hello from:ComponentBean", componentBean.sayHello());
    }

    @Test
    void testComponentBeanRef() {
        ComponentBeanRef componentBeanRef = this.applicationContext.getBean(ComponentBeanRef.class);
        Assertions.assertNotNull(componentBeanRef);
        Assertions.assertEquals("Say hello from:ComponentBeanRef", componentBeanRef.sayHello());

        ComponentBean componentBean = this.applicationContext.getBean(ComponentBean.class);
        Assertions.assertNotNull(componentBean);

        // TODO  hashcode() 不一样
        Assertions.assertFalse(componentBeanRef.getComponentBean().hashCode() == componentBean.hashCode());

    }

    @Test
    void testConfigurationBean() {
        ConfigurationBean configurationBean = this.applicationContext.getBean(ConfigurationBean.class);
        Assertions.assertNotNull(configurationBean);
        Assertions.assertEquals("Say hello from:ConfigurationBean", configurationBean.sayHello());
    }

    @Test
    void testConfigurationBeanRef() {
        ConfigurationBeanRef configurationBeanRef = this.applicationContext.getBean(ConfigurationBeanRef.class);
        Assertions.assertNotNull(configurationBeanRef);
        Assertions.assertEquals("Say hello from:ConfigurationBeanRef", configurationBeanRef.sayHello());

        ConfigurationBean configurationBean = this.applicationContext.getBean(ConfigurationBean.class);
        Assertions.assertNotNull(configurationBean);

        // TODO hashcode() 一样 - 核心思想 - 走 CGLib代理
        Assertions.assertEquals(configurationBeanRef.getConfigurationBean().hashCode(), configurationBean.hashCode());

    }
}