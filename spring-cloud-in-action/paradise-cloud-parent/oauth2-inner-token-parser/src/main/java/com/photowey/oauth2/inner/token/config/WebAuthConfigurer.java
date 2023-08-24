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
package com.photowey.oauth2.inner.token.config;

import com.photowey.oauth2.inner.token.security.filter.AccessTokenFilter;
import com.photowey.oauth2.inner.token.security.parser.AccessTokenParser;
import com.photowey.oauth2.inner.token.security.resolver.AuthUserArgumentResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code WebAuthConfigurer}
 *
 * @author photowey
 * @date 2022/01/17
 * @since 1.0.0
 */
@Configuration
public class WebAuthConfigurer {

    @Bean
    @ConditionalOnMissingBean
    public AccessTokenParser accessTokenParser() {
        return new AccessTokenParser();
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessTokenFilter accessTokenFilter() {
        return new AccessTokenFilter(this.accessTokenParser());
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthUserArgumentResolver authUserArgumentResolver() {
        return new AuthUserArgumentResolver();
    }
}
