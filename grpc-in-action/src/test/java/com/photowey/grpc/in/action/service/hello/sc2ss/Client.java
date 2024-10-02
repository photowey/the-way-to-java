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
package com.photowey.grpc.in.action.service.hello.sc2ss;

import com.photowey.grpc.in.action.api.HelloProto;
import com.photowey.grpc.in.action.api.HelloServiceGrpc;
import com.photowey.grpc.in.action.util.Sleeper;
import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

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

    private final HelloServiceGrpc.HelloServiceStub asyncStub;

    public Client(Channel channel) {
        asyncStub = HelloServiceGrpc.newStub(channel);
    }

    public static void main(String[] args) throws Exception {
        String target = "localhost:9090";
        String name = "photowey";
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
            Client client = new Client(channel);
            client.cs2ss(name);
            channel.awaitTermination(15, TimeUnit.SECONDS);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }

        // ...
    }

    public void cs2ss(String name) {
        log.info("Will try to cs2ss {} ...", name);

        StreamObserver<HelloProto.HelloBidiStreamingRequest> observer = asyncStub.bidiStreaming(new StreamObserver<>() {
            @Override
            public void onNext(HelloProto.HelloBidiStreamingResponse response) {
                String message = response.getMessage();
                log.info("Server: bidiStreaming.response.onNext(), request:{}, response:{}", name, message);

                // 监控服务端的每条响应
            }

            @Override
            public void onError(Throwable t) {
                log.error("Server: bidiStreaming.response.onError(), request:{} ...", name, t);
            }

            @Override
            public void onCompleted() {
                log.info("Server: bidiStreaming.response.onCompleted(), request:{} ...", name);
            }
        });

        for (int i = 0; i < 10; i++) {
            HelloProto.HelloBidiStreamingRequest request = HelloProto.HelloBidiStreamingRequest.newBuilder()
                    .setName(name + ":" + (i + 1))
                    .build();
            observer.onNext(request);

            sleep(1_000L);
        }

        log.info("Cs2ss after call bidiStreaming()");
        observer.onCompleted();
    }

    private static void sleep(long millis) {
        Sleeper.sleep(millis);
    }
}