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

import com.photowey.oauth2.authentication.core.util.OAuthUtils;
import com.photowey.spring.cloud.gateway.cors.CorsFilter;
import com.photowey.spring.cloud.gateway.exception.RequestAuthenticationEntryPoint;
import com.photowey.spring.cloud.gateway.exception.handler.RequestAccessDeniedHandler;
import com.photowey.spring.cloud.gateway.property.OAuth2GatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.AuthorizationContext;

/**
 * {@code SecurityConfigure}
 *
 * @author photowey
 * @date 2022/01/16
 * @since 1.0.0
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfigure {

    @Autowired
    private ReactiveAuthorizationManager<AuthorizationContext> accessManager;
    @Autowired
    private ReactiveAuthenticationManager tokenAuthenticationManager;

    @Autowired
    private RequestAuthenticationEntryPoint requestAuthenticationEntryPoint;
    @Autowired
    private RequestAccessDeniedHandler requestAccessDeniedHandler;
    @Autowired
    private OAuth2GatewayProperties gatewayProperties;

    @Autowired
    private CorsFilter corsFilter;

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(tokenAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());
        http.httpBasic().disable()
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(OAuthUtils.toArray(this.gatewayProperties.getIgnoreUrls(), String.class)).permitAll()
                .anyExchange().access(accessManager)
                .and()
                .exceptionHandling().authenticationEntryPoint(requestAuthenticationEntryPoint).accessDeniedHandler(requestAccessDeniedHandler)
                .and()
                .addFilterAt(corsFilter, SecurityWebFiltersOrder.CORS)
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }
}