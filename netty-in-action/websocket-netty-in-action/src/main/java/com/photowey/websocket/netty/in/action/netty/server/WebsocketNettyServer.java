/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.websocket.netty.in.action.netty.server;

import com.photowey.websocket.netty.in.action.netty.handler.WebsocketHelloHandler;
import com.photowey.websocket.netty.in.action.netty.property.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@code WebsocketNettyServer}
 *
 * @author photowey
 * @date 2022/02/13
 * @since 1.0.0
 */
@Slf4j
@Component
public class WebsocketNettyServer implements SmartInitializingSingleton, DisposableBean {

    private final ExecutorService executorService;

    private ServerBootstrap bootstrap;

    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private Channel channel;

    @Autowired
    private NettyProperties nettyProperties;

    public WebsocketNettyServer() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.executorService.execute(this::start);
    }

    @Override
    public void destroy() throws Exception {
        this.shutdown();
    }

    public void start() {
        this.boss = new NioEventLoopGroup();
        this.worker = new NioEventLoopGroup();

        this.bootstrap = new ServerBootstrap();
        this.bootstrap.group(this.boss, this.worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("http-codec", new HttpServerCodec());
                        pipeline.addLast("aggergator", new HttpObjectAggregator(65536));
                        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                        pipeline.addLast("websocket-handler",
                                new WebSocketServerProtocolHandler(nettyProperties.getPath(), null, false, 1024 * 1024 * 3));
                        pipeline.addLast("hello-handler", new WebsocketHelloHandler(nettyProperties));
                    }
                });
        try {
            this.channel = this.bootstrap.bind(this.nettyProperties.getPort()).sync().channel();
            log.info("the netty websocket server started on ports:[{}] (tcp) with websocket path:[{}]", this.nettyProperties.getPort(), this.nettyProperties.getPath());
            this.channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("the netty  websocket server start failure", e);
        }
    }

    public void shutdown() {
        this.shutdownSafe();
    }

    private void shutdownSafe() {
        if (null != channel) {
            channel.close();
        }
        if (null != boss) {
            boss.shutdownGracefully();
        }
        if (null != worker) {
            worker.shutdownGracefully();
        }
    }
}
