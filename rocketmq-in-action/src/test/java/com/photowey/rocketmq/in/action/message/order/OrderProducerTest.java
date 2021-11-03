package com.photowey.rocketmq.in.action.message.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code OrderProducerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class OrderProducerTest {

    @Autowired
    private OrderProducer orderProducer;

    @Test
    void testPartOrderMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        this.orderProducer.produce();
    }

}