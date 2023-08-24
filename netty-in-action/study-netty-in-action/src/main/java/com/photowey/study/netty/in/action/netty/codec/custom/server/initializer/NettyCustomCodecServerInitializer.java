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
package com.photowey.study.netty.in.action.netty.codec.custom.server.initializer;

import com.photowey.study.netty.in.action.netty.codec.custom.codec.CustomByteToMessageDecoder;
import com.photowey.study.netty.in.action.netty.codec.custom.codec.CustomMessageToByteEncoder;
import com.photowey.study.netty.in.action.netty.codec.custom.server.handler.NettyCustomCodecServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * {@code NettyCustomCodecServerInitializer}
 *
 * @author photowey
 * @date 2022/03/28
 * @since 1.0.0
 */
public class NettyCustomCodecServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
//                .addLast(new CustomByteToMessageDecoderExt())
                .addLast(new CustomByteToMessageDecoder()).addLast(new CustomMessageToByteEncoder()).addLast(new NettyCustomCodecServerHandler());
    }
}
