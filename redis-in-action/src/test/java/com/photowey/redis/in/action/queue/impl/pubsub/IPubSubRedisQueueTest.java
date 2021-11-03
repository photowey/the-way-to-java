package com.photowey.redis.in.action.queue.impl.pubsub;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code IPubSubRedisQueueTest}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@SpringBootTest
class IPubSubRedisQueueTest {

    @Autowired
    private IPubSubRedisQueueSubscriber subscriber;
    @Autowired
    private IPubSubRedisQueuePublisher publisher;

    private static final String CHANNEL_NAME = "redis:pub:sub:mq:key:channel";

    @Test
    void testPublishMessage() {
        for (int i = 0; i < 10; i++) {
            this.publisher.publish(CHANNEL_NAME, "我是PubSubQueue message. id:" + (i + 1));
        }
    }

    @Test
    @SneakyThrows
    void testSubscribeMessage() {
        this.subscriber.subscribe(CHANNEL_NAME);
    }

}