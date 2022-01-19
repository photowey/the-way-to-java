/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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

import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import com.photowey.spring.cloud.gateway.exception.RequestAuthenticationEntryPoint;
import com.photowey.spring.cloud.gateway.exception.handler.RequestAccessDeniedHandler;
import com.photowey.spring.cloud.gateway.filter.RemoveJwtFilter;
import com.photowey.spring.cloud.gateway.filter.cors.CorsFilter;
import com.photowey.spring.cloud.gateway.property.OAuth2GatewayProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * {@code SecurityConfigure}
 *
 * @author photowey
 * @date 2022/01/16
 * @since 1.0.0
 */
@Configuration
@AllArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfigure {

    private final ReactiveAuthorizationManager<AuthorizationContext> authorizationManager;
    private final ReactiveAuthenticationManager tokenAuthenticationManager;

    private final RequestAuthenticationEntryPoint requestAuthenticationEntryPoint;
    private final RequestAccessDeniedHandler requestAccessDeniedHandler;
    private final OAuth2GatewayProperties gatewayProperties;
    private final CorsFilter corsFilter;
    private final RemoveJwtFilter removeJwtFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {

        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(this.jwtAuthenticationConverter());
        // 自定义处理 {@code jwt} 请求头过期或签名错误的结果
        http.oauth2ResourceServer().authenticationEntryPoint(requestAuthenticationEntryPoint);

        // 对白名单路径,直接移除JWT请求头
        http.addFilterBefore(removeJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(tokenAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());
        http.httpBasic().disable()
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(toArray(this.gatewayProperties.getIgnoreUrls(), String.class)).permitAll()
                // 鉴权管理器配置
                .anyExchange().access(authorizationManager)
                .and().exceptionHandling()
                // 处理未授权
                .accessDeniedHandler(requestAccessDeniedHandler)
                // 处理未认证
                .authenticationEntryPoint(requestAuthenticationEntryPoint)
                .and()
                .addFilterAt(corsFilter, SecurityWebFiltersOrder.CORS)
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(TokenConstants.AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    public static <T> T[] toArray(Collection<T> collection, Class<T> componentType) {
        return collection.toArray(newArray(componentType, 0));
    }

    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (T[]) Array.newInstance(componentType, newSize);
    }
}