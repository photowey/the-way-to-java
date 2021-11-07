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
package com.photowey.redis.in.action.engine.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * {@code IHashEngine}
 *
 * @author photowey
 * @date 2021/10/26
 * @since 1.0.0
 */
public interface IHashEngine extends IEngine {

    // ========================================= hset

    <T> void hset(String key, Object field, T value);

    <T> void hset(String key, Map<Object, T> hashValue);

    // ========================================= hget

    <T> T hget(String key, Object field);

    List<Object> hmultiGet(String key, List<Object> fields);

    <T> List<T> hmultiGet(String key, List<Object> fields, Function<List<Object>, List<T>> function);

    // ========================================= hdel

    Long hdel(String key, List<Object> fields);

    Long hdel(String key, Object... fields);

    // ========================================= hexists

    Boolean hexists(String key, Object field);

    // ========================================= hlen

    Long hlen(String key);

    Long hstrlen(String key, Object field);

    // ========================================= hkeys

    Set<Object> hkeys(String key);

    // ========================================= hvals

    List<Object> hvals(String key);

    // ========================================= hincrby

    Long hincrby(String key, Object field, long delta);

    // ========================================= hgetall

    Map<Object, Object> hgetall(String key);

    <T> T hgetall(String key, Function<Map<Object, Object>, T> function);

}
