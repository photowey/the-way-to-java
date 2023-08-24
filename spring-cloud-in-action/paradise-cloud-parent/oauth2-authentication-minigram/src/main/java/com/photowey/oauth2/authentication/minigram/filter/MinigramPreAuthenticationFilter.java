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
package com.photowey.oauth2.authentication.minigram.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.photowey.oauth2.authentication.minigram.domain.MinigramClient;
import com.photowey.oauth2.authentication.minigram.domain.MinigramSessionKeyResponse;
import com.photowey.oauth2.authentication.minigram.response.ResponseWriter;
import com.photowey.oauth2.authentication.minigram.service.MinigramClientService;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;

/**
 * {@code MinigramPreAuthenticationFilter}
 *
 * @author photowey
 * @date 2022/07/30
 * @since 1.0.0
 */
public class MinigramPreAuthenticationFilter extends OncePerRequestFilter {

    private static final String JSCODE_2_SESSION_ENDPOINT = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String MINIGRAM_CLIENT_ID = "clientId";
    private static final String JS_CODE = "jscode";
    private static final String PREAUTH_ATTRIBUTE_KEY = "minigram.preauth";
    private static final String CACHE_KEY_TEMPLATE = "wechat:minigram:sessionkey:%s:%s";

    private final MinigramClientService minigramClientService;
    private final MinigramSessionKeyCache minigramSessionKeyCache;

    private final RequestMatcher preauthRequestMatcher;
    private final RestTemplate restTemplate;
    private final PreAuthResponseWriter preAuthResponseWriter;

    public MinigramPreAuthenticationFilter(
            MinigramClientService minigramClientService,
            MinigramSessionKeyCache minigramSessionKeyCache) {
        this.minigramClientService = minigramClientService;
        this.minigramSessionKeyCache = minigramSessionKeyCache;

        this.preauthRequestMatcher = new AntPathRequestMatcher("/minigram/preauth", HttpMethod.POST.name());
        this.restTemplate = new RestTemplate();
        this.preAuthResponseWriter = new PreAuthResponseWriter();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (response.isCommitted()) {
            return;
        }

        if (preauthRequestMatcher.matches(request)) {
            String clientId = request.getParameter(MINIGRAM_CLIENT_ID);
            String jscode = request.getParameter(JS_CODE);

            MinigramClient minigramClient = this.minigramClientService.get(clientId);
            MinigramSessionKeyResponse sessionKeyResponse = this.minigramPreAuth(minigramClient, jscode);
            String cacheKey = String.format(CACHE_KEY_TEMPLATE, clientId, sessionKeyResponse.getOpenId());
            this.minigramSessionKeyCache.set(cacheKey, sessionKeyResponse.getSessionKey());

            sessionKeyResponse.setSessionKey(null);
            request.setAttribute(PREAUTH_ATTRIBUTE_KEY, sessionKeyResponse);
            this.preAuthResponseWriter.write(request, response);
        }

        filterChain.doFilter(request, response);
    }

    private MinigramSessionKeyResponse minigramPreAuth(MinigramClient minigramClient, String jscode) throws JsonProcessingException {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("appid", minigramClient.getAppId());
        queryParams.add("secret", minigramClient.getAppSecret());
        queryParams.add("js_code", jscode);
        queryParams.add("grant_type", "authorization_code");

        URI uri = UriComponentsBuilder.fromHttpUrl(JSCODE_2_SESSION_ENDPOINT)
                .queryParams(queryParams)
                .build()
                .toUri();
        MinigramSessionKeyResponse sessionKeyResponse = this.restTemplate.getForObject(uri, MinigramSessionKeyResponse.class);

        if (Objects.isNull(sessionKeyResponse)) {
            throw new BadCredentialsException("minigram response is null");
        }

        return sessionKeyResponse;
    }

    private static class PreAuthResponseWriter extends ResponseWriter {

        @Override
        protected ResponseEntity<?> toBody(HttpServletRequest request) {
            MinigramSessionKeyResponse sessionKeyResponse = (MinigramSessionKeyResponse) request.getAttribute(PREAUTH_ATTRIBUTE_KEY);
            MinigramPreAuthenticated<String> authenticated = MinigramPreAuthenticated.ok(sessionKeyResponse.getOpenId());

            return new ResponseEntity<>(authenticated, HttpStatus.OK);
        }
    }

    @Data
    public static class MinigramPreAuthenticated<T> {
        private Integer status;
        private String code;
        private String message;
        private T data;


        public static <T> MinigramPreAuthenticated<T> ok(T data) {
            MinigramPreAuthenticated<T> authenticated = new MinigramPreAuthenticated<>();
            authenticated.setStatus(200);
            authenticated.setCode("200");
            authenticated.setMessage("ok");
            authenticated.setData(data);

            return authenticated;
        }
    }
}
