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
package com.photowey.study.netty.in.action.netty.simple.server;

import com.photowey.study.netty.in.action.netty.simple.server.initializer.NettyServerInitializer;
import com.photowey.study.netty.in.action.netty.simple.server.lisenter.ChannelFutureListenerImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code NettyServer}
 *
 * @author photowey
 * @date 2022/03/13
 * @since 1.0.0
 */
@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        start();
    }

    public static void start() {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new NettyServerInitializer());

            ChannelFuture channelFuture = bootstrap.bind(6888).sync();
            channelFuture.addListener(new ChannelFutureListenerImpl());

            // listener close channel
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("start the netty server exception", e);
            throw new RuntimeException(e);
        } finally {
            shutdownGracefully(bossGroup, workerGroup);
        }
    }

    private static void shutdownGracefully(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
