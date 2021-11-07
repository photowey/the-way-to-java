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

import com.photowey.redis.in.action.engine.redis.IRedisEngine;
import com.photowey.redis.in.action.queue.impl.stream.StreamQueueMessage;
import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.convert.MappingRedisConverter;
import org.springframework.data.redis.core.convert.RedisConverter;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;
import java.util.Collections;

import static com.photowey.redis.in.action.queue.impl.stream.template.TemplateStreamQueueConstants.*;

/**
 * {@code TemplateObjectStreamQueueConfigure}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Slf4j
//@Configuration
public class TemplateObjectStreamQueueConfigure {

    @Autowired
    private IRedisEngine redisEngine;
    @Autowired
    private StreamListener<String, ObjectRecord<String, StreamQueueMessage>> streamListener;

    @Autowired
    private StreamMessageListenerContainer<String, ObjectRecord<String, StreamQueueMessage>> streamMessageListenerContainer;

    /**
     * Failed to convert from type
     * [org.springframework.data.redis.connection.stream.StreamRecords$ByteMapBackedRecord] to type
     * [com.photowey.redis.in.action.queue.impl.stream.StreamQueueMessage] for value
     * 'MapBackedRecord{xxx}'; nested exception is java.lang.IllegalArgumentException: Value must not be null!
     *
     * @see * https://github.com/spring-projects/spring-data-redis/blob/main/src/main/asciidoc/reference/redis-streams.adoc
     * @see * https://github.com/spring-projects/spring-data-redis/issues/1753
     */

    @Bean
    @Primary
    public RedisMappingContext redisMappingContext() {
        RedisMappingContext ctx = new RedisMappingContext();
        ctx.setInitialEntitySet(Collections.singleton(StreamQueueMessage.class));
        ctx.afterPropertiesSet();

        return ctx;
    }

    @Bean
    @Primary
    public RedisConverter redisConverter(RedisMappingContext mappingContext) {
        MappingRedisConverter redisConverter = new MappingRedisConverter(mappingContext);
        redisConverter.afterPropertiesSet();

        return redisConverter;
    }

    @Bean
    @Primary
    public ObjectHashMapper hashMapper(RedisConverter redisConverter) {
        return new ObjectHashMapper(redisConverter);
    }

    @Primary
    @Bean(destroyMethod = "stop")
    public StreamMessageListenerContainer<String, ObjectRecord<String, StreamQueueMessage>> streamMessageListenerContainer(
            RedisConnectionFactory connectionFactory, ObjectHashMapper hashMapper) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, StreamQueueMessage>> options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .batchSize(10)
                .pollTimeout(Duration.ZERO)
                .objectMapper(hashMapper)
                .targetType(StreamQueueMessage.class)
                .build();

        return StreamMessageListenerContainer.create(connectionFactory, options);
    }

    // =======================================================================================================

    @Bean
    public Subscription subscription(StreamMessageListenerContainer<String, ObjectRecord<String, StreamQueueMessage>> streamMessageListenerContainer) {
        boolean matched = this.checkGroup(TEMPLATE_STREAM_QUEUE_NAME, TEMPLATE_STREAM_GROUP_NAME);
        if (!matched) {
            this.createGroup(TEMPLATE_STREAM_QUEUE_NAME, TEMPLATE_STREAM_GROUP_NAME);
        }
        Subscription subscription = streamMessageListenerContainer.receive(
                Consumer.from(TEMPLATE_STREAM_GROUP_NAME, TEMPLATE_STREAM_CONSUMER_NAME),
                StreamOffset.create(TEMPLATE_STREAM_QUEUE_NAME, ReadOffset.lastConsumed()),
                streamListener);

        streamMessageListenerContainer.start();

        return subscription;
    }

    public void createGroup(String queue, String group) {
        this.redisEngine.redisTemplate().opsForStream().createGroup(queue, group);
    }

    public boolean checkGroup(String queue, String group) {
        try {
            StreamInfo.XInfoGroups infoGroups = this.redisEngine.redisTemplate().opsForStream().groups(queue);
            return infoGroups.stream().anyMatch(infoGroup -> infoGroup.groupName().equals(group));
        } catch (RedisSystemException | RedisException | InvalidDataAccessApiUsageException e) {
            log.info("handle check the redis-stream-group exists failure,the group not exists,and create next.");
        }

        return false;
    }
}
