package com.photowey.rocketmq.in.action.message.normal.sync;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code SyncProducerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class SyncProducerTest {

    @Autowired
    private SyncProducer syncProducer;

    @Test
    void testSyncMessage() throws RemotingException, MQClientException, InterruptedException, MQBrokerException {
        this.syncProducer.produce();
        Thread.sleep(2_000);
    }

}