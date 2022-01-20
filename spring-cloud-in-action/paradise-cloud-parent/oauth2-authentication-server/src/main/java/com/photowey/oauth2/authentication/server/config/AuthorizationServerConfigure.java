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
package com.photowey.oauth2.authentication.server.config;

import com.photowey.oauth2.authentication.jwt.model.oauth2.OAuth2JksProperties;
import com.photowey.oauth2.authentication.server.exception.OAuthServerAuthenticationEntryPoint;
import com.photowey.oauth2.authentication.server.exception.OAuthServerWebResponseExceptionTranslator;
import com.photowey.oauth2.authentication.server.filter.OAuthServerClientCredentialsTokenEndpointFilter;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/**
 * {@code AuthorizationServerConfigure}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
@EnableConfigurationProperties(value = {OAuth2JksProperties.class})
public class AuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {

    private final TokenStore tokenStore;
    private final ClientDetailsService clientDetailsService;
    private final AuthenticationManager authenticationManager;
    private final DataSource dataSource;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;
    private final OAuthServerAuthenticationEntryPoint authenticationEntryPoint;
    private final OAuth2JksProperties oAuth2JksProperties;

    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setTokenEnhancer(jwtAccessTokenConverter);
        tokenServices.setSupportRefreshToken(this.oAuth2JksProperties.isSupportRefreshToken());
        tokenServices.setAccessTokenValiditySeconds(this.oAuth2JksProperties.getAccessTokenValiditySeconds());
        tokenServices.setRefreshTokenValiditySeconds(this.oAuth2JksProperties.getRefreshTokenValiditySeconds());

        return tokenServices;
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new OAuthServerWebResponseExceptionTranslator();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        OAuthServerClientCredentialsTokenEndpointFilter tokenEndpointFilter = new OAuthServerClientCredentialsTokenEndpointFilter(security);
        tokenEndpointFilter.afterPropertiesSet();
        security.addTokenEndpointAuthenticationFilter(tokenEndpointFilter);
        security.authenticationEntryPoint(authenticationEntryPoint)
                .tokenKeyAccess("permitAll()")
                //.checkTokenAccess("permitAll()");
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.exceptionTranslator(this.webResponseExceptionTranslator())
                .tokenServices(this.tokenServices())
                .authorizationCodeServices(this.authorizationCodeServices())
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }
}
