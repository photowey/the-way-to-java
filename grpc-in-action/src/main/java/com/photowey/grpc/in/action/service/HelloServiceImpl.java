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
package com.photowey.grpc.in.action.service;

import com.photowey.grpc.in.action.api.HelloProto;
import com.photowey.grpc.in.action.api.HelloServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * {@code HelloServiceImpl}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/09/17
 */
@Slf4j
@Service
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    private boolean tryInjectError() {
        // We create ~5% error.
        return ThreadLocalRandom.current().nextInt(0, 99) >= 95;
    }

    @Override
    public void unary(
            HelloProto.HelloRequest request,
            StreamObserver<HelloProto.HelloResponse> responseObserver) {
        String name = request.getName();
        log.info("gRPC: hello.request.parameter.name is:{}", name);
        // 18:19:52.370 [grpc-default-executor-0] INFO com.photowey.grpc.in.action.service.HelloServiceImpl - gRPC: hello.request.parameter.name is:photowey

        HelloProto.HelloResponse response = HelloProto.HelloResponse.newBuilder()
                .setMessage("Hello, " + name + "!")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<HelloProto.HelloClientStreamingRequest> clientStreaming(
            StreamObserver<HelloProto.HelloClientStreamingResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(HelloProto.HelloClientStreamingRequest request) {
                HelloProto.HelloClientStreamingResponse response = HelloProto.HelloClientStreamingResponse.newBuilder()
                        .setMessage(request.getName())
                        .build();

                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                if (tryInjectError()) {
                    responseObserver.onError(Status.INTERNAL.asException());
                } else {
                    responseObserver.onCompleted();
                }
            }
        };
    }

    @Override
    public void serverStreaming(
            HelloProto.HelloServerStreamingRequest request,
            StreamObserver<HelloProto.HelloServerStreamingResponse> responseObserver) {
        if (tryInjectError()) {
            responseObserver.onError(Status.INTERNAL.asException());

            return;
        }

        HelloProto.HelloServerStreamingResponse response = HelloProto.HelloServerStreamingResponse.newBuilder()
                .setMessage(request.getName())
                .build();

        // 数据回传
        responseObserver.onNext(response);
        // 响应结束 -> 打上结束标记
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<HelloProto.HelloBidiStreamingRequest> bidiStreaming(
            StreamObserver<HelloProto.HelloBidiStreamingResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(HelloProto.HelloBidiStreamingRequest request) {
                HelloProto.HelloBidiStreamingResponse response = HelloProto.HelloBidiStreamingResponse.newBuilder()
                        .setMessage(request.getName())
                        .build();

                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                if (tryInjectError()) {
                    responseObserver.onError(Status.INTERNAL.asException());

                    return;
                }

                responseObserver.onCompleted();
            }
        };
    }
}