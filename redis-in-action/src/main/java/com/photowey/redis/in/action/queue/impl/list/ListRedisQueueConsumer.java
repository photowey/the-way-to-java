package com.photowey.redis.in.action.queue.impl.list;

import com.photowey.redis.in.action.engine.redis.IRedisEngine;
import com.photowey.redis.in.action.queue.event.list.ListEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@code ListRedisQueueConsumer}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Slf4j
@Component
public class ListRedisQueueConsumer implements IListRedisQueueConsumer {

    private final IRedisEngine redisEngine;
    private ApplicationContext applicationContext;
    private ScheduledExecutorService scheduledExecutorService;

    public ListRedisQueueConsumer(IRedisEngine redisEngine) {
        this.redisEngine = redisEngine;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void consume(String queue) {
        this.consume(0, queue);
    }

    @Override
    public void consume(int timeout, String queue) {
        this.scheduledExecutorService.scheduleAtFixedRate(() -> {
            try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
                List<String> messages = jedis.brpop(timeout, queue);
                if (this.isNotEmpty(messages)) {
                    messages.forEach((message) -> {
                        if (log.isInfoEnabled()) {
                            log.info("redis:list:mq consume message on queue:[{}],message is:[{}]", queue, message);
                        }
                        this.applicationContext.publishEvent(new ListEvent(message));
                    });
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    private boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !CollectionUtils.isEmpty(collection);
    }
}
