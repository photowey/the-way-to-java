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
package io.github.photowey.jwt.authcenter.jwt.loader;

import io.github.photowey.jwt.authcenter.core.cache.AuthorizedCache;
import io.github.photowey.jwt.authcenter.property.SecurityProperties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.Ordered;

/**
 * {@code AuthorizedCacheLoader}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/23
 */
public interface AuthorizedCacheLoader extends BeanFactoryAware, Ordered {

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
     * 获取配置的权限加载器名称
     *
     * @return 权限加载器名称
     */
    default String loader() {
        return this.securityProperties().auth().loader().name();
    }

    /**
     * 是否支持权限集加载
     *
     * @return {@code boolean} true: 支持; false: 不支持
     */
    boolean supports();

    /**
     * 加载权限
     *
     * @param userId {@link AuthorizedCache}
     * @return {@link AuthorizedCache} 认证用户权限集
     */
    AuthorizedCache load(Long userId);
}

