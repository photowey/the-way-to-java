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
package com.photowey.spring.security.oauth2.pkce.client.controller;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.util.JSONObjectUtils;
import com.photowey.spring.security.oauth2.pkce.client.resolver.JwkResolver;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * {@code JwkController}
 *
 * @author photowey
 * @date 2022/09/03
 * @since 1.0.0
 */
@RestController
public class JwkController {

    private final JwkResolver jwkResolver;

    public JwkController(JwkResolver jwkResolver) {
        this.jwkResolver = jwkResolver;
    }

    @SneakyThrows
    @GetMapping(value = "/oauth2/jwks")
    public Map<String, Object> jwks() {
        JWK jwk = this.jwkResolver.apply();
        JWKSet jwkSet = new JWKSet(jwk);

        return JSONObjectUtils.parse(jwkSet.toString());
    }
}
