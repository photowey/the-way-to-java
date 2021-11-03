package com.photowey.rocketmq.in.action.message.batch;

import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code BatchConsumerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class BatchConsumerTest {

    @Autowired
    private BatchConsumer batchConsumer;

    @Test
    void testBatchMessage() throws MQClientException, InterruptedException {
        this.batchConsumer.consume();
        Thread.sleep(15_000);
    }
}