package com.photowey.netty.telnet.in.action.netty.session;

import com.photowey.netty.telnet.in.action.service.NamedService;

/**
 * {@code TelnetServerService}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public interface TelnetServerService extends NamedService {

    void run();

    void shutdown();
}
