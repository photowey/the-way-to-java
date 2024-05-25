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
package com.photowey.spring.in.action.ctx.holder;

import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code ApplicationContextHolder}
 *
 * @author photowey
 * @date 2023/09/06
 * @since 1.0.0
 */
public enum ApplicationContextHolder {

    /**
     * Instance spring {@link org.springframework.context.ConfigurableApplicationContext} holder.
     */
    INSTANCE;

    /**
     * {@link org.springframework.context.ConfigurableApplicationContext}.
     */
    private ConfigurableApplicationContext applicationContext;

    private final Map<Class<?>, Object> sharedObjects = new HashMap<>();

    public void applicationContext(final ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ConfigurableApplicationContext applicationContext() {
        return this.applicationContext;
    }

    /**
     * 不推荐直接使用 {@code BeanName} 的方式直接依赖查找
     * |- 除非开发者自己非常确定只有一个实例被 {@code IOC} 管理
     *
     * @param beanName 实例名称
     * @param <T>      T 类型
     * @return T 实例
     */
    public <T> T getBean(String beanName) {
        return (T) this.applicationContext.getBean(beanName);
    }

    public <T> T getBean(String beanName, Class<T> requiredType) {
        return this.applicationContext.getBean(beanName, requiredType);
    }

    public <T> void setSharedObject(Class<T> sharedType, T t) {
        this.sharedObjects.put(sharedType, t);
    }

    public <T> T getSharedObject(Class<T> sharedType) {
        Object target = this.sharedObjects.get(sharedType);
        if (null == target) {
            target = this.applicationContext.getBean(sharedType);
        }

        return (T) target;
    }

    public static void cleanSharedObjects() {
        INSTANCE.sharedObjects.clear();
    }
}

