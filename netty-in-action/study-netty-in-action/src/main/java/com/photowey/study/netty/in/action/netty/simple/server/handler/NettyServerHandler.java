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
package com.photowey.study.netty.in.action.netty.simple.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * {@code NettyServerHandler}
 *
 * @author photowey
 * @date 2022/03/13
 * @since 1.0.0
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        // async
        ctx.channel().eventLoop().execute(() -> {

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception ignore) {
            }
            log.info("--- do async biz logic-1 ---");
        });

        ctx.channel().eventLoop().execute(() -> {

            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (Exception ignore) {
            }
            log.info("--- do async biz logic-2 ---");
        });

        // schedule
        ctx.channel().eventLoop().schedule(() -> {
            log.info("--- do scheduled async biz logic ---");
        }, 5, TimeUnit.SECONDS);

        log.info("the remote client:[{}] request message is:[{}]", ctx.channel().remoteAddress(), buf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, 世界~", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("channel read exception", cause);
        ctx.close();
    }
}
