package com.photowey.rocketmq.in.action.message.transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code TransactionProducerTest}
 *
 * @author photowey
 * @date 2021/11/01
 * @since 1.0.0
 */
@SpringBootTest
class TransactionProducerTest {

    @Autowired
    private TransactionProducer transactionProducer;

    @Test
    void testTransactionMessage() throws MQClientException, InterruptedException {
        this.transactionProducer.produce();
        Thread.sleep(5_000);
    }
}