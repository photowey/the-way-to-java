/*
 * Copyright © 2021 photowey (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    public void setRange(String key, String value, long offset) {
        this.stringRedisTemplate.opsForValue().set(key, value, offset);
    }

    @Override
    public String get(String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public String getAndSet(String key, String value) {
        return this.stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public String getRange(String key, long start, long end) {
        return this.stringRedisTemplate.opsForValue().get(key, start, end);
    }

    // ========================================= incr

    @Override
    public Long incr(String key) {
        return this.stringRedisTemplate.opsForValue().increment(key);
    }

    @Override
    public Long incrBy(String key, long delta) {
        return this.stringRedisTemplate.opsForValue().increment(key, delta);
    }

    // ========================================= decr

    @Override
    public Long decr(String key) {
        return this.stringRedisTemplate.opsForValue().decrement(key);
    }

    @Override
    public Long decrBy(String key, long delta) {
        return this.stringRedisTemplate.opsForValue().decrement(key, delta);
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

    // ========================================= exists

    @Override
    public Boolean exists(String key) {
        return this.stringRedisTemplate.hasKey(key);
    }

    // ========================================= append

    @Override
    public Integer append(String key, String value) {
        return this.stringRedisTemplate.opsForValue().append(key, value);
    }

    // ========================================= strlen

    @Override
    public Long strlen(String key) {
        return this.stringRedisTemplate.opsForValue().size(key);
    }

    // ========================================= delete

    @Override
    public Boolean delete(String key) {
        return this.stringRedisTemplate.delete(key);
    }

    // copy from hashmap

    private static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * @see {@code HashMap#tableSizeFor(int)}
     */
    private static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
