/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.study.netty.in.action.netty.webscoket.server.initializer;

import com.photowey.study.netty.in.action.netty.webscoket.server.handler.NettyWebSocketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * {@code NettyWebSocketInitializer}
 *
 * @author photowey
 * @date 2022/03/26
 * @since 1.0.0
 */
public class NettyWebSocketInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast("WebsocketHttpServerCodec", new HttpServerCodec())
                .addLast("WebsocketChunkedWriteHandler", new ChunkedWriteHandler())
                .addLast("WebsocketHttpObjectAggregator", new HttpObjectAggregator(8192))
                // ws://localhost:8888/ws/xxx
                // 将 http 协议升级为 ws 协议 -> 保持长连接
                .addLast("WebsocketWebSocketServerProtocolHandler", new WebSocketServerProtocolHandler("/ws"))
                // 自定义 handler
                .addLast("WebsocketNettyWebSocketHandler", new NettyWebSocketHandler());
    }
}
