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
package com.photowey.oauth2.authentication.api.security.authenticate.filter;

import com.alibaba.fastjson.JSON;
import com.photowey.oauth2.authentication.api.security.annotation.PrivateApi;
import com.photowey.oauth2.authentication.api.security.annotation.PublicApi;
import com.photowey.oauth2.authentication.api.security.head.TokenHead;
import com.photowey.oauth2.authentication.api.security.head.TokenHeadWrapper;
import com.photowey.oauth2.authentication.api.security.manager.ServiceAuthorityManager;
import com.photowey.oauth2.authentication.api.security.mapping.RequestMappingHandlerMappingExt;
import com.photowey.oauth2.authentication.crypto.util.AESUtils;
import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import com.photowey.oauth2.authentication.jwt.model.AuthUser;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * {@code GlobalAuthenticationFilter}
 *
 * @author photowey
 * @date 2022/01/29
 * @since 1.0.0
 */
public class GlobalAuthenticationFilter extends OncePerRequestFilter {

    private final RequestMappingHandlerMappingExt handlerMappingExt;
    private final ServiceAuthorityManager serviceAuthorityManager;

    public GlobalAuthenticationFilter(
            RequestMappingHandlerMappingExt handlerMappingExt, ServiceAuthorityManager serviceAuthorityManager) {
        this.handlerMappingExt = handlerMappingExt;
        this.serviceAuthorityManager = serviceAuthorityManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(TokenConstants.JWT_TOKEN_HEADER);
        if (StringUtils.hasText(authorizationHeader)) {
            // TODO 401 - 服务-永不接受来自于外界的请求
            //  只能来自于-网关和服务
        }

        HandlerMethod handlerInternal = this.handlerMappingExt.getHandlerExternal(request);
        Method method = Optional.ofNullable(handlerInternal.getMethod()).orElseThrow(() -> new RuntimeException("Can't find the handler-method"));

        boolean handleToken = false;
        String principal = this.checkAndAcquireInnerIssueHeader(request);

        if (method.isAnnotationPresent(PublicApi.class)) {
            // 公共API 所有的均可调用
            // TODO 如果 PublicApi.value() 有值
            if (principal.startsWith(TokenConstants.SERVICE_ISSUE_TOKEN_PREFIX)) {
                PublicApi annotation = method.getAnnotation(PublicApi.class);
                List<String> allowList = Arrays.asList(annotation.value());
                if (!CollectionUtils.isEmpty(allowList)) {
                    // 解析 token
                    TokenHeadWrapper headWrapper = this.serviceAuthorityManager.verify(principal);
                    if (headWrapper.isAuthenticated()) {
                        TokenHead tokenHead = headWrapper.getTokenHead();
                        String serviceName = tokenHead.getServiceName();
                        if (!allowList.contains(serviceName)) {
                            // TODO 没有在 PublicApi.value() 的 白名单中
                            // TODO 401
                        }
                    } else {
                        // TODO 验签失败 401
                    }
                }
            } else {
                // TODO 处理 AuthUser 如果需要
            }
            filterChain.doFilter(request, response);
        } else {
            if (method.isAnnotationPresent(PrivateApi.class)) {
                PrivateApi annotation = method.getAnnotation(PrivateApi.class);
                List<String> services = Arrays.asList(annotation.value());
                handleToken = this.handlePrivateApi(request, principal, services);
            } else {
                handleToken = this.handleNormalApi(request, principal);
            }
            try {
                filterChain.doFilter(request, response);
            } finally {
                if (handleToken) {
                    request.removeAttribute(TokenConstants.AUTH_USER_KEY);
                }
            }
        }
    }

    private boolean handleNormalApi(HttpServletRequest request, String principal) {
        // 什么注解都没有标注
        if (principal.startsWith(TokenConstants.GATEWAY_ISSUE_TOKEN_PREFIX)) {
            // 解析用户
            AuthUser authUser = this.populateAuthUser(principal);
            request.setAttribute(TokenConstants.AUTH_USER_KEY, authUser);
            return true;
        } else {
            // TODO 校验内部 token
            TokenHeadWrapper headWrapper = this.serviceAuthorityManager.verify(principal);
            if (headWrapper.isAuthenticated()) {
                if (this.parseMockPrincipal(request)) {
                    return true;
                }
            } else {
                // TODO 验签失败 401
            }
        }

        return false;
    }

    /**
     * 处理-私有 {@code API}
     *
     * @param request
     * @param principal
     * @param blackList 黑名单
     * @return
     */
    private boolean handlePrivateApi(HttpServletRequest request, String principal, List<String> blackList) {
        if (principal.startsWith(TokenConstants.GATEWAY_ISSUE_TOKEN_PREFIX)) {
            // TODO 401
            // TODO 私有API 只能来自于服务调用 - 不能是网关
        } else {
            // TODO 验证内部服务调用请求头
            TokenHeadWrapper headWrapper = this.serviceAuthorityManager.verify(principal);
            if (headWrapper.isAuthenticated()) {
                if (this.parseMockPrincipal(request)) {
                    return true;
                }
            } else {
                TokenHead tokenHead = headWrapper.getTokenHead();
                String serviceName = tokenHead.getServiceName();
                if (!CollectionUtils.isEmpty(blackList) && blackList.contains(serviceName)) {
                    // TODO 401
                } else {
                    if (this.parseMockPrincipal(request)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 处理 {@code MOCK} 请求头
     *
     * @param request
     * @return
     */
    private boolean parseMockPrincipal(HttpServletRequest request) {
        // TODO 来自于服务 - 但是有用户信息 - 可能是: {@code MOCK}
        String passport = request.getHeader(TokenConstants.SERVICE_USER_HEADER);
        if (StringUtils.hasText(passport)) {
            AuthUser authUser = this.parsePassport(passport);
            request.setAttribute(TokenConstants.AUTH_USER_KEY, authUser);
            return true;
        }

        return false;
    }

    private String checkAndAcquireInnerIssueHeader(HttpServletRequest request) {
        String principal = request.getHeader(TokenConstants.INNER_TOKEN_HEADER);
        // 不管是 网关还是服务调用  必须有该请求头
        if (StringUtils.isEmpty(principal)) {
            // TODO 401
        }

        return principal;
    }

    private AuthUser populateAuthUser(String principal) {
        // 解析 JSON

        // 暂时:
        return this.parsePassport(principal);
    }

    private AuthUser parsePassport(String passport) {
        String passportDecrypt = AESUtils.encrypt(TokenConstants.INNER_TOKEN_AES_KEY, passport);
        String jsonPrincipal = passportDecrypt.replaceAll(TokenConstants.SERVICE_USER_PREFIX, "");
        return JSON.parseObject(jsonPrincipal, AuthUser.class);
    }
}
