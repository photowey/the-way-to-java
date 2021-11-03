package com.photowey.redis.in.action.queue.impl.zset;

import org.springframework.context.ApplicationContextAware;

/**
 * {@code IZSetRedisQueueConsumer}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public interface IZSetRedisQueueConsumer extends IZSetRedisQueue, ApplicationContextAware {

    void consume(String queue);
}
