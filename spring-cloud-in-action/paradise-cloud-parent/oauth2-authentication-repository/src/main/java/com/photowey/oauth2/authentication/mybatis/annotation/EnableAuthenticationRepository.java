package com.photowey.oauth2.authentication.mybatis.annotation;

import com.photowey.oauth2.authentication.mybatis.config.AuthenticationRepositoryAutoConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@code EnableAuthenticationRepository}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(AuthenticationRepositoryAutoConfigure.class)
public @interface EnableAuthenticationRepository {
}
