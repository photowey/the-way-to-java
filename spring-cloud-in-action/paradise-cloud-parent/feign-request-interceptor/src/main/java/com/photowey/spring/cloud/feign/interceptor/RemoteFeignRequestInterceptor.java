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
package com.photowey.spring.cloud.feign.interceptor;

import com.alibaba.fastjson.JSON;
import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import com.photowey.oauth2.authentication.jwt.model.AuthUser;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@code RemoteFeignRequestInterceptor}
 *
 * @author photowey
 * @date 2022/01/16
 * @since 1.0.0
 */
@Slf4j
public class RemoteFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest request = getRequest();
        this.copyHeaderIfNecessary(template, request);

        this.applyPassport(template, request);
    }

    public void copyHeaderIfNecessary(RequestTemplate template, HttpServletRequest request) {
        // TODO 条件判断-是否需要进行-header 传递
        if (this.determineProceed()) {
            Map<String, String> headers = this.parseHeaders(request);
            headers.forEach(template::header);
        }
    }

    public boolean determineProceed() {
        return true;
    }

    public void applyPassport(RequestTemplate requestTemplate, HttpServletRequest request) {
        String headerName = TokenConstants.GATEWAY_TOKEN_NAME;
        if (requestTemplate.headers().containsKey(headerName)) {
            return;
        }

        // 解析-传递到下游
        String passport = request.getHeader(headerName);
        // 当前服务没有接收到上游服务发送的用户请求头,但是有用户(网关?mock? 手动创建?)
        if (ObjectUtils.isEmpty(passport)) {
            AuthUser authUser = (AuthUser) request.getAttribute(TokenConstants.AUTH_USER_KEY);
            if (null != authUser) {
                passport = this.populatePassport(authUser);
            }
        }

        if (StringUtils.hasText(passport)) {
            requestTemplate.header(headerName, passport);
        }
    }

    private String populatePassport(AuthUser authUser) {
        String passport = JSON.toJSONString(authUser);
        // BASE64 后传递到服务-未加密
        passport = Base64Utils.encodeToString(passport.getBytes(StandardCharsets.UTF_8));

        // 采用网关认证后统一颁发的: 内部TOKEN
        return TokenConstants.GATEWAY_TOKEN_PREFIX + passport;
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
    }

    private Map<String, String> parseHeaders(HttpServletRequest request) {
        Map<String, String> headers = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (Objects.nonNull(enumeration)) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                headers.put(key, value);
            }
        }

        return headers;
    }
}
