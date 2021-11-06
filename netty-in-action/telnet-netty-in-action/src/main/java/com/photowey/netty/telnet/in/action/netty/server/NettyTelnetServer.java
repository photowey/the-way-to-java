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
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new NettyTelnetInitializer());

        channel = serverBootstrap.bind(port).sync().channel();
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
