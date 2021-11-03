package com.photowey.redis.in.action.queue.impl.stream.jedis;

import com.alibaba.fastjson.JSON;
import com.photowey.redis.in.action.engine.redis.IRedisEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntryID;

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
public class StreamRedisQueueProducer implements IStreamRedisQueueProducer {

    private final IRedisEngine redisEngine;

    public StreamRedisQueueProducer(IRedisEngine redisEngine) {
        this.redisEngine = redisEngine;
    }

    @Override
    public StreamEntryID produce(String queue, Map<String, String> message) {
        try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
            if (log.isInfoEnabled()) {
                log.info("redis:stream:mq produce message on queue:[{}],message is:{}", queue, JSON.toJSONString(message));
            }

            return jedis.xadd(queue, StreamEntryID.NEW_ENTRY, message);
        }
    }
}
