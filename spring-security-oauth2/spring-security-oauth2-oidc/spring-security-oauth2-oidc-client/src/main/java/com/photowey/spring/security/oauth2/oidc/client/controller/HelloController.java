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
package com.photowey.spring.security.oauth2.oidc.client.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * {@code HelloController}
 *
 * @author photowey
 * @date 2022/08/21
 * @since 1.0.0
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("/hello/oidc")
    public ResponseEntity<AuthWrapper> helloOidc(@RegisteredOAuth2AuthorizedClient("photowey") OAuth2AuthorizedClient client) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return new ResponseEntity<>(new AuthWrapper(authentication, client), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<AuthWrapper> index(
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return new ResponseEntity<>(new AuthWrapper(authentication, client), HttpStatus.OK);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthWrapper implements Serializable {

        private static final long serialVersionUID = 3563796847034125063L;

        private Authentication auth;
        private OAuth2AuthorizedClient client;
    }

}
