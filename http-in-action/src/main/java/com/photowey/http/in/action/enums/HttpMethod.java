package com.photowey.http.in.action.enums;

import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code HttpMethod}
 *
 * @author weichangjun
 * @date 2022/03/01
 * @since 1.0.0
 */
public enum HttpMethod {

    /**
     * {@code GET}
     */
    GET,
    /**
     * {@code POST}
     */
    POST,
    /**
     * {@code PUT}
     */
    PUT,
    /**
     * {@code PATCH}
     */
    PATCH,
    /**
     * {@code DELETE}
     */
    DELETE;

    private static final Map<String, HttpMethod> METHOD_MAP = new HashMap<>(16);

    static {
        for (HttpMethod httpMethod : values()) {
            METHOD_MAP.put(httpMethod.name(), httpMethod);
        }
    }

    @Nullable
    public static HttpMethod resolve(@Nullable String method) {
        return (method != null ? METHOD_MAP.get(method) : null);
    }

    public boolean matches(String method) {
        return (this == resolve(method));
    }

}
