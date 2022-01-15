package com.photowey.oauth2.authentication.service.annotation;

import com.photowey.oauth2.authentication.mybatis.annotation.EnableAuthenticationRepository;
import com.photowey.oauth2.authentication.service.config.AuthenticationServiceAutoConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@code EnableAuthenticationService}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableAuthenticationRepository
@Import(AuthenticationServiceAutoConfigure.class)
public @interface EnableAuthenticationService {
}
