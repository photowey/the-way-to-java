package com.photowey.translator.http.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code RequestHeaders}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public class RequestHeaders {

    private ConcurrentHashMap<String, Object> ctx = new ConcurrentHashMap<>();

    public <T> RequestHeaders add(String key, T value) {
        ctx.put(key, value);

        return this;
    }

    public <T> RequestHeaders add(Map<String, T> params) {
        ctx.putAll(params);

        return this;
    }

    public <T> T get(String key) {
        return (T) ctx.get(key);
    }

    public <T> Map<String, T> get() {
        return (Map<String, T>) ctx;
    }

    public <T> T getHeader(String key) {
        return (T) ctx.get(key);
    }
}
