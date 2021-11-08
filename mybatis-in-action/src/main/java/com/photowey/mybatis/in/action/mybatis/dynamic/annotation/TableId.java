package com.photowey.mybatis.in.action.mybatis.dynamic.annotation;

import com.photowey.mybatis.in.action.mybatis.dynamic.enums.IdTypeEnum;

import java.lang.annotation.*;

/**
 * {@code TableId}
 * 标记数据表主键
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableId {

    /**
     * 主键标记
     * 数据库主键字段名称
     *
     * @return 数据库主键字段名称
     */
    String value();

    /**
     * 数据库主键类型
     * 默认自增长
     *
     * @return {@link IdTypeEnum}
     */
    IdTypeEnum type() default IdTypeEnum.AUTO;
}
