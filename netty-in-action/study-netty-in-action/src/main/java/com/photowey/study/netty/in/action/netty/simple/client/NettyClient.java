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
package com.photowey.study.netty.in.action.netty.simple.client;

import com.photowey.study.netty.in.action.netty.simple.client.initializer.NettyClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code NettyClient}
 *
 * @author photowey
 * @date 2022/03/13
 * @since 1.0.0
 */
@Slf4j
public class NettyClient {

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6888).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("start the netty client exception", e);
            throw new RuntimeException(e);
        } finally {
            shutdownGracefully(workerGroup);
        }
    }

    private static void shutdownGracefully(EventLoopGroup workerGroup) {
        workerGroup.shutdownGracefully();
    }

}
