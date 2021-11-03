package com.photowey.redis.in.action.engine.redis.impl;

import com.photowey.redis.in.action.engine.redis.IStringEngine;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * {@code StringEngineImpl}
 *
 * @author photowey
 * @date 2021/10/26
 * @since 1.0.0
 */
@Component
public class StringEngineImpl implements IStringEngine {

    private final StringRedisTemplate stringRedisTemplate;

    public StringEngineImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void set(String key, String value) {
        this.stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout) {
        this.set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        this.stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public String get(String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
    }

    // ========================================= multi

    @Override
    public void mset(Map<String, String> context) {
        this.stringRedisTemplate.opsForValue().multiSet(context);
    }

    @Override
    public Map<String, String> mget(List<String> keys) {
        Map<String, String> map = new HashMap<>(tableSizeFor(keys.size()));
        if (CollectionUtils.isEmpty(keys)) {
            return map;
        }

        // contains null member, maybe, if the key not exists.
        List<String> values = this.stringRedisTemplate.opsForValue().multiGet(keys);
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), Objects.requireNonNull(values).get(i));
        }

        return map;
    }

    // copy from hashmap

    private static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * @see {@link HashMap#tableSizeFor(int)}
     */
    private static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
