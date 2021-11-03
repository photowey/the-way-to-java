package com.photowey.rocketmq.in.action.message.normal.async;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code AsyncProducerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class AsyncProducerTest {

    @Autowired
    private AsyncProducer asyncProducer;

    @Test
    void testAsyncMessage() throws RemotingException, MQClientException, InterruptedException {
        this.asyncProducer.produce();
        Thread.sleep(2_000);
    }

}