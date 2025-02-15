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
package io.github.photowey.jwt.authcenter.jwt.token.api;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * {@code AuthenticationService}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/15
 */
public interface AuthenticationService {

    /**
     * 解析 {@code Token} 获取 {@link UsernamePasswordAuthenticationToken}
     *
     * @param token     令牌
     * @param secretKey 认证密钥
     * @return {@link Authentication}
     */
    Authentication tryAuthentication(String token, String secretKey);

    /**
     * 采用配置的密钥
     * |- 解析 {@code Token} 获取 {@link UsernamePasswordAuthenticationToken}
     *
     * @param token 令牌
     * @return {@link Authentication}
     */
    Authentication tryAuthentication(String token);
}
