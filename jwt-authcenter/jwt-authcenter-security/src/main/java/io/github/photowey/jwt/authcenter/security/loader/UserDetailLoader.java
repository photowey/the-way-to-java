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
package io.github.photowey.jwt.authcenter.security.loader;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.Ordered;

/**
 * {@code UserDetailLoader}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/13
 */
public interface UserDetailLoader extends BeanFactoryAware, Ordered {

    /**
     * 是否支持加载用户信息
     *
     * @param proxy 用户信息代理
     * @return {@code boolean} true: 支持: false: 不支持
     */
    boolean supports(String proxy);

    /**
     * 获取 {@code BeanFactory} 实例
     *
     * @return {@link BeanFactory}
     */
    BeanFactory beanFactory();
}

