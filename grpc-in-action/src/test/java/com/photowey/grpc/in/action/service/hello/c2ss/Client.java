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
package com.photowey.grpc.in.action.service.hello.c2ss;

import com.photowey.grpc.in.action.api.HelloProto;
import com.photowey.grpc.in.action.api.HelloServiceGrpc;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * {@code Client}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/02
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
            client.c2ss(name);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }

        // ...
    }

    public void c2ss(String name) {
        log.info("Will try to c2ss {} ...", name);
        HelloProto.HelloServerStreamingRequest request = HelloProto.HelloServerStreamingRequest.newBuilder()
                .setName(name)
                .build();
        try {
            Iterator<HelloProto.HelloServerStreamingResponse> iterator = blockingStub.serverStreaming(request);
            while (iterator.hasNext()) {
                HelloProto.HelloServerStreamingResponse response = iterator.next();
                log.info("C2ss: {}", response.getMessage());
            }
        } catch (StatusRuntimeException e) {
            log.error("C2ss failed", e);
        }
    }
}