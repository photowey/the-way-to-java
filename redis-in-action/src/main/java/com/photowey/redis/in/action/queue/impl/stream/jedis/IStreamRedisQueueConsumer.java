package com.photowey.redis.in.action.queue.impl.stream.jedis;

import com.photowey.redis.in.action.queue.impl.stream.IStreamRedisQueue;
import org.springframework.context.ApplicationContextAware;
import redis.clients.jedis.StreamEntryID;

/**
 * {@code IStreamRedisQueue}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public interface IStreamRedisQueueConsumer extends IStreamRedisQueue, ApplicationContextAware {

    void consume(String queue, String customer, String group);

    Long ack(String queue, String group, StreamEntryID id);

    Long batchAck(String queue, String group, StreamEntryID... ids);

    void createGroup(String queue, String group, String offset);

    boolean checkGroup(String queue, String group);
}
