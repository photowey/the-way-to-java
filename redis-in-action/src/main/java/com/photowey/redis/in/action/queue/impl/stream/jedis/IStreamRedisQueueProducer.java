package com.photowey.redis.in.action.queue.impl.stream.jedis;

import com.photowey.redis.in.action.queue.impl.stream.IStreamRedisQueue;
import redis.clients.jedis.StreamEntryID;

import java.util.Map;

/**
 * {@code IStreamRedisQueue}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public interface IStreamRedisQueueProducer extends IStreamRedisQueue {

    StreamEntryID produce(String queue, Map<String, String> message);
}
