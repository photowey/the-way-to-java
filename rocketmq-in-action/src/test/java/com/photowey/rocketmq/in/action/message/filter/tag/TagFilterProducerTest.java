package com.photowey.rocketmq.in.action.message.filter.tag;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code TagFilterProducerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class TagFilterProducerTest {

    @Autowired
    private TagFilterProducer tagFilterProducer;

    @Test
    void testTagFilterMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        this.tagFilterProducer.produce();
        Thread.sleep(2_000);
    }
}