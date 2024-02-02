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

import com.photowey.common.in.action.func.FourConsumer;
import com.photowey.common.in.action.func.ThreeConsumer;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * {@code IRedisTemplate}
 *
 * @author photowey
 * @date 2023/12/29
 * @since 1.0.0
 */
public interface IRedisTemplate extends ICacheTemplate {

    // ---------------------------------------------------------------- delete/remove

    void remove(final String key);

    void remove(final String... keys);

    void removePattern(final String pattern);

    // ---------------------------------------------------------------- expire

    boolean expire(final String key, long expireTime);

    boolean expire(final String key, long expireTime, TimeUnit timeUnit);

    // ---------------------------------------------------------------- list

    <T> long leftPush(final String key, T value);

    <T> T rightPop(final String key);

    long rightPush(final String key, Object value);

    <T> T leftPop(final String key);

    <T> List<T> range(final String key, Integer start, Integer stop);

    // ---------------------------------------------------------------- set

    <T> Long setAdd(final String key, T... values);

    <T> Long setRemove(final String key, T... values);

    <T> T setPop(final String key);

    <T> Boolean setMove(final String key, T value, String destKey);

    Long setSize(final String key);

    <T> Boolean setIsMember(final String key, T value);

    <T> Set<T> setMembers(final String key);

    <T> T setRandomMember(final String key);

    <T> List<T> setRandomMembers(String key, long count);

    // ---------------------------------------------------------------- zset

    Long zsetSize(final String key);

    boolean zsetExists(final String key, Object value);

    void zsetTrim(final String key, final long max);

    void zsetRemoveRange(final String key, final long start, final long end);

    // ---------------------------------------------------------------- pipeline

    /**
     * 管道
     * |-批量删除操作
     *
     * @param actors 批量操作目标列表
     * @param kfx    key 回调函数
     * @param vfx    member 回调函数
     * @param <T>    T 目标对象裂隙
     * @param <V>    V member 成员类型
     * @return {@code int} 0: 失败 1: 成功
     */
    <T, V> Integer zsetRemovePipeline(List<T> actors, Function<T, String> kfx, Function<T, V> vfx);

    /**
     * 管道
     * |-批量操作
     *
     * @param actors 批量操作目标列表
     * @param kfx    key 回调函数
     * @param vfx    member 回调函数
     * @param fx     操作回调函数
     * @param <T>    T 目标对象裂隙
     * @param <V>    V member 成员类型
     * @return {@code int} 0: 失败 1: 成功
     */
    <T, V> Integer zsetPipeline(List<T> actors, Function<T, String> kfx, Function<T, V> vfx, ThreeConsumer<RedisZSetCommands, byte[], byte[]> fx);

    <T, V> Integer pipeline(List<T> actors, Function<T, String> kfx, Function<T, V> vfx, ThreeConsumer<RedisConnection, byte[], byte[]> fx);

    <T> Integer pipeline(List<T> actors, boolean exposeConnection, FourConsumer<RedisConnection, RedisSerializer<String>, RedisSerializer<Object>, T> fx);

}
