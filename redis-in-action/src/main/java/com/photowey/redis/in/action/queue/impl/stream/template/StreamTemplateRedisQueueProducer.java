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
