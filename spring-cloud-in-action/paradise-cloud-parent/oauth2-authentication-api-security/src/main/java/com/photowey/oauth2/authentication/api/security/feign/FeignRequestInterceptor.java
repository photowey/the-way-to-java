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
package com.photowey.oauth2.authentication.api.security.feign;

import com.alibaba.fastjson.JSON;
import com.photowey.oauth2.authentication.api.security.manager.ServiceAuthorityManager;
import com.photowey.oauth2.authentication.crypto.util.AESUtils;
import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import com.photowey.oauth2.authentication.jwt.model.AuthUser;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * {@code FeignRequestInterceptor}
 *
 * @author photowey
 * @date 2022/01/29
 * @since 1.0.0
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    private final ServiceAuthorityManager serviceAuthorityManager;

    public FeignRequestInterceptor(ServiceAuthorityManager serviceAuthorityManager) {
        this.serviceAuthorityManager = serviceAuthorityManager;
    }

    /**
     * 子类根据-实际情况决定是否需要进行-重写
     */
    public String handleProxyPassportIfNecessary(String passport) {

        return passport;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String innerIssueTokenHeader = TokenConstants.INNER_TOKEN_HEADER;
        if (requestTemplate.headers().containsKey(innerIssueTokenHeader)) {
            return;
        }
        HttpServletRequest request = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes || null == (request = attributes.getRequest())) {
            return;
        }
        String passport = request.getHeader(innerIssueTokenHeader);
        if (StringUtils.isEmpty(passport)) {
            // TODO 没有 header 但是有用户信息 - 可能是: {@code MOCK}
            AuthUser authUser = (AuthUser) request.getAttribute(TokenConstants.AUTH_USER_KEY);
            if (null != authUser) {
                String mockPassport = this.populateMockPassport(authUser);
                // TODO 没有-网关的请求头 = 但是有用户信息 - 可能是 {@code MOCK} 的用户信息
                requestTemplate.header(TokenConstants.SERVICE_USER_HEADER, mockPassport);
            }
        } else {
            // 路由到下游
            String proxyPassport = this.handleProxyPassportIfNecessary(passport);
            requestTemplate.header(TokenConstants.INNER_TOKEN_HEADER, proxyPassport);
        }

        // 处理服务签名
        this.handleServiceSign(requestTemplate);
    }

    private void handleServiceSign(RequestTemplate requestTemplate) {
        String head = this.serviceAuthorityManager.sign();

        requestTemplate.header(TokenConstants.INNER_SIGN_HEADER, head);
    }

    private String populateMockPassport(AuthUser authUser) {
        String passport = JSON.toJSONString(authUser);
        passport = AESUtils.encrypt(TokenConstants.INNER_TOKEN_AES_KEY, passport);

        return TokenConstants.USER_NAME_NOCK_PREFIX + passport;
    }

}
