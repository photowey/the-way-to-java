package com.photowey.netty.telnet.in.action.netty.server.initalizer;

import com.google.common.collect.Lists;
import com.photowey.netty.telnet.in.action.constant.NamedConstants;
import com.photowey.netty.telnet.in.action.netty.server.handler.NettyTelnetHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * {@code NettyTelnetInitializer}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class NettyTelnetInitializer extends ChannelInitializer<SocketChannel> {

    private static final StringDecoder DECODER = new StringDecoder(StandardCharsets.UTF_8);
    private static final StringEncoder ENCODER = new StringEncoder(Charset.forName("GBK"));

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        if (isOpenSecurity()) {
            boolean notLocal = ALLOWED_LIST.contains(channel.remoteAddress().getHostName());
            if (notLocal) {
                return;
            }
        }

        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new DelimiterBasedFrameDecoder(2 << 10, Delimiters.lineDelimiter()));
        pipeline.addLast(ENCODER);
        pipeline.addLast(DECODER);
        pipeline.addLast(new NettyTelnetHandler());
    }

    private static final List<String> ALLOWED_LIST = Lists.newArrayList("127.0.0.1", "localhost");

    public static boolean isOpenSecurity() {
        return System.getProperty(NamedConstants.TELNET_SERVER_SECURITY_ENABLE, "false").equalsIgnoreCase("true");
    }

}