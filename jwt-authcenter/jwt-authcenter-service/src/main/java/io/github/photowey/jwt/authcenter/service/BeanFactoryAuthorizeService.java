/*
 * Copyright © 2025 the original author or authors.
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
package io.github.photowey.jwt.authcenter.service;

import io.github.photowey.jwt.authcenter.property.SecurityProperties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * {@code BeanFactoryAuthorizeService}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/10
 */
public interface BeanFactoryAuthorizeService extends BeanFactoryAware {

    /**
     * 获取 {@link BeanFactory} 实例
     *
     * @return {@link BeanFactory} 实例
     */
    BeanFactory beanFactory();

    /**
     * 获取 {@link SecurityProperties} 实例
     *
     * @return {@link SecurityProperties} 实例
     */
    default SecurityProperties securityProperties() {
        return this.beanFactory().getBean(SecurityProperties.class);
    }

    /**
     * 获取事件线程池
     * |- 名称 {@code eventAsyncExecutor}
     *
     * @return {@link ThreadPoolTaskExecutor}
     */
    default ThreadPoolTaskExecutor eventAsyncExecutor() {
        return this.beanFactory().getBean("eventAsyncExecutor", ThreadPoolTaskExecutor.class);
    }
}
