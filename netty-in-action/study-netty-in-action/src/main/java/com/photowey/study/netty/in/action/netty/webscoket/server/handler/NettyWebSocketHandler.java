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
package com.photowey.study.netty.in.action.netty.webscoket.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@code NettyWebSocketHandler}
 *
 * @author photowey
 * @date 2022/03/26
 * @since 1.0.0
 */
@Slf4j
public class NettyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("receive the bower message:{}", msg.text());
        String now = formatter.format(LocalDateTime.now());
        ctx.channel().writeAndFlush(new TextWebSocketFrame(String.format("now: %s msg:%s", now, msg.text())));
    }

    /**
     * 新增连接
     *
     * @param ctx {@link ChannelHandlerContext}
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 16-8-8-16-8
        // 005056fffec00001-00003fb4-00000001-32c19d1cf90364eb-6ad466ad
        log.info("handler add::asLongText:[{}]", ctx.channel().id().asLongText());
        // 6ad466ad
        log.info("handler add::asShortText:[{}]", ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handler removed:::[{}]", ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("handler exception caught:::[{}]", ctx.channel().id().asLongText());
        ctx.close();
    }
}
