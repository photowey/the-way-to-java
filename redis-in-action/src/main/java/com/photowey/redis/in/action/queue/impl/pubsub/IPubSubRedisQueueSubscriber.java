package com.photowey.redis.in.action.queue.impl.pubsub;

import org.springframework.context.ApplicationContextAware;

/**
 * {@code IPubSubRedisQueueSubscriber}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public interface IPubSubRedisQueueSubscriber extends IPubSubRedisQueue, ApplicationContextAware {

    void subscribe(String... channels);
}
