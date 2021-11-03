package com.photowey.redis.in.action.queue.impl.list;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code IListRedisQueueTest}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@SpringBootTest
class IListRedisQueueTest {

    @Autowired
    private IListRedisQueueConsumer consumer;
    @Autowired
    private IListRedisQueueProducer producer;

    private static final String QUEUE_NAME = "redis:list:mq:key:queue";

    @Test
    void testProduceMessage() {
        for (int i = 0; i < 10; i++) {
            this.producer.produce(QUEUE_NAME, "我是ListQueue message. id:" + (i + 1));
        }
    }

    @Test
    @SneakyThrows
    void testConsumeMessage() {
        this.consumer.consume(QUEUE_NAME);
        Thread.sleep(10000);
    }

}