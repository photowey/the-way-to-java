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
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

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
        return false;
    }

    @Override
    public void delete(String key) {

    }

    @Override
    public <T> void set(String key, T value) {

    }

    @Override
    public <T> void reset(String key, T value) {

    }

    @Override
    public <T> void set(String key, T value, long expires, TimeUnit timeUnit) {

    }

    @Override
    public <T> void reset(String key, T value, long expires, TimeUnit timeUnit) {

    }

    @Override
    public <T> T get(String key) {
        return null;
    }

    @Override
    public void setString(String key, String value) {

    }

    @Override
    public void resetString(String key, String value) {

    }

    @Override
    public void setString(String key, String value, long expires, TimeUnit timeUnit) {

    }

    @Override
    public void resetString(String key, String value, long expires, TimeUnit timeUnit) {

    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public Long incr(String key) {
        return null;
    }

    @Override
    public Long incr(String key, Long delta) {
        return null;
    }

    @Override
    public Long hashIncr(String key, String filed) {
        return null;
    }

    @Override
    public Long hashIncr(String key, String filed, Long delta) {
        return null;
    }

    @Override
    public <T> Long hashIncr(String key, LambdaFunction<T, ?> filed) {
        return null;
    }

    @Override
    public <T> Long hashIncr(String key, LambdaFunction<T, ?> filed, Long delta) {
        return null;
    }

    @Override
    public Long decr(String key) {
        return null;
    }

    @Override
    public Long decr(String key, Long delta) {
        return null;
    }

    @Override
    public Long hashDecr(String key, String filed) {
        return null;
    }

    @Override
    public Long hashDecr(String key, String filed, Long delta) {
        return null;
    }

    @Override
    public <T> Long hashDecr(String key, LambdaFunction<T, ?> filed) {
        return null;
    }

    @Override
    public <T> Long hashDecr(String key, LambdaFunction<T, ?> filed, Long delta) {
        return null;
    }

    // ----------------------------------------------------------------

    @Override
    public void remove(String key) {

    }

    @Override
    public void remove(String... keys) {

    }

    @Override
    public void removePattern(String pattern) {

    }

    @Override
    public boolean expire(String key, long expireTime) {
        return false;
    }

    @Override
    public boolean expire(String key, long expireTime, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public <T> long leftPush(String key, T value) {
        return 0;
    }

    @Override
    public <T> T rightPop(String key) {
        return null;
    }

    @Override
    public long rightPush(String key, Object value) {
        return 0;
    }

    @Override
    public <T> T leftPop(String key) {
        return null;
    }

    @Override
    public <T> List<T> range(String key, Integer start, Integer stop) {
        return null;
    }

    @Override
    public <T> Long setAdd(String key, T... values) {
        return null;
    }

    @Override
    public <T> Long setRemove(String key, T... values) {
        return null;
    }

    @Override
    public <T> T setPop(String key) {
        return null;
    }

    @Override
    public <T> Boolean setMove(String key, T value, String destKey) {
        return null;
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