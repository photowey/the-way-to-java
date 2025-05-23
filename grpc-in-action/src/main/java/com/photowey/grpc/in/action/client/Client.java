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
package com.photowey.grpc.in.action.client;

import com.photowey.grpc.in.action.connection.Connection;

import java.io.Serializable;

/**
 * {@code Client}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/12
 */
public interface Client extends Serializable {

    String clientId();

    String clientIp();

    Integer clientPort();

    String protocol();

    Connection connection();
}
