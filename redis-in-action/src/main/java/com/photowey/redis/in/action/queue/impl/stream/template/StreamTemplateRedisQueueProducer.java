package com.photowey.redis.in.action.queue.impl.stream.template;

import com.alibaba.fastjson.JSON;
import com.photowey.redis.in.action.engine.redis.IRedisEngine;
import com.photowey.redis.in.action.queue.impl.stream.StreamQueueMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * {@code StreamRedisQueueProducer}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Slf4j
@Component
public class StreamTemplateRedisQueueProducer implements IStreamTemplateRedisQueueProducer {

    private final IRedisEngine redisEngine;

    public StreamTemplateRedisQueueProducer(IRedisEngine redisEngine) {
        this.redisEngine = redisEngine;
    }

    @Override
    public RecordId produce(String queue, StreamQueueMessage message) {
        if (log.isInfoEnabled()) {
            log.info("redis:stream:mq:template:queue produce message on queue:[{}],message is:{}", queue, JSON.toJSONString(message));
        }
        ObjectRecord<String, StreamQueueMessage> record = StreamRecords.objectBacked(message).withStreamKey(queue);
        // ObjectHashMapper hashMapper = this.redisEngine.applicationContext().getBean(ObjectHashMapper.class);

        return this.redisEngine.redisTemplate().opsForStream().add(record);
    }

    @Override
    public RecordId produce(String queue, Map<String, String> message) {
        if (log.isInfoEnabled()) {
            log.info("redis:stream:mq:template:queue produce message on queue:[{}],message is:{}", queue, JSON.toJSONString(message));
        }
        return this.redisEngine.redisTemplate().opsForStream().add(queue, message);
    }
}
