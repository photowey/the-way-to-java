package com.photowey.redis.in.action.lock.v2.annotation;

import com.photowey.redis.in.action.lock.v2.enums.RedisLockPrefix;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code RedisLock}
 *
 * @author photowey
 * @date 2022/10/04
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RedisLock {

    /**
     * 特定参数识别，默认取第 0 个下标
     */
    int lockFiled() default 0;

    /**
     * 超时重试次数
     */
    int tryCount() default 3;

    /**
     * 自定义加锁类型
     */
    RedisLockPrefix prefix();

    /**
     * 释放时间(单位: 秒 s)
     */
    long lockTime() default 30;
}