/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.redis.in.action.proxy.template;

import com.photowey.common.in.action.func.ThreeConsumer;
import com.photowey.common.in.action.func.lambda.LambdaFunction;
import com.photowey.common.in.action.util.ObjectUtils;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * {@code DefaultRedisTemplateProxy}
 *
 * @author photowey
 * @date 2024/01/20
 * @since 1.0.0
 */
public class DefaultRedisTemplateProxy implements RedisTemplateProxy {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public DefaultRedisTemplateProxy(
            RedisTemplate<String, Object> redisTemplate,
            StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public RedisTemplate<String, Object> redis() {
        return this.redisTemplate;
    }

    @Override
    public StringRedisTemplate stringRedis() {
        return this.stringRedisTemplate;
    }

    // ----------------------------------------------------------------

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(this.redisTemplate.hasKey(key));
    }

    @Override
    public void delete(String key) {
        this.redisTemplate.delete(key);
    }

    @Override
    public <T> void set(String key, T value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public <T> void reset(String key, T value) {
        this.set(key, value);
    }

    @Override
    public void set(String key, Object value, long expires) {
        this.redisTemplate.opsForValue().set(key, value, expires, TimeUnit.SECONDS);
    }

    @Override
    public void reset(String key, Object value, long expires) {
        this.set(key, value, expires, TimeUnit.SECONDS);
    }

    @Override
    public <T> void set(String key, T value, long expires, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value, expires, timeUnit);
    }

    @Override
    public <T> void reset(String key, T value, long expires, TimeUnit timeUnit) {
        this.set(key, value, expires, timeUnit);
    }

    @Override
    public <T> T get(String key) {
        return (T) this.redisTemplate.opsForValue().get(key);
    }

    @Override
    public void setString(String key, String value) {
        this.stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void resetString(String key, String value) {
        this.setString(key, value);
    }

    @Override
    public void setString(String key, String value, long expires, TimeUnit timeUnit) {
        this.stringRedisTemplate.opsForValue().set(key, value, expires, timeUnit);
    }

    @Override
    public void resetString(String key, String value, long expires, TimeUnit timeUnit) {
        this.setString(key, value, expires, timeUnit);
    }

    @Override
    public String getString(String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Long incr(String key) {
        return this.incr(key, 1L);
    }

    @Override
    public Long incr(String key, Long delta) {
        return this.redisTemplate.opsForValue().increment(key, Math.abs(delta));
    }

    @Override
    public Long hashIncr(String key, String filed) {
        return this.hashIncr(key, filed, 1L);
    }

    @Override
    public Long hashIncr(String key, String filed, Long delta) {
        return this.redisTemplate.opsForHash().increment(key, filed, Math.abs(delta));
    }

    @Override
    public <T> Long hashIncr(String key, LambdaFunction<T, ?> filed) {
        return this.hashIncr(key, filed, 1L);
    }

    @Override
    public <T> Long hashIncr(String key, LambdaFunction<T, ?> filed, Long delta) {
        return this.hashIncr(key, LambdaFunction.resolve(filed), delta);
    }

    @Override
    public Long decr(String key) {
        return this.decr(key, -1L);
    }

    @Override
    public Long decr(String key, Long delta) {
        return this.redisTemplate.opsForValue().decrement(key, -1L * Math.abs(delta));
    }

    @Override
    public Long hashDecr(String key, String filed) {
        return this.hashDecr(key, filed, -1L);
    }

    @Override
    public Long hashDecr(String key, String filed, Long delta) {
        return this.redisTemplate.opsForHash().increment(key, filed, -1L * Math.abs(delta));
    }

    @Override
    public <T> Long hashDecr(String key, LambdaFunction<T, ?> filed) {
        return this.hashDecr(key, filed, -1L);
    }

    @Override
    public <T> Long hashDecr(String key, LambdaFunction<T, ?> filed, Long delta) {
        return this.hashDecr(key, LambdaFunction.resolve(filed), -1L * Math.abs(delta));
    }

    // ----------------------------------------------------------------

    @Override
    public void remove(String key) {
        this.redisTemplate.delete(key);
    }

    @Override
    public void remove(String... keys) {
        if (ObjectUtils.isNullOrEmpty(keys)) {
            return;
        }

        this.redisTemplate.delete(Arrays.asList(keys));
    }

    @Override
    public void removePattern(String pattern) {
        Set<String> keys = this.redisTemplate.keys(pattern);
        if (ObjectUtils.isNotNullOrEmpty(keys)) {
            this.redisTemplate.delete(keys);
        }
    }

    @Override
    public boolean expire(String key, long expireTime) {
        return this.expire(key, expireTime, TimeUnit.SECONDS);
    }

    @Override
    public boolean expire(String key, long expireTime, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(this.redisTemplate.expire(key, expireTime, timeUnit));
    }

    @Override
    public <T> long leftPush(String key, T value) {
        Long length = this.redisTemplate.opsForList().leftPush(key, value);
        return length != null ? length : 0L;
    }

    @Override
    public <T> T rightPop(String key) {
        return (T) this.redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public long rightPush(String key, Object value) {
        Long length = this.redisTemplate.opsForList().rightPush(key, value);
        return length != null ? length : 0L;
    }

    @Override
    public <T> T leftPop(String key) {
        return (T) this.redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public <T> List<T> range(String key, Integer start, Integer stop) {
        start = null != start ? start : 0;
        stop = null != stop ? stop : -1;

        List<T> results = (List<T>) this.redisTemplate.opsForList().range(key, start, stop);
        if (null == results) {
            return this.emptyList();
        }

        return results;
    }

    @Override
    public <T> Long setAdd(String key, T... values) {
        return this.redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public <T> Long setRemove(String key, T... values) {
        return this.redisTemplate.opsForSet().remove(key, values);
    }

    @Override
    public <T> T setPop(String key) {
        return (T) this.redisTemplate.opsForSet().pop(key);
    }

    @Override
    public <T> Boolean setMove(String key, T value, String destKey) {
        return this.redisTemplate.opsForSet().move(key, value, destKey);
    }

    @Override
    public Long setSize(String key) {
        return null;
    }

    @Override
    public <T> Boolean setIsMember(String key, T value) {
        return null;
    }

    @Override
    public <T> Set<T> setMembers(String key) {
        return null;
    }

    @Override
    public <T> T setRandomMember(String key) {
        return null;
    }

    @Override
    public <T> List<T> setRandomMembers(String key, long count) {
        return null;
    }

    @Override
    public Long zsetSize(String key) {
        return null;
    }

    @Override
    public boolean zsetExists(String key, Object value) {
        return false;
    }

    @Override
    public void zsetTrim(String key, long max) {

    }

    @Override
    public void zsetRemoveRange(String key, long start, long end) {

    }

    @Override
    public <T, V> int zsetRemovePipeline(List<T> actors, Function<T, String> kfx, Function<T, V> vfx) {
        return 0;
    }

    @Override
    public <T, V> Integer zsetPipeline(List<T> actors, Function<T, String> kfx, Function<T, V> vfx, ThreeConsumer<RedisZSetCommands, byte[], byte[]> fx) {
        return null;
    }

}