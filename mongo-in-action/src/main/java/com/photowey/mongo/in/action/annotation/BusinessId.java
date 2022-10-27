package com.photowey.mongo.in.action.annotation;

import java.lang.annotation.*;

/**
 * {@code BusinessId}
 *
 * @author photowey
 * @date 2022/10/26
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BusinessId {

    // 表征: 该注解修饰的字段是: 业务主键标识
}
