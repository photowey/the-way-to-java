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
package com.photowey.spring.security.oauth2.privatekey.clent.config;

import com.photowey.spring.security.oauth2.privatekey.clent.resolver.JwkResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.oauth2.client.endpoint.*;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Base64;

/**
 * {@code SecurityConfigure}
 *
 * @author photowey
 * @date 2022/09/25
 * @since 1.0.0
 */
@Configuration
public class SecurityConfigure {

    private final StringKeyGenerator secureKeyGenerator;
    private final JwkResolver jwkResolver;

    public SecurityConfigure(JwkResolver jwkResolver) {
        this.secureKeyGenerator = new Base64StringKeyGenerator(Base64.getUrlEncoder().withoutPadding(), 96);
        this.jwkResolver = jwkResolver;
    }

    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient = this.accessTokenResponseClient();
        http.authorizeRequests((requests) -> requests
                        .antMatchers("/favicon.ico").permitAll()
                        .antMatchers( "/hello/privatekey","/oauth2/jwks").hasAnyAuthority("ROLE_ANONYMOUS", "SCOPE_userinfo")
                .anyRequest().authenticated())
                    .oauth2Login().authorizationEndpoint()
                .and()
                    .tokenEndpoint()
                    .accessTokenResponseClient(accessTokenResponseClient);

        http.oauth2Client()
                .authorizationCodeGrant()
                .accessTokenResponseClient(accessTokenResponseClient);
        // @formatter:on

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer ignore() {
        return web -> web.ignoring().antMatchers("/favicon.ico");
    }

    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        OAuth2AuthorizationCodeGrantRequestEntityConverter grantRequestEntityConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
        NimbusJwtClientAuthenticationParametersConverter<OAuth2AuthorizationCodeGrantRequest> converter = new NimbusJwtClientAuthenticationParametersConverter<>(this.jwkResolver::apply);
        grantRequestEntityConverter.addParametersConverter(converter);
        DefaultAuthorizationCodeTokenResponseClient tokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        tokenResponseClient.setRequestEntityConverter(grantRequestEntityConverter);

        return tokenResponseClient;
    }
}
