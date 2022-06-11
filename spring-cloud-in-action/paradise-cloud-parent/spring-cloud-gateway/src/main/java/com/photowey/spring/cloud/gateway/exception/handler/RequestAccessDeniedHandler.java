/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.spring.cloud.gateway.exception.handler;

import com.alibaba.fastjson.JSON;
import com.photowey.oauth2.authentication.jwt.model.ResponseModel;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * {@code RequestAccessDeniedHandler}
 *
 * @author photowey
 * @date 2022/01/16
 * @since 1.0.0
 */
@Component
public class RequestAccessDeniedHandler implements ServerAccessDeniedHandler {
    /**
     * handle 403
     *
     * @param exchange
     * @param denied
     * @return
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ResponseModel responseModel = new ResponseModel(403, "40030", "无权限访问", null);
        DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONString(responseModel).getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }
}