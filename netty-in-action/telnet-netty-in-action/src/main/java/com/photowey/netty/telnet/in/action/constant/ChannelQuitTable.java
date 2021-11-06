package com.photowey.netty.telnet.in.action.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ChannelQuitTable}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public final class ChannelQuitTable {

    private ChannelQuitTable() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static final List<String> CHANNEL_QUIT_TABLE = new ArrayList<>();

    static {
        CHANNEL_QUIT_TABLE.add("quit");
        CHANNEL_QUIT_TABLE.add("q");
        CHANNEL_QUIT_TABLE.add("exit");
    }

}
