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
package io.github.photowey.jjwt.authcenter.jwt.token.api;

import io.github.photowey.jwt.authcenter.jwt.token.api.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.security.core.Authentication;

import java.util.function.Consumer;

/**
 * {@code JwtTokenService}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/23
 */
public interface JwtTokenService extends TokenService {

    /**
     * 解析 {@code token} 的 Claims
     *
     * @param authToken 认证 {@code token}
     * @param secretKey 颁发 {@code token} 的秘钥
     * @return {@link Claims}
     */
    Jws<Claims> parseClaims(String authToken, String secretKey);

    /**
     * 颁发令牌
     * |- 默认: 不记住我
     *
     * @param authentication {@link Authentication} 认证对象
     * @param fx             {@link JwtBuilder} 回调函数
     * @return 令牌
     */
    default String createToken(Authentication authentication, Consumer<JwtBuilder> fx) {
        return this.createToken(authentication, false, fx);
    }

    /**
     * 颁发令牌
     *
     * @param authentication {@link Authentication} 认证对象
     * @param rememberMe     rememberMe 记住我 true: 是; false: 否
     * @param fx             {@link JwtBuilder} 回调函数
     * @return 令牌
     */
    String createToken(Authentication authentication, boolean rememberMe, Consumer<JwtBuilder> fx);
}
