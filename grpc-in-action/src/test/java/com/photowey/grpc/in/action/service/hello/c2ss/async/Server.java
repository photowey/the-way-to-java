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
package com.photowey.grpc.in.action.service.hello.c2ss.async;

import com.photowey.grpc.in.action.service.HelloServiceImpl;
import io.grpc.ServerBuilder;

import java.util.concurrent.TimeUnit;

/**
 * {@code Server}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/02
 */
public class Server {

    public static void main(String[] args) throws Exception {
        io.grpc.Server server = ServerBuilder.forPort(9090)
                .addService(new HelloServiceImpl())
                .build();

        server.start();
        server.awaitTermination(25, TimeUnit.SECONDS);

        // ...
    }
}