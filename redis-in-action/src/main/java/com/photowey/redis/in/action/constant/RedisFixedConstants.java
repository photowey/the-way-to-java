package com.photowey.redis.in.action.constant;

/**
 * {@code RedisFixedConstants}
 *
 * @author photowey
 * @date 2022/12/28
 * @since 1.0.0
 */
public interface RedisFixedConstants {

    String CUSTOM_REDIS_TEMPLATE_BEAN_NAME = "cst.redisTemplate";

    String REDIS_KEY_SERIALIZER_BEAN_NAME = "org.springframework.data.redis.serializer.RedisSerializer.key";
    String REDIS_VALUE_SERIALIZER_BEAN_NAME = "org.springframework.data.redis.serializer.RedisSerializer.value";
}
