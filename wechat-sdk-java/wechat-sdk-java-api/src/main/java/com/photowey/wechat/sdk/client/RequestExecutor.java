/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.wechat.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.photowey.wechat.sdk.core.enums.WechatV3APIEnum;
import org.springframework.core.io.Resource;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * {@code RequestExecutor}
 *
 * @author photowey
 * @date 2024/03/07
 * @since 1.0.0
 */
public interface RequestExecutor<M> {

    RequestExecutor<M> translator(BiFunction<WechatV3APIEnum, M, RequestEntity<?>> translator);

    RequestExecutor<M> objectMapper(ObjectMapper objectMapper);

    RequestExecutor<M> callback(Consumer<ResponseEntity<ObjectNode>> callback);

    RequestExecutor<M> callbackString(Consumer<ResponseEntity<String>> callback);

    RequestExecutor<M> callbackResource(Consumer<ResponseEntity<Resource>> callback);

    RequestExecutor<M> throwDelayed(boolean throwDelayed);

    default void request() {

    }

    default void txt() {

    }

    default void bytes() {

    }

    default void wechatBytes() {

    }

    default void normalBytes() {

    }

    default void bill() {

    }
}

