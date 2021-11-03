package com.photowey.redis.in.action.queue.impl.zset;

import com.photowey.redis.in.action.engine.redis.IRedisEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * {@code ListRedisQueue}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Slf4j
@Component
public class ZSetRedisQueueProducer implements IZSetRedisQueueProducer {

    private final IRedisEngine redisEngine;

    public ZSetRedisQueueProducer(IRedisEngine redisEngine) {
        this.redisEngine = redisEngine;
    }

    @Override
    public void produceDelayed(String queue, String message, long delayedMillis) {
        try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
            if (log.isInfoEnabled()) {
                log.info("redis:zset:mq produce delayed message on queue:[{}], delayed:[{}], message is:{}", queue, delayedMillis, message);
            }
            jedis.zadd(queue, delayedMillis, message);
        }
    }
}
