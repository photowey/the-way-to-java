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
package com.photowey.spring.cloud.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.StringUtils;
import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import com.photowey.oauth2.authentication.jwt.model.ResponseModel;
import com.photowey.oauth2.authentication.jwt.model.token.InnerToken;
import com.photowey.spring.cloud.gateway.constant.GatewayConstants;
import com.photowey.spring.cloud.gateway.property.OAuth2GatewayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * {@code GlobalAuthenticationFilter}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Slf4j
@Component
public class GlobalAuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private OAuth2GatewayProperties gatewayProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().value();
        if (this.checkUrls(gatewayProperties.getIgnoreUrls(), requestUrl)) {
            return chain.filter(exchange);
        }
        String tokenHeader = this.parseAccessToken(exchange);
        if (StringUtils.isBlank(tokenHeader)) {
            return this.invalidTokenMono(exchange);
        }

        try {
            OAuth2AccessToken accessToken = this.tokenStore.readAccessToken(tokenHeader);
            Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
            String jti = additionalInformation.get(TokenConstants.TOKEN_JTI).toString();
            // black_list
            Boolean blackAt = this.stringRedisTemplate.hasKey(TokenConstants.JTI_KEY_PREFIX + jti);
            if (null != blackAt && blackAt) {
                return this.invalidTokenMono(exchange);
            }
            // {@link com.photowey.oauth2.authentication.server.config.AccessTokenConfigure.JwtAccessTokenEnhancer}
            String userName = additionalInformation.get(TokenConstants.TOKEN_USER_NAME_UNDERLINE).toString();
            List<String> authorities = (List<String>) additionalInformation.get(TokenConstants.TOKEN_AUTHORITIES_FULL);
            if (CollectionUtils.isEmpty(authorities)) {
                authorities = new ArrayList<>(0);
            }
            String userId = additionalInformation.get(TokenConstants.TOKEN_USER_ID).toString();
            InnerToken innerToken = this.populateInnerToken(accessToken, jti, userName, authorities, userId);
            String innerTokenBase64 = this.populatePassport(innerToken);
            // 网关重新颁发-新的 inner-token 到下游服务
            ServerHttpRequest tokenRequest = exchange.getRequest().mutate()
                    .header(TokenConstants.INNER_TOKEN_HEADER, innerTokenBase64)
                    // 移除 {@code Authorization} 请求头 - 后端服务不接受任何 带有: {@code Authorization} 请求头的请求
                    .header(TokenConstants.JWT_TOKEN_HEADER, "")
                    // 标记该请求 来自于网关
                    .header(TokenConstants.GATEWAY_SYMBOL_HEADER, TokenConstants.GATEWAY_SYMBOL_HEADER_VALUE + this.uuid())
                    .build();
            ServerWebExchange build = exchange.mutate().request(tokenRequest).build();

            return chain.filter(build);
        } catch (InvalidTokenException e) {
            log.error("invalid token:{}", tokenHeader, e);
            return this.invalidTokenMono(exchange);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }

    private String populatePassport(InnerToken innerToken) {
        String passport = JSON.toJSONString(innerToken);
        // BASE64 后传递到服务-未加密
        passport = Base64Utils.encodeToString(passport.getBytes(StandardCharsets.UTF_8));

        // 网关认证后统一颁发新的: 内部: TOKEN
        return TokenConstants.GATEWAY_ISSUE_TOKEN_PREFIX + passport;
    }

    private boolean checkUrls(List<String> urls, String path) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String url : urls) {
            if (pathMatcher.match(url, path)) {
                return true;
            }
        }

        return false;
    }

    private String parseAccessToken(ServerWebExchange exchange) {
        String authorization = exchange.getRequest().getHeaders().getFirst(GatewayConstants.AUTHORIZATION_HEADER);
        if (StringUtils.isBlank(authorization)) {
            return null;
        }
        String accessToken = authorization.split(" ")[1];
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }

        return accessToken;
    }

    private Mono<Void> invalidTokenMono(ServerWebExchange exchange) {
        // TODO 后续-统一整理: 状态码和业务码
        return this.buildReturnMono(new ResponseModel(401, "4001", "无效的token", null), exchange);
    }

    private Mono<Void> buildReturnMono(ResponseModel responseModel, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        byte[] bytes = JSON.toJSONString(responseModel).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(GatewayConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);

        return response.writeWith(Mono.just(buffer));
    }

    private InnerToken populateInnerToken(OAuth2AccessToken accessToken, String jti, String userName, List<String> authorities, String userId) {
        InnerToken innerToken = new InnerToken();
        innerToken.setPp(userName);
        innerToken.setUi(userId);
        innerToken.setJti(jti);
        innerToken.setAu(String.join(",", authorities));
        innerToken.setEi(accessToken.getExpiresIn());
        return innerToken;
    }

    private String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
