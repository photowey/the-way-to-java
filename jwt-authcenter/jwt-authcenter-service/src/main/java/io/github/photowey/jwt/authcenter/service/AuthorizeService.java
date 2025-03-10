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

import io.github.photowey.jwt.authcenter.core.domain.dto.AuthorizeLogoutDTO;
import io.github.photowey.jwt.authcenter.core.domain.dto.TokenDTO;
import io.github.photowey.jwt.authcenter.core.domain.payload.AuthorizeLogoutPayload;
import io.github.photowey.jwt.authcenter.core.domain.payload.AuthorizePayload;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * {@code AuthorizeService}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/10
 */
public interface AuthorizeService extends BeanFactoryAuthorizeService {

    /**
     * 获取事件线程池
     * |- 名称 {@code eventAsyncExecutor}
     *
     * @return {@link ThreadPoolTaskExecutor}
     */
    default ThreadPoolTaskExecutor eventAsyncExecutor() {
        return this.beanFactory().getBean("eventAsyncExecutor", ThreadPoolTaskExecutor.class);
    }

    /**
     * 认证
     * |- 登录
     *
     * @param payload {@link AuthorizePayload} 实例
     * @return 令牌 {@link TokenDTO} 实例
     */
    TokenDTO authorize(AuthorizePayload payload);

    /**
     * 认证
     * |- 登出
     *
     * @param payload {@link AuthorizeLogoutPayload} 实例
     * @return {@link AuthorizeLogoutDTO} 实例
     */
    AuthorizeLogoutDTO logout(AuthorizeLogoutPayload payload);

    // ----------------------------------------------------------------

    default long now() {
        return System.currentTimeMillis();
    }

    default String jwtId() {
        throw new UnsupportedOperationException("Unsupported now");
    }

    default String audit(String platform) {
        throw new UnsupportedOperationException("Unsupported now");
    }
}

