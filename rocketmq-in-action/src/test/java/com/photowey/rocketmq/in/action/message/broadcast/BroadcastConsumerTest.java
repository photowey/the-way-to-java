package com.photowey.rocketmq.in.action.message.broadcast;

import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code BroadcastConsumerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class BroadcastConsumerTest {

    @Autowired
    private BroadcastConsumer broadcastConsumer;

    @Test
    void testBroadcastMessage() throws MQClientException, InterruptedException {
        this.broadcastConsumer.consume();
        Thread.sleep(5_000);
    }

    @Test
    void testBroadcastMessage2() throws MQClientException, InterruptedException {
        this.broadcastConsumer.consume();
        Thread.sleep(5_000);
    }
}