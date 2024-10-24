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
package com.photowey.grpc.in.action.service.hello.unary;

import com.photowey.grpc.in.action.api.HelloProto;
import com.photowey.grpc.in.action.api.HelloServiceGrpc;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * {@code Client}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/09/17
 */
@Slf4j
public class Client {

    private final HelloServiceGrpc.HelloServiceBlockingStub blockingStub;

    public Client(Channel channel) {
        blockingStub = HelloServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws Exception {
        String target = "localhost:9090";
        String name = "photowey";
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
            .build();
        try {
            Client client = new Client(channel);
            client.greet(name);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }

        // ...
    }

    public void greet(String name) {
        log.info("Will try to greet {} ...", name);
        HelloProto.HelloRequest request = HelloProto.HelloRequest.newBuilder()
            .setName(name)
            .build();
        try {
            HelloProto.HelloResponse response = blockingStub.unary(request);
            log.info("Greeting: {}", response.getMessage());

            // 18:19:52.465 [main] INFO com.photowey.grpc.in.action.service.hello.unary.Client - Greeting: Hello, photowey!
        } catch (StatusRuntimeException e) {
            log.error("Greeting failed", e);
        }
    }
}
