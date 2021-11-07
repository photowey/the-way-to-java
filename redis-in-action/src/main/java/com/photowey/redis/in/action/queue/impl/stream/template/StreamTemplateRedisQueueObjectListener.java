/*
 * Copyright © 2021 photowey (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
