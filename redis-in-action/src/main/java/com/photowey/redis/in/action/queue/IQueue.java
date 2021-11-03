package com.photowey.redis.in.action.queue;

/**
 * {@code IQueue}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public interface IQueue {

    default void produce(String queue, String message) {

    }

}
