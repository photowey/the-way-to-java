package com.photowey.oauth2.authentication.server.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code AuthenticationFailureHandlerImpl}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private AuthenticationEntryPoint authenticationEntryPoint;

    public AuthenticationFailureHandlerImpl(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof BadCredentialsException) {
            exception = new BadCredentialsException(exception.getMessage(), new BadClientCredentialsException());
        }
        authenticationEntryPoint.commence(request, response, exception);
    }
}
