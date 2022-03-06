package com.photowey.http.in.action.query;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code RequestParameters}
 * 请求参数
 *
 * @author weichangjun
 * @date 2022/02/28
 * @since 1.0.0
 */
public class RequestParameters {

    private static final ConcurrentHashMap<String, Object> PARAMS_CONTEXT = new ConcurrentHashMap<>();

    public <T> RequestParameters add(String key, T value) {
        PARAMS_CONTEXT.put(key, value);

        return this;
    }

    public <T> RequestParameters add(Map<String, Object> params) {
        PARAMS_CONTEXT.putAll(params);

        return this;
    }

    public <T> T get(String key) {
        return (T) PARAMS_CONTEXT.get(key);
    }

    public <T> Map<String, Object> get() {
        return PARAMS_CONTEXT;
    }

}
