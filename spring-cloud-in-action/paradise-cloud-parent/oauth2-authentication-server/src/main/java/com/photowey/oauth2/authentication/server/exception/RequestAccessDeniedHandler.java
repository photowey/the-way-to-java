/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.oauth2.authentication.server.exception;

import com.photowey.oauth2.authentication.jwt.model.ResponseModel;
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