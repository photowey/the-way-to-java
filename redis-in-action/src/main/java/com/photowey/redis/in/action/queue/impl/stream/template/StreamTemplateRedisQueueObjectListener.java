package com.photowey.redis.in.action.queue.impl.stream.template;

import com.alibaba.fastjson.JSON;
import com.photowey.redis.in.action.engine.redis.IRedisEngine;
import com.photowey.redis.in.action.queue.impl.stream.StreamQueueMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.stream.StreamListener;

/**
 * {@code StreamTemplateRedisQueueObjectListener}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Slf4j
//@Component
public class StreamTemplateRedisQueueObjectListener implements StreamListener<String, ObjectRecord<String, StreamQueueMessage>> {

    private final IRedisEngine redisEngine;

    public StreamTemplateRedisQueueObjectListener(IRedisEngine redisEngine) {
        this.redisEngine = redisEngine;
    }

    @Override
    public void onMessage(ObjectRecord<String, StreamQueueMessage> objectRecord) {
        StreamQueueMessage message = objectRecord.getValue();
        String queue = objectRecord.getStream();
        if (log.isInfoEnabled()) {
            log.info("redis:stream:mq:template:queue on message on queue:[{}],message is:{}", queue, JSON.toJSONString(message));
        }
        Long ack = this.ack(queue, message.getGroup(), objectRecord.getId());
        if (log.isInfoEnabled()) {
            log.info("redis:stream:mq:template:queue ack message on queue:[{}],message is:{}, ack:[{}], successfully...", queue, ack, JSON.toJSONString(message));
        }
    }

    public Long ack(String queue, String group, RecordId id) {
        return this.batchAck(queue, group, id);
    }

    public Long batchAck(String queue, String group, RecordId... ids) {
        return this.redisEngine.redisTemplate().opsForStream().acknowledge(queue, group, ids);
    }
}
