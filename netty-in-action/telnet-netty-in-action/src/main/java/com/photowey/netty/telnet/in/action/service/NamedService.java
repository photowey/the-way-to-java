package com.photowey.netty.telnet.in.action.service;

import com.photowey.netty.telnet.in.action.order.PriorityOrdered;

/**
 * {@code NamedService}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public interface NamedService extends PriorityOrdered {

    void init() throws RuntimeException;

    void dispose() throws RuntimeException;
}

