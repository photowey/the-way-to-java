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
package com.photowey.spring.security.oauth2.oidc.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;

/**
 * {@code SecurityConfigurer}
 *
 * @author photowey
 * @date 2022/08/21
 * @since 1.0.0
 */
@EnableWebSecurity(debug = true)
public class SecurityConfigurer {

    @Autowired
    private Environment environment;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // @formatter:off
        http
            .authorizeRequests(authorizeRequests ->
                        authorizeRequests.antMatchers("/oauth2/userinfo")
                                .hasAnyAuthority("SCOPE_userinfo")
                                .anyRequest()
                                .authenticated()
                ).formLogin()
            .and()
                .oauth2ResourceServer()
                .jwt();
        // @formatter:on

        return http.build();
    }

    @Bean
    public UserDetailsService users() {

        UserDetails user = User.builder()
                .username("sharkchili")
                .password("123456")
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

        String env = environment.getActiveProfiles()[0];
        ClassPathResource resource = new ClassPathResource(String.format("key/%s/jose.crt", env));
        Certificate certificate = certificateFactory.generateCertificate(resource.getInputStream());
        RSAPublicKey publicKey = (RSAPublicKey) certificate.getPublicKey();
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/favicon.ico", "/actuator/**", "/h2-console/**");
    }
}
