package com.photowey.redis.in.action.queue.impl.zset;

import com.photowey.redis.in.action.engine.redis.IRedisEngine;
import com.photowey.redis.in.action.queue.event.zset.ZSetEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@code ZSetRedisQueueConsumer}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Slf4j
@Component
public class ZSetRedisQueueConsumer implements IZSetRedisQueueConsumer {

    private final IRedisEngine redisEngine;
    private ApplicationContext applicationContext;
    private ScheduledExecutorService scheduledExecutorService;

    public ZSetRedisQueueConsumer(IRedisEngine redisEngine) {
        this.redisEngine = redisEngine;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void consume(String queue) {
        try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
            this.scheduledExecutorService.scheduleWithFixedDelay(() -> {
                Set<String> messages = jedis.zrangeByScore(queue, 0, System.currentTimeMillis(), 0, 1);
                if (this.isNotEmpty(messages)) {
                    String message = messages.iterator().next();
                    if (jedis.zrem(queue, message) > 0) {
                        if (log.isInfoEnabled()) {
                            log.info("redis:zset:mq consume delayed message on queue:[{}], message is:{}", queue, message);
                        }
                        this.applicationContext.publishEvent(new ZSetEvent(message));
                    }
                }
            }, 1, 1, TimeUnit.SECONDS);
        }
    }

    private boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !CollectionUtils.isEmpty(collection);
    }
}
