package com.photowey.redis.in.action.queue.impl.pubsub;

/**
 * {@code IPubSubRedisQueuePublisher}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public interface IPubSubRedisQueuePublisher extends IPubSubRedisQueue {

    void publish(String channel, String message);
}
