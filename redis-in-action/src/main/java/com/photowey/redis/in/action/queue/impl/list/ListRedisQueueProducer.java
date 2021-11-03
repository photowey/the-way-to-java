package com.photowey.redis.in.action.queue.impl.list;

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
public class ListRedisQueueProducer implements IListRedisQueueProducer {

    private final IRedisEngine redisEngine;

    public ListRedisQueueProducer(IRedisEngine redisEngine) {
        this.redisEngine = redisEngine;
    }

    @Override
    public void produce(String queue, String message) {
        try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
            if (log.isInfoEnabled()) {
                log.info("redis:list:mq produce message on queue:[{}],message is:[{}]", queue, message);
            }
            jedis.lpush(queue, message);
        }
    }
}
