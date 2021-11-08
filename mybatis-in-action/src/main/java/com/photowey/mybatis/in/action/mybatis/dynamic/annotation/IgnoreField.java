package com.photowey.mybatis.in.action.mybatis.dynamic.annotation;

import java.lang.annotation.*;

/**
 * {@code IgnoreField}
 * 当不需要处理该字段映射时,也是在指定的属性上面加上该注解
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreField {
}
