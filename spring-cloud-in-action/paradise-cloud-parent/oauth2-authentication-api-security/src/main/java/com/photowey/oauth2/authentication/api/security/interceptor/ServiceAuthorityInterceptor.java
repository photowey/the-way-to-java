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
package com.photowey.oauth2.authentication.api.security.interceptor;

import com.photowey.oauth2.authentication.api.security.head.TokenHeadWrapper;
import com.photowey.oauth2.authentication.api.security.manager.ServiceAuthorityManager;
import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code ServiceAuthorityInterceptor}
 *
 * @author photowey
 * @date 2022/01/29
 * @since 1.0.0
 */
public class ServiceAuthorityInterceptor extends HandlerInterceptorAdapter {

    private final ServiceAuthorityManager serviceAuthorityManager;

    public ServiceAuthorityInterceptor(ServiceAuthorityManager serviceAuthorityManager) {
        this.serviceAuthorityManager = serviceAuthorityManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String signHeader = request.getHeader(TokenConstants.INNER_TOKEN_HEADER);
        if (StringUtils.hasText(signHeader)) {
            // TODO 验证来自于服务调用 - 网关的请求放行
            if (signHeader.startsWith(TokenConstants.SERVICE_ISSUE_TOKEN_PREFIX)) {
                TokenHeadWrapper headWrapper = this.serviceAuthorityManager.verify(signHeader);
                if (!headWrapper.isAuthenticated()) {
                    // TODO 401
                    return false;
                }
            }
        } else {
            // TODO 401
            return false;
        }

        return super.preHandle(request, response, handler);
    }
}