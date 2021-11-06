package com.photowey.netty.telnet.in.action.netty.server;

/**
 * {@code RemotingServer}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public interface RemotingServer {

    void run() throws InterruptedException;

    void shutdown();
}
