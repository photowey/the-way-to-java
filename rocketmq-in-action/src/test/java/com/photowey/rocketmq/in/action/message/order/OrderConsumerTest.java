package com.photowey.rocketmq.in.action.message.order;

import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code OrderConsumerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class OrderConsumerTest {

    @Autowired
    private OrderConsumer orderConsumer;

    @Test
    void testConsumePartOrderMessage() throws MQClientException, InterruptedException {
        this.orderConsumer.consume();
        Thread.sleep(2_000);
    }
}