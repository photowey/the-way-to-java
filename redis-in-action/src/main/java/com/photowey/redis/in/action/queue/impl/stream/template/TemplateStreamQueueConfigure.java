package com.photowey.redis.in.action.queue.impl.stream.template;

import com.photowey.redis.in.action.engine.redis.IRedisEngine;
import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;

import static com.photowey.redis.in.action.queue.impl.stream.template.TemplateStreamQueueConstants.*;

/**
 * {@code TemplateStreamQueueConfigure}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class TemplateStreamQueueConfigure {

    @Autowired
    private IRedisEngine redisEngine;
    @Autowired
    private StreamListener<String, MapRecord<String, String, String>> streamListener;

    @Autowired
    private StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer;

    @Bean(destroyMethod = "stop")
    public StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .batchSize(10)
                .pollTimeout(Duration.ZERO)
                .build();

        return StreamMessageListenerContainer.create(connectionFactory, options);
    }

    // =======================================================================================================

    @Bean
    public Subscription subscription(StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer) {
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
