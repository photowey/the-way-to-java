package com.photowey.oauth2.authentication.server.exception;

import com.photowey.oauth2.authentication.core.model.ResponseModel;
import com.photowey.oauth2.authentication.server.handler.OAuthServerResponseHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code OAuthServerAuthenticationEntryPoint}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Component
public class OAuthServerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final OAuthServerResponseHandler responseHandler;

    public OAuthServerAuthenticationEntryPoint(OAuthServerResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        this.responseHandler.walk(response, this.responseModel());
    }

    public ResponseModel responseModel() {
        return new ResponseModel(401, "4001", "客户端认证失败", null);
    }
}