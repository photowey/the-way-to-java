package com.photowey.rocketmq.in.action.message.delay;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code DelayProducerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class DelayProducerTest {

    @Autowired
    private DelayProducer delayProducer;

    @Test
    void testDelayMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        this.delayProducer.produce();
        Thread.sleep(2_000);
    }
}