package com.photowey.spring.cloud.alibaba.seata.in.action.mysql.support.annotation;

import java.lang.annotation.*;

/**
 * {@code EnableMybatisAutoConfigure}
 *
 * @author photowey
 * @date 2022/03/02
 * @since 1.0.0
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//@Import(MyBatisConfig.class)
public @interface EnableMybatisAutoConfigure {
}
