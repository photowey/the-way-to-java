/*
 * Copyright © 2022 photowey (photowey@gmail.com)
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
