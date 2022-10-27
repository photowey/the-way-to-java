package com.photowey.mongo.in.action.event.generator;

/**
 * {@code MongoKeyGenerator}
 *
 * @author photowey
 * @date 2022/10/26
 * @since 1.0.0
 */
public interface MongoKeyGenerator extends KeyGenerator {

    /**
     * 字符串类型
     * 对象标识
     *
     * @return 对象标识
     */
    String objectId();
}
