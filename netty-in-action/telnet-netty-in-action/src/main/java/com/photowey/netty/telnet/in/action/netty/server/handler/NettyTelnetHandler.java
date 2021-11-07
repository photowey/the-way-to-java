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
package com.photowey.netty.telnet.in.action.netty.server.handler;

import com.photowey.netty.telnet.in.action.constant.ChannelQuitTable;
import com.photowey.netty.telnet.in.action.netty.command.NamedCommandHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * {@code NettyTelnetHandler}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class NettyTelnetHandler extends SimpleChannelInboundHandler<String> {

    private NamedCommandHandler commandHandler = new NamedCommandHandler();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.write(this.commandHandler.handlePrompt());
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (ChannelQuitTable.CHANNEL_QUIT_TABLE.contains(msg)) {
            ctx.channel().close();
            return;
        }
        ctx.write(this.commandHandler.handleRequest(msg));
        ctx.flush();
    }
}
