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
package com.photowey.study.netty.in.action.netty.codec.custom.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code NettyCustomCodecClientHandler}
 *
 * @author photowey
 * @date 2022/03/28
 * @since 1.0.0
 */
@Slf4j
public class NettyCustomCodecClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        log.info("the remote server address is:{}", ctx.channel().remoteAddress());
        log.info("the remote server message is:{}", msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("request to remote server address is:{} --------------------------- 111111-111", ctx.channel().remoteAddress());
        ctx.writeAndFlush(12345654321L);
    }
}
