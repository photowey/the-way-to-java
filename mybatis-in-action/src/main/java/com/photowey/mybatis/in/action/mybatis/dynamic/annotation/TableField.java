package com.photowey.mybatis.in.action.mybatis.dynamic.annotation;

import java.lang.annotation.*;

/**
 * {@code TableField}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {

    /**
     * 数据表-字段名
     *
     * @return 数据表-字段名
     */
    String value();
}
