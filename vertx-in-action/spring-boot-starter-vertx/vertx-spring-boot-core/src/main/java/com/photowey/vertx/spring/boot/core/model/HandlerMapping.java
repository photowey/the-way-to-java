package com.photowey.vertx.spring.boot.core.model;

import io.netty.handler.codec.http.HttpMethod;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * {@code HandlerMapping}
 *
 * @author photowey
 * @date 2022/02/17
 * @since 1.0.0
 */
@Data
public class HandlerMapping implements Serializable {

    /**
     * 请求路径
     */
    private String path;
    /**
     * {@code HttpMethod}
     */
    private String method = HttpMethod.GET.name();
    /**
     * 对应 Spring 里的 {@code beanName}
     */
    private List<String> handlers;
}
