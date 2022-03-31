/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.photowey.study.netty.in.action.netty.example.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * Echoes back any received data from a client.
 */
public final class EchoServer {

    /**
     * 流程
     * 1.接收连接
     * 2.创建一个新的 {@link io.netty.channel.socket.nio.NioSocketChannel}
     * 3.注册到一个 {@code worker} {@link io.netty.channel.nio.NioEventLoop}
     * 4.注册 {@code selector Read} 事件
     */

    /**
     * {@link ChannelPipeline}
     *
     * {@link SocketChannel} 和 {@link ChannelPipeline} 一一对应
     * {@link ChannelPipeline} 内部有多个 {@link ChannelHandlerContext}
     * -- -------------------------------------------------------------------------------
     * // io.netty.channel.AbstractChannel#newChannelPipeline()
     * pipeline = newChannelPipeline();
     * -- -------------------------------------------------------------------------------
     *
     */

    /**
     * {@link ChannelHandlerContext}
     * {@link ChannelHandlerContext} 是对 {@link ChannelHandler} 的封装
     * {@link ChannelHandlerContext#fireChannelRead(Object)}
     */

    /**
     * {@link ChannelHandler}
     * 处理 {@code I/O} 事件 或者 拦截 I/O 事件, 并将其转发非下一个处理程序 {@link ChannelHandler}
     * <p>
     * {@link ChannelHandler} 的 事件处理 分为:
     * 1.入站 {@link ChannelInboundHandler}
     * 2.出站 {@link ChannelOutboundHandler}
     * <p>
     * {@link ChannelInboundHandler#channelActive(ChannelHandlerContext)}
     * -- 用于 {@link Channel} 处于活动状态时被调用
     * {@link ChannelInboundHandler#channelRead(ChannelHandlerContext, Object)} }
     * -- 当从 {@link Channel} 中 读取数据时被调用
     */

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            //p.addLast(new EchoServerHandler());
                        }
                    });

            // Start the server.
            ChannelFuture f = b.bind(PORT).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
