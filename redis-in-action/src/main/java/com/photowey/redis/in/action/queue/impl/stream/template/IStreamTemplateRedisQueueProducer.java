package com.photowey.redis.in.action.queue.impl.stream.template;

import com.photowey.redis.in.action.queue.impl.stream.IStreamRedisQueue;
import com.photowey.redis.in.action.queue.impl.stream.StreamQueueMessage;
import org.springframework.data.redis.connection.stream.RecordId;

import java.util.Map;

/**
 * {@code IStreamTemplateRedisQueueProducer}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public interface IStreamTemplateRedisQueueProducer extends IStreamRedisQueue {

    RecordId produce(String queue, StreamQueueMessage message);

    RecordId produce(String queue, Map<String, String> message);
}
