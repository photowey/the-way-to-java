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

    void set(String key, String value);

    void set(String key, String value, long timeout);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    String get(String key);

    // ========================================= multi

    void mset(Map<String, String> context);

    Map<String, String> mget(List<String> keys);
}
