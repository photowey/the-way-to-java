package com.photowey.http.in.action.query;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code RequestHeaders}
 * 请求-头
 *
 * @author weichangjun
 * @date 2022/02/28
 * @since 1.0.0
 */
public class RequestHeaders {

    private static final ConcurrentHashMap<String, Object> HEADERS_CONTEXT = new ConcurrentHashMap<>();

    public <T> RequestHeaders add(String key, T value) {
        HEADERS_CONTEXT.put(key, value);

        return this;
    }

    public <T> RequestHeaders add(Map<String, Object> params) {
        HEADERS_CONTEXT.putAll(params);

        return this;
    }

    public <T> T get(String key) {
        return (T) HEADERS_CONTEXT.get(key);
    }

    public <T> Map<String, Object> get() {
        return HEADERS_CONTEXT;
    }
}
