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
