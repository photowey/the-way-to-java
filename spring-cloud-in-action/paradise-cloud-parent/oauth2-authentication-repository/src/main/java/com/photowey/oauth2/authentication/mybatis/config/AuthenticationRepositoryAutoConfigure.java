package com.photowey.oauth2.authentication.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * {@code AuthenticationRepositoryAutoConfigure}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Configuration
@MapperScan("com.photowey.oauth2.authentication.mybatis.repository")
public class AuthenticationRepositoryAutoConfigure {
}
