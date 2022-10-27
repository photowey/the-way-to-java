package com.photowey.mongo.in.action.annotation;

import java.lang.annotation.*;

/**
 * {@code DocumentId}
 *
 * @author photowey
 * @date 2022/10/26
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DocumentId {

    // 表征: 该注解修饰的字段是: 主键标识
}
