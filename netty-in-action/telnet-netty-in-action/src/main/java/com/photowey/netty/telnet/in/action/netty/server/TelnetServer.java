package com.photowey.netty.telnet.in.action.netty.server;

/**
 * {@code TelnetServer}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public interface TelnetServer extends RemotingServer {

    void open() throws InterruptedException;
}
