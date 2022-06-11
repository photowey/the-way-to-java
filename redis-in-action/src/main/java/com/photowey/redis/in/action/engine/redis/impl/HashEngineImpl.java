/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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

import com.photowey.redis.in.action.engine.redis.IHashEngine;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * {@code HashEngineImpl}
 *
 * @author photowey
 * @date 2021/10/26
 * @since 1.0.0
 */
@Component
public class HashEngineImpl implements IHashEngine {

    private final RedisTemplate<String, Object> redisTemplate;

    public HashEngineImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ========================================= hset

    @Override
    public <T> void hset(String key, Object field, T value) {
        this.redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public <T> void hset(String key, Map<Object, T> hashValue) {
        this.redisTemplate.opsForHash().putAll(key, hashValue);
    }

    // ========================================= hget

    @Override
    public <T> T hget(String key, Object field) {
        return (T) this.redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public List<Object> hmultiGet(String key, List<Object> fields) {
        List<Object> hashValues = this.redisTemplate.opsForHash().multiGet(key, fields);
        return hashValues;
    }

    @Override
    public <T> List<T> hmultiGet(String key, List<Object> fields, Function<List<Object>, List<T>> function) {
        List<Object> hashValues = this.redisTemplate.opsForHash().multiGet(key, fields);
        return function.apply(hashValues);
    }

    // ========================================= hdel

    @Override
    public Long hdel(String key, List<Object> fields) {
        return this.hdel(key, fields.toArray());
    }

    @Override
    public Long hdel(String key, Object... fields) {
        return this.redisTemplate.opsForHash().delete(key, fields);
    }

    // ========================================= hexists

    @Override
    public Boolean hexists(String key, Object field) {
        return this.redisTemplate.opsForHash().hasKey(key, field);
    }

    // ========================================= hlen

    @Override
    public Long hlen(String key) {
        return this.redisTemplate.opsForHash().size(key);
    }

    @Override
    public Long hstrlen(String key, Object field) {
        return this.redisTemplate.opsForHash().lengthOfValue(key, field);
    }

    // ========================================= hkeys

    @Override
    public Set<Object> hkeys(String key) {
        return this.redisTemplate.opsForHash().keys(key);
    }

    // ========================================= hvals

    @Override
    public List<Object> hvals(String key) {
        return this.redisTemplate.opsForHash().values(key);
    }

    // ========================================= hincrby

    @Override
    public Long hincrby(String key, Object field, long delta) {
        return this.redisTemplate.opsForHash().increment(key, field, delta);
    }

    // ========================================= hgetall

    @Override
    public Map<Object, Object> hgetall(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    @Override
    public <T> T hgetall(String key, Function<Map<Object, Object>, T> function) {
        Map<Object, Object> entries = this.redisTemplate.opsForHash().entries(key);
        return function.apply(entries);
    }
}
