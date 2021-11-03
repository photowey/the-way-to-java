package com.photowey.redis.in.action.queue.impl.pubsub;

import com.photowey.redis.in.action.engine.redis.IRedisEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * {@code RedisPubSubQueuePublisher}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Slf4j
@Component
public class RedisPubSubRedisQueuePublisher implements IPubSubRedisQueuePublisher {

    private final IRedisEngine redisEngine;

    public RedisPubSubRedisQueuePublisher(IRedisEngine redisEngine) {
        this.redisEngine = redisEngine;
    }

    @Override
    public void publish(String channel, String message) {
        try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
            if (log.isInfoEnabled()) {
                log.info("redis:pub/sub:mq produce message on channel:[{}],message is:{}", channel, message);
            }
            jedis.publish(channel, message);
        }
    }
}
