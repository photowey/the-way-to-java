package com.photowey.spring.in.action.di.annotation;

import java.lang.annotation.*;

/**
 * {@code Injected}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Injected {
}
