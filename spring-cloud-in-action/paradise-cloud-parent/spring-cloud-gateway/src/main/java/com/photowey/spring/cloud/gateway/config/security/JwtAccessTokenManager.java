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
package com.photowey.spring.cloud.gateway.config.security;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import com.photowey.spring.cloud.gateway.property.OAuth2GatewayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * {@code JwtAccessTokenManager}
 *
 * @author photowey
 * @date 2022/01/16
 * @since 1.0.0
 */

@Slf4j
@Primary
@Component
public class JwtAccessTokenManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final RedisTemplate redisTemplate;
    private final OAuth2GatewayProperties gatewayProperties;

    public JwtAccessTokenManager(RedisTemplate redisTemplate, OAuth2GatewayProperties gatewayProperties) {
        this.redisTemplate = redisTemplate;
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        PathMatcher pathMatcher = new AntPathMatcher();
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        // 对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }
        URI uri = request.getURI();
        // 白名单路径直接放行
        List<String> ignoreUrls = this.gatewayProperties.getIgnoreUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }

        String method = authorizationContext.getExchange().getRequest().getMethodValue();
        String combinePath = method + TokenConstants.OAUTH_METHOD_DELIMITER + uri.getPath();
        Map<String, List<String>> entries = this.redisTemplate.opsForHash().entries(TokenConstants.OAUTH_PERMISSION_URLS);
        List<String> authorities = new ArrayList<>();
        entries.forEach((path, roles) -> {
            if (pathMatcher.match(path, combinePath)) {
                authorities.addAll(roles);
            }
        });
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    if (Objects.equals(TokenConstants.ROLE_ROOT_CODE, authority)) {
                        return true;
                    }
                    return CollectionUtils.isNotEmpty(authorities) && authorities.contains(authority);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}