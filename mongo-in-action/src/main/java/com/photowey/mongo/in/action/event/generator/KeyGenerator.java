package com.photowey.mongo.in.action.event.generator;

/**
 * {@code KeyGenerator}
 *
 * @author photowey
 * @date 2022/10/26
 * @since 1.0.0
 */
public interface KeyGenerator {

    /**
     * 数值类型标识
     * 主键标识
     *
     * @return 主键标识
     */
    long nextId();

    /**
     * 指定长度随机数
     * - 默认: 21 字长
     * 随机数标识
     *
     * @return 随机数
     */
    String nanoId();
}
