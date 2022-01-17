package com.photowey.oauth2.inner.token.annotation;

import com.photowey.oauth2.inner.token.config.WebAuthConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@code EnableHandleInnerToken}
 *
 * @author photowey
 * @date 2022/01/17
 * @since 1.0.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {WebAuthConfigurer.class})
public @interface EnableHandleInnerToken {
}
