package com.photowey.rocketmq.in.action.message.delay;

import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code DelayConsumerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class DelayConsumerTest {

    @Autowired
    private DelayConsumer delayConsumer;

    @Test
    void testDelayMessage() throws MQClientException, InterruptedException {
        this.delayConsumer.consume();
        Thread.sleep(10_000);
    }
}