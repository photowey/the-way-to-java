package com.photowey.netty.telnet.in.action.order;

/**
 * {@code PriorityOrdered}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public interface PriorityOrdered {

    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    int DEFAULT_PRECEDENCE = 100;

    int getPriority();
}
