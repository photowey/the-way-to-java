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
package com.photowey.oauth2.authentication.server.filter;

import com.photowey.oauth2.authentication.server.handler.AuthenticationFailureHandlerImpl;
import com.photowey.oauth2.authentication.server.handler.AuthenticationSuccessHandlerImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * {@code OAuthServerClientCredentialsTokenEndpointFilter}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
public class OAuthServerClientCredentialsTokenEndpointFilter extends ClientCredentialsTokenEndpointFilter {

    private final AuthorizationServerSecurityConfigurer securityConfigurer;
    private AuthenticationEntryPoint authenticationEntryPoint;

    public OAuthServerClientCredentialsTokenEndpointFilter(AuthorizationServerSecurityConfigurer securityConfigurer) {
        this.securityConfigurer = securityConfigurer;
    }

    @Override
    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected AuthenticationManager getAuthenticationManager() {
        return securityConfigurer.and().getSharedObject(AuthenticationManager.class);
    }

    @Override
    public void afterPropertiesSet() {
        setAuthenticationFailureHandler(this.authenticationFailureHandler());
        setAuthenticationSuccessHandler(this.authenticationSuccessHandler());
    }

    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandlerImpl(authenticationEntryPoint);
    }

    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl(authenticationEntryPoint);
    }
}
