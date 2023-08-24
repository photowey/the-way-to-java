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
package com.photowey.spring.security.oauth2.oidc.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * {@code SecurityConfigurer}
 *
 * @author photowey
 * @date 2022/08/21
 * @since 1.0.0
 */
@Configuration
public class SecurityConfigurer {

    private static final String PROVIDER_ID = "photowey";

    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                .anyRequest().authenticated()
            .and()
                .oauth2Login(oauth2clientLogin -> oauth2clientLogin.loginPage("/oauth2/authorization/" + PROVIDER_ID));
        // @formatter:on

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer ignore() {
        return web -> web.ignoring().antMatchers("/favicon.ico");
    }
}
