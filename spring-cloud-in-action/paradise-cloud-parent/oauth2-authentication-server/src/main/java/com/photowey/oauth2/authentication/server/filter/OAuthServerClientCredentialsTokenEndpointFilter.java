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
