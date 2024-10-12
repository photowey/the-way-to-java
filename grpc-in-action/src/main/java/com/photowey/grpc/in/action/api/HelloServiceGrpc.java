package com.photowey.grpc.in.action.api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.66.0)",
    comments = "Source: Hello.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class HelloServiceGrpc {

  private HelloServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "HelloService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloRequest,
      com.photowey.grpc.in.action.api.HelloProto.HelloResponse> getUnaryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "unary",
      requestType = com.photowey.grpc.in.action.api.HelloProto.HelloRequest.class,
      responseType = com.photowey.grpc.in.action.api.HelloProto.HelloResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloRequest,
      com.photowey.grpc.in.action.api.HelloProto.HelloResponse> getUnaryMethod() {
    io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloRequest, com.photowey.grpc.in.action.api.HelloProto.HelloResponse> getUnaryMethod;
    if ((getUnaryMethod = HelloServiceGrpc.getUnaryMethod) == null) {
      synchronized (HelloServiceGrpc.class) {
        if ((getUnaryMethod = HelloServiceGrpc.getUnaryMethod) == null) {
          HelloServiceGrpc.getUnaryMethod = getUnaryMethod =
              io.grpc.MethodDescriptor.<com.photowey.grpc.in.action.api.HelloProto.HelloRequest, com.photowey.grpc.in.action.api.HelloProto.HelloResponse>newBuilder()
                  .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                  .setFullMethodName(generateFullMethodName(SERVICE_NAME, "unary"))
                  .setSampledToLocalTracing(true)
                  .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                      com.photowey.grpc.in.action.api.HelloProto.HelloRequest.getDefaultInstance()))
                  .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                      com.photowey.grpc.in.action.api.HelloProto.HelloResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new HelloServiceMethodDescriptorSupplier("unary"))
                  .build();
        }
      }
    }
    return getUnaryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingRequest,
      com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingResponse> getClientStreamingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "clientStreaming",
      requestType = com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingRequest.class,
      responseType = com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingRequest,
      com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingResponse> getClientStreamingMethod() {
    io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingRequest, com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingResponse> getClientStreamingMethod;
    if ((getClientStreamingMethod = HelloServiceGrpc.getClientStreamingMethod) == null) {
      synchronized (HelloServiceGrpc.class) {
        if ((getClientStreamingMethod = HelloServiceGrpc.getClientStreamingMethod) == null) {
          HelloServiceGrpc.getClientStreamingMethod = getClientStreamingMethod =
              io.grpc.MethodDescriptor.<com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingRequest, com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingResponse>newBuilder()
                  .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
                  .setFullMethodName(generateFullMethodName(SERVICE_NAME, "clientStreaming"))
                  .setSampledToLocalTracing(true)
                  .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                      com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingRequest.getDefaultInstance()))
                  .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                      com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new HelloServiceMethodDescriptorSupplier("clientStreaming"))
                  .build();
        }
      }
    }
    return getClientStreamingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingRequest,
      com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingResponse> getServerStreamingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "serverStreaming",
      requestType = com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingRequest.class,
      responseType = com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingRequest,
      com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingResponse> getServerStreamingMethod() {
    io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingRequest, com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingResponse> getServerStreamingMethod;
    if ((getServerStreamingMethod = HelloServiceGrpc.getServerStreamingMethod) == null) {
      synchronized (HelloServiceGrpc.class) {
        if ((getServerStreamingMethod = HelloServiceGrpc.getServerStreamingMethod) == null) {
          HelloServiceGrpc.getServerStreamingMethod = getServerStreamingMethod =
              io.grpc.MethodDescriptor.<com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingRequest, com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingResponse>newBuilder()
                  .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
                  .setFullMethodName(generateFullMethodName(SERVICE_NAME, "serverStreaming"))
                  .setSampledToLocalTracing(true)
                  .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                      com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingRequest.getDefaultInstance()))
                  .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                      com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new HelloServiceMethodDescriptorSupplier("serverStreaming"))
                  .build();
        }
      }
    }
    return getServerStreamingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingRequest,
      com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingResponse> getBidiStreamingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "bidiStreaming",
      requestType = com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingRequest.class,
      responseType = com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingRequest,
      com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingResponse> getBidiStreamingMethod() {
    io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingRequest, com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingResponse> getBidiStreamingMethod;
    if ((getBidiStreamingMethod = HelloServiceGrpc.getBidiStreamingMethod) == null) {
      synchronized (HelloServiceGrpc.class) {
        if ((getBidiStreamingMethod = HelloServiceGrpc.getBidiStreamingMethod) == null) {
          HelloServiceGrpc.getBidiStreamingMethod = getBidiStreamingMethod =
              io.grpc.MethodDescriptor.<com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingRequest, com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingResponse>newBuilder()
                  .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
                  .setFullMethodName(generateFullMethodName(SERVICE_NAME, "bidiStreaming"))
                  .setSampledToLocalTracing(true)
                  .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                      com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingRequest.getDefaultInstance()))
                  .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                      com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new HelloServiceMethodDescriptorSupplier("bidiStreaming"))
                  .build();
        }
      }
    }
    return getBidiStreamingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloRequest,
      com.photowey.grpc.in.action.api.HelloProto.HelloResponse> getUnaryAsyncMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "unaryAsync",
      requestType = com.photowey.grpc.in.action.api.HelloProto.HelloRequest.class,
      responseType = com.photowey.grpc.in.action.api.HelloProto.HelloResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloRequest,
      com.photowey.grpc.in.action.api.HelloProto.HelloResponse> getUnaryAsyncMethod() {
    io.grpc.MethodDescriptor<com.photowey.grpc.in.action.api.HelloProto.HelloRequest, com.photowey.grpc.in.action.api.HelloProto.HelloResponse> getUnaryAsyncMethod;
    if ((getUnaryAsyncMethod = HelloServiceGrpc.getUnaryAsyncMethod) == null) {
      synchronized (HelloServiceGrpc.class) {
        if ((getUnaryAsyncMethod = HelloServiceGrpc.getUnaryAsyncMethod) == null) {
          HelloServiceGrpc.getUnaryAsyncMethod = getUnaryAsyncMethod =
              io.grpc.MethodDescriptor.<com.photowey.grpc.in.action.api.HelloProto.HelloRequest, com.photowey.grpc.in.action.api.HelloProto.HelloResponse>newBuilder()
                  .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                  .setFullMethodName(generateFullMethodName(SERVICE_NAME, "unaryAsync"))
                  .setSampledToLocalTracing(true)
                  .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                      com.photowey.grpc.in.action.api.HelloProto.HelloRequest.getDefaultInstance()))
                  .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                      com.photowey.grpc.in.action.api.HelloProto.HelloResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new HelloServiceMethodDescriptorSupplier("unaryAsync"))
                  .build();
        }
      }
    }
    return getUnaryAsyncMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HelloServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HelloServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<HelloServiceStub>() {
            @java.lang.Override
            public HelloServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new HelloServiceStub(channel, callOptions);
            }
        };
    return HelloServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HelloServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HelloServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<HelloServiceBlockingStub>() {
            @java.lang.Override
            public HelloServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new HelloServiceBlockingStub(channel, callOptions);
            }
        };
    return HelloServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HelloServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HelloServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<HelloServiceFutureStub>() {
            @java.lang.Override
            public HelloServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new HelloServiceFutureStub(channel, callOptions);
            }
        };
    return HelloServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void unary(com.photowey.grpc.in.action.api.HelloProto.HelloRequest request,
                       io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUnaryMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingRequest> clientStreaming(
        io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getClientStreamingMethod(), responseObserver);
    }

    /**
     */
    default void serverStreaming(com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingRequest request,
                                 io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getServerStreamingMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingRequest> bidiStreaming(
        io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getBidiStreamingMethod(), responseObserver);
    }

    /**
     */
    default void unaryAsync(com.photowey.grpc.in.action.api.HelloProto.HelloRequest request,
                            io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUnaryAsyncMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service HelloService.
   */
  public static abstract class HelloServiceImplBase
      implements io.grpc.BindableService, AsyncService {

      @java.lang.Override
      public final io.grpc.ServerServiceDefinition bindService() {
      return HelloServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service HelloService.
   */
  public static final class HelloServiceStub
      extends io.grpc.stub.AbstractAsyncStub<HelloServiceStub> {
    private HelloServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HelloServiceStub(channel, callOptions);
    }

    /**
     */
    public void unary(com.photowey.grpc.in.action.api.HelloProto.HelloRequest request,
                      io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUnaryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingRequest> clientStreaming(
        io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getClientStreamingMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void serverStreaming(com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingRequest request,
                                io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getServerStreamingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingRequest> bidiStreaming(
        io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getBidiStreamingMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void unaryAsync(com.photowey.grpc.in.action.api.HelloProto.HelloRequest request,
                           io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUnaryAsyncMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service HelloService.
   */
  public static final class HelloServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<HelloServiceBlockingStub> {
    private HelloServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HelloServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.photowey.grpc.in.action.api.HelloProto.HelloResponse unary(com.photowey.grpc.in.action.api.HelloProto.HelloRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUnaryMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingResponse> serverStreaming(
        com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getServerStreamingMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.photowey.grpc.in.action.api.HelloProto.HelloResponse unaryAsync(com.photowey.grpc.in.action.api.HelloProto.HelloRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUnaryAsyncMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service HelloService.
   */
  public static final class HelloServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<HelloServiceFutureStub> {
    private HelloServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HelloServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.photowey.grpc.in.action.api.HelloProto.HelloResponse> unary(
        com.photowey.grpc.in.action.api.HelloProto.HelloRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUnaryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.photowey.grpc.in.action.api.HelloProto.HelloResponse> unaryAsync(
        com.photowey.grpc.in.action.api.HelloProto.HelloRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUnaryAsyncMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_UNARY = 0;
  private static final int METHODID_SERVER_STREAMING = 1;
  private static final int METHODID_UNARY_ASYNC = 2;
  private static final int METHODID_CLIENT_STREAMING = 3;
  private static final int METHODID_BIDI_STREAMING = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UNARY:
          serviceImpl.unary((com.photowey.grpc.in.action.api.HelloProto.HelloRequest) request,
              (io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloResponse>) responseObserver);
          break;
        case METHODID_SERVER_STREAMING:
          serviceImpl.serverStreaming((com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingRequest) request,
              (io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingResponse>) responseObserver);
          break;
        case METHODID_UNARY_ASYNC:
          serviceImpl.unaryAsync((com.photowey.grpc.in.action.api.HelloProto.HelloRequest) request,
              (io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CLIENT_STREAMING:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.clientStreaming(
              (io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingResponse>) responseObserver);
        case METHODID_BIDI_STREAMING:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.bidiStreaming(
              (io.grpc.stub.StreamObserver<com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
            getUnaryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
                new MethodHandlers<
                    com.photowey.grpc.in.action.api.HelloProto.HelloRequest,
                    com.photowey.grpc.in.action.api.HelloProto.HelloResponse>(
                    service, METHODID_UNARY)))
        .addMethod(
            getClientStreamingMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
                new MethodHandlers<
                    com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingRequest,
                    com.photowey.grpc.in.action.api.HelloProto.HelloClientStreamingResponse>(
                    service, METHODID_CLIENT_STREAMING)))
        .addMethod(
            getServerStreamingMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
                new MethodHandlers<
                    com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingRequest,
                    com.photowey.grpc.in.action.api.HelloProto.HelloServerStreamingResponse>(
                    service, METHODID_SERVER_STREAMING)))
        .addMethod(
            getBidiStreamingMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
                new MethodHandlers<
                    com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingRequest,
                    com.photowey.grpc.in.action.api.HelloProto.HelloBidiStreamingResponse>(
                    service, METHODID_BIDI_STREAMING)))
        .addMethod(
            getUnaryAsyncMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
                new MethodHandlers<
                    com.photowey.grpc.in.action.api.HelloProto.HelloRequest,
                    com.photowey.grpc.in.action.api.HelloProto.HelloResponse>(
                    service, METHODID_UNARY_ASYNC)))
        .build();
  }

  private static abstract class HelloServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HelloServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.photowey.grpc.in.action.api.HelloProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HelloService");
    }
  }

  private static final class HelloServiceFileDescriptorSupplier
      extends HelloServiceBaseDescriptorSupplier {
    HelloServiceFileDescriptorSupplier() {}
  }

  private static final class HelloServiceMethodDescriptorSupplier
      extends HelloServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    HelloServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HelloServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HelloServiceFileDescriptorSupplier())
              .addMethod(getUnaryMethod())
              .addMethod(getClientStreamingMethod())
              .addMethod(getServerStreamingMethod())
              .addMethod(getBidiStreamingMethod())
              .addMethod(getUnaryAsyncMethod())
              .build();
        }
      }
    }
    return result;
  }
}
