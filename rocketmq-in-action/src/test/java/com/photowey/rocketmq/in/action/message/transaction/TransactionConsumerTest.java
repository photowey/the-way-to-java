package com.photowey.rocketmq.in.action.message.transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code TransactionConsumerTest}
 *
 * @author photowey
 * @date 2021/11/01
 * @since 1.0.0
 */
@SpringBootTest
class TransactionConsumerTest {

    @Autowired
    private TransactionConsumer transactionConsumer;

    @Test
    void testTransactionMessage() throws MQClientException, InterruptedException {
        this.transactionConsumer.consume();
        Thread.sleep(1000_000);
    }
}