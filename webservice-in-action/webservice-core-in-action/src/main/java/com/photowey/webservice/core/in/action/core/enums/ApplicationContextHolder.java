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
package com.photowey.webservice.core.in.action.core.enums;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@code ApplicationContextHolder}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/06
 */
public enum ApplicationContextHolder {

    INSTANCE,

    ;

    private ConfigurableApplicationContext applicationContext;

    // ----------------------------------------------------------------

    public void applicationContext(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ConfigurableApplicationContext applicationContext() {
        return this.applicationContext;
    }

    // ----------------------------------------------------------------

    public <T> T getBean(String beanName) {
        return (T) INSTANCE.applicationContext().getBean(beanName);
    }

    public <T> T getBean(Class<T> clazz) {
        return INSTANCE.applicationContext().getBean(clazz);
    }

    public <T> T getBean(String beanName, Class<T> clazz) {
        return INSTANCE.applicationContext().getBean(beanName, clazz);
    }
}
