package com.photowey.redis.in.action.engine.redis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@code IStringEngine}
 *
 * @author photowey
 * @date 2021/10/26
 * @since 1.0.0
 */
public interface IStringEngine extends IEngine {

    // ========================================= set

    void set(String key, String value);

    void set(String key, String value, long timeout);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    void setRange(String key, String value, long offset);

    // ========================================= get

    String get(String key);

    String getAndSet(String key, String value);

    String getRange(String key, long start, long end);

    // ========================================= incr

    Long incr(String key);

    Long incrBy(String key, long delta);

    // ========================================= decr

    Long decr(String key);

    Long decrBy(String key, long delta);

    // ========================================= multi

    void mset(Map<String, String> context);

    Map<String, String> mget(List<String> keys);

    // ========================================= exists

    Boolean exists(String key);

    // ========================================= append

    Integer append(String key, String value);

    // ========================================= strlen

    Long strlen(String key);

    // ========================================= delete

    Boolean delete(String key);
}
