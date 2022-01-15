package com.photowey.oauth2.authentication.server.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code AuthenticationSuccessHandlerImpl}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private AuthenticationEntryPoint authenticationEntryPoint;

    public AuthenticationSuccessHandlerImpl(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    }
}
