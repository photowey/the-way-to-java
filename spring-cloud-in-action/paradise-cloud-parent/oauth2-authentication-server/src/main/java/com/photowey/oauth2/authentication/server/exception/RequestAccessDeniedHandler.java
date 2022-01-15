package com.photowey.oauth2.authentication.server.exception;

import com.photowey.oauth2.authentication.core.model.ResponseModel;
import com.photowey.oauth2.authentication.server.handler.OAuthServerResponseHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code RequestAccessDeniedHandler}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Component
public class RequestAccessDeniedHandler implements AccessDeniedHandler {

    private final OAuthServerResponseHandler responseHandler;

    public RequestAccessDeniedHandler(OAuthServerResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    /**
     * 403
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        this.responseHandler.walk(response, this.responseModel());
    }

    public ResponseModel responseModel() {
        return new ResponseModel(403, "4003", "客户端认证失败", null);
    }
}