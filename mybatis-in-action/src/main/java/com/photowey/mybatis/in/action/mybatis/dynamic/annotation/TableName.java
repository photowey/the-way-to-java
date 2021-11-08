package com.photowey.mybatis.in.action.mybatis.dynamic.annotation;

import java.lang.annotation.*;

/**
 * {@code TableName}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableName {

    /**
     * 映射数据库表名
     *
     * @return 数据表表名
     */
    String value();
}
