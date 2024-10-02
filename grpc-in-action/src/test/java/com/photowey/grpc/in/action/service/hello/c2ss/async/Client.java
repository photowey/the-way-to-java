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

import com.photowey.grpc.in.action.api.HelloProto;
import com.photowey.grpc.in.action.api.HelloServiceGrpc;
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
            client.c2ss(name);
            channel.awaitTermination(15, TimeUnit.SECONDS);
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

        asyncStub.serverStreaming(request, new StreamObserver<>() {
            @Override
            public void onNext(HelloProto.HelloServerStreamingResponse response) {
                log.info("C2ss: {}", response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                log.info("{} onError ...", name, t);
            }

            @Override
            public void onCompleted() {
                log.info("{} onCompleted ...", name);
            }
        });

        log.info("C2ss after call serverStreaming()");
    }
}