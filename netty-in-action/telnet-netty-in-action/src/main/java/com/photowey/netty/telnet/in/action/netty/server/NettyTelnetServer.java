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
package com.photowey.netty.telnet.in.action.netty.server;

import com.photowey.netty.telnet.in.action.netty.server.initalizer.NettyTelnetInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.Executor;

/**
 * {@code NettyTelnetServer}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class NettyTelnetServer implements TelnetServer {

    private ServerBootstrap serverBootstrap;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel channel;

    private int port;

    public NettyTelnetServer(int port, Executor executor) {
        this.port = port;
        bossGroup = new NioEventLoopGroup(1, executor);
        workerGroup = new NioEventLoopGroup(1, executor);
    }

    @Override
    public void open() throws InterruptedException {
        // TODO do something and run
        this.run();
    }

    @Override
    public void run() throws InterruptedException {
        try {
            this.serverBootstrap = new ServerBootstrap();
            this.serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            this.serverBootstrap
                    .group(this.bossGroup, this.workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new NettyTelnetInitializer());

            this.channel = serverBootstrap.bind(port).sync().channel();
            this.channel.closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.shutdown();
        }

    }

    @Override
    public void shutdown() {
        this.shutdownSafe();
    }

    private void shutdownSafe() {
        if (null != channel) {
            channel.close();
        }
        if (null != bossGroup) {
            bossGroup.shutdownGracefully();
        }
        if (null != workerGroup) {
            workerGroup.shutdownGracefully();
        }
    }
}
