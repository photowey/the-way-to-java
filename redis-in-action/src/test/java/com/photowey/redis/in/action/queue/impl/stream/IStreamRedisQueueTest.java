/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.redis.in.action.queue.impl.stream;

import com.photowey.redis.in.action.queue.impl.stream.jedis.IStreamRedisQueueConsumer;
import com.photowey.redis.in.action.queue.impl.stream.jedis.IStreamRedisQueueProducer;
import com.photowey.redis.in.action.queue.impl.stream.template.IStreamTemplateRedisQueueProducer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.convert.MappingRedisConverter;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.hash.ObjectHashMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.photowey.redis.in.action.queue.impl.stream.template.TemplateStreamQueueConstants.TEMPLATE_STREAM_GROUP_NAME;
import static com.photowey.redis.in.action.queue.impl.stream.template.TemplateStreamQueueConstants.TEMPLATE_STREAM_QUEUE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@code IStreamRedisQueueTest}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@SpringBootTest
class IStreamRedisQueueTest {

    @Autowired
    private IStreamRedisQueueConsumer consumer;
    @Autowired
    private IStreamRedisQueueProducer producer;
    @Autowired
    private IStreamTemplateRedisQueueProducer templateProducer;

    private static final String STREAM_QUEUE_NAME = "redis:stream:mq:key:queue";
    private static final String STREAM_CONSUMER_NAME = "redis:stream:mq:key:consumer";
    private static final String STREAM_GROUP_NAME = "redis:stream:mq:key:group";

    @Test
    void testProduceMessage() {
        for (int i = 0; i < 10; i++) {
            Map<String, String> context = new HashMap<>();
            context.put("name", "photowey");
            context.put("description", "我是StreamQueue message. id:" + (i + 1));
            context.put("age", "" + (18 + i + 1));
            this.producer.produce(STREAM_QUEUE_NAME, context);
        }
    }

    @Test
    @SneakyThrows
    void testConsumeMessage() {
        this.consumer.consume(STREAM_QUEUE_NAME, STREAM_CONSUMER_NAME, STREAM_GROUP_NAME);
        Thread.sleep(60000);
    }

    // ===================================================

    @Test
    @SneakyThrows
    void testProduceTemplateMessage() {
        for (int i = 0; i < 10; i++) {
            StreamQueueMessage message = new StreamQueueMessage();
            message.setName("photowey");
            message.setAge((18 + i + 1));
            message.setContent("我是StreamQueue message. id:" + (i + 1));
            message.setGroup(TEMPLATE_STREAM_GROUP_NAME);

            RecordId produces = this.templateProducer.produce(TEMPLATE_STREAM_QUEUE_NAME, message);
            System.out.println(produces.getValue());
        }
        Thread.sleep(20000);
    }

    @Test
    @SneakyThrows
    void testProduceTemplateMapMessage() {
        for (int i = 0; i < 10; i++) {
            Map<String, String> context = new HashMap<>();
            context.put("name", "photowey");
            context.put("description", "我是StreamQueue map message. id:" + (i + 1));
            context.put("group", TEMPLATE_STREAM_GROUP_NAME);
            context.put("age", "" + (18 + i + 1));

            RecordId produces = this.templateProducer.produce(TEMPLATE_STREAM_QUEUE_NAME, context);
            System.out.println(produces.getValue());
        }
        Thread.sleep(20000);
    }


    @Test // DATAREDIS-1179
    public void hashMapperAllowsReuseOfRedisConverter/*and thus the MappingContext holding eg. TypeAlias information*/() {

        StreamQueueMessage source = new StreamQueueMessage();
        source.setName("setName");
        source.setAge(10);
        source.setContent("setContent");
        source.setGroup("setGroup");

        Map<byte[], byte[]> hash = new ObjectHashMapper().toHash(source);

        RedisMappingContext ctx = new RedisMappingContext();
        ctx.setInitialEntitySet(Collections.singleton(StreamQueueMessage.class));
        ctx.afterPropertiesSet();

        MappingRedisConverter mappingRedisConverter = new MappingRedisConverter(ctx);
        mappingRedisConverter.afterPropertiesSet();

        ObjectHashMapper objectHashMapper = new ObjectHashMapper(mappingRedisConverter);
        assertThat(objectHashMapper.fromHash(hash)).isEqualTo(source);
    }
}