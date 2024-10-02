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
package com.photowey.grpc.in.action.service.hello.unary.async;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.photowey.grpc.in.action.api.HelloProto;
import com.photowey.grpc.in.action.api.HelloServiceGrpc;
import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private final HelloServiceGrpc.HelloServiceFutureStub futureStub;
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    public Client(Channel channel) {
        futureStub = HelloServiceGrpc.newFutureStub(channel);
    }

    public static void main(String[] args) throws Exception {
        String target = "localhost:9090";
        String name = "photowey";
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
            Client client = new Client(channel);
            client.greetAsync(name);
            channel.awaitTermination(3, TimeUnit.SECONDS);

            threadPool.shutdownNow();
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }

        // ...
    }

    public void greetAsync(String name) {
        log.info("Will try to greet {} ...", name);
        HelloProto.HelloRequest request = HelloProto.HelloRequest.newBuilder()
                .setName(name)
                .build();

        ListenableFuture<HelloProto.HelloResponse> future = futureStub.unaryAsync(request);

        /**
         future.addListener(() -> {
         log.info("Greeting async response");
         }, Executors.newCachedThreadPool());
         */

        Futures.addCallback(future, new FutureCallback<>() {
            @Override
            public void onSuccess(HelloProto.@Nullable HelloResponse response) {
                assert response != null;
                log.info("Greeting async response: {}", response.getMessage());
            }

            @Override
            public void onFailure(Throwable t) {
                log.error("Greeting async failed", t);
            }
        }, threadPool);
    }
}