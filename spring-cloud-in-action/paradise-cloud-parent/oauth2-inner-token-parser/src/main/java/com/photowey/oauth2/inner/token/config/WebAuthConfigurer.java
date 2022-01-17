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
