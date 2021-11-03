package com.photowey.redis.in.action.queue.impl.zset;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code IZSetRedisQueueTest}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@SpringBootTest
class IZSetRedisQueueTest {

    @Autowired
    private IZSetRedisQueueConsumer consumer;
    @Autowired
    private IZSetRedisQueueProducer producer;

    private static final String QUEUE_NAME = "redis:zset:mq:queue:key";

    @Test
    void testProduceDelayedMessage() {
        for (int i = 0; i < 10; i++) {
            long delayedMillis = System.currentTimeMillis() + 10 * 1000;
            this.producer.produceDelayed(QUEUE_NAME, "我是ZSetQueue message. id:" + (i + 1), delayedMillis);
        }
    }

    @Test
    @SneakyThrows
    void testConsumeMessage() {
        this.consumer.consume(QUEUE_NAME);
        Thread.sleep(15000);
    }

}