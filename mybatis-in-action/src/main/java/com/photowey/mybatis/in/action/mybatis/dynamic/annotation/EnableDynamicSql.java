package com.photowey.mybatis.in.action.mybatis.dynamic.annotation;

import com.photowey.mybatis.in.action.mybatis.dynamic.config.DynamicSqlAutoConfigurationImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * {@code EnableDynamicSql}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DynamicSqlAutoConfigurationImportSelector.class)
public @interface EnableDynamicSql {

    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};
}
