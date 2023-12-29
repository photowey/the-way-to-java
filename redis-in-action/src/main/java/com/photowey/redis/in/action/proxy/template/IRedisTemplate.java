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

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

}
