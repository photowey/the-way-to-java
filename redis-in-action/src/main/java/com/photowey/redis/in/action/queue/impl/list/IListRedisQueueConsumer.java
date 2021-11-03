package com.photowey.redis.in.action.queue.impl.list;

import com.photowey.redis.in.action.queue.IRedisQueue;
import org.springframework.context.ApplicationContextAware;

/**
 * {@code IListRedisQueueConsumer}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public interface IListRedisQueueConsumer extends IRedisQueue, ApplicationContextAware {

    void consume(String queue);

    void consume(int timeout, String queue);
}
