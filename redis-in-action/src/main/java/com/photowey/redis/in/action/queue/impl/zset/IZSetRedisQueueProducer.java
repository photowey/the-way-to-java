package com.photowey.redis.in.action.queue.impl.zset;

/**
 * {@code IZSetRedisQueueProducer}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public interface IZSetRedisQueueProducer extends IZSetRedisQueue {

    void produceDelayed(String queue, String message, long delayedMillis);
}
