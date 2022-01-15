package com.photowey.oauth2.authentication.server.config;

import com.photowey.oauth2.authentication.server.exception.OAuthServerAuthenticationEntryPoint;
import com.photowey.oauth2.authentication.server.exception.OAuthServerWebResponseExceptionTranslator;
import com.photowey.oauth2.authentication.server.filter.OAuthServerClientCredentialsTokenEndpointFilter;
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
@EnableAuthorizationServer
public class AuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {

    private final TokenStore tokenStore;
    private final ClientDetailsService clientDetailsService;
    private final AuthenticationManager authenticationManager;
    private final DataSource dataSource;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;
    private final OAuthServerAuthenticationEntryPoint authenticationEntryPoint;

    public AuthorizationServerConfigure(
            TokenStore tokenStore,
            ClientDetailsService clientDetailsService,
            AuthenticationManager authenticationManager,
            DataSource dataSource,
            JwtAccessTokenConverter jwtAccessTokenConverter,
            OAuthServerAuthenticationEntryPoint authenticationEntryPoint) {
        this.tokenStore = tokenStore;
        this.clientDetailsService = clientDetailsService;
        this.authenticationManager = authenticationManager;
        this.dataSource = dataSource;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 24 * 3);
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);
        tokenServices.setTokenEnhancer(jwtAccessTokenConverter);

        return tokenServices;
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        OAuthServerClientCredentialsTokenEndpointFilter tokenEndpointFilter = new OAuthServerClientCredentialsTokenEndpointFilter(security);
        tokenEndpointFilter.afterPropertiesSet();
        security.addTokenEndpointAuthenticationFilter(tokenEndpointFilter);
        security.authenticationEntryPoint(authenticationEntryPoint)
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .exceptionTranslator(new OAuthServerWebResponseExceptionTranslator())
                .tokenServices(this.tokenServices())
                .authorizationCodeServices(this.authorizationCodeServices())
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }
}
