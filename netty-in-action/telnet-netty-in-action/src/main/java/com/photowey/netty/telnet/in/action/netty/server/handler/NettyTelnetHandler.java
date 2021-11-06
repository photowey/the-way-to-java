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
