package com.photowey.rocketmq.in.action.message.cluster;

import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code ClusterConsumerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class ClusterConsumerTest {

    @Autowired
    private ClusterConsumer clusterConsumer;

    @Test
    void testConsumeClusterMessage() throws MQClientException, InterruptedException {
        this.clusterConsumer.consume();
        Thread.sleep(5_000);
    }

    @Test
    void testConsumeClusterMessage2() throws MQClientException, InterruptedException {
        this.clusterConsumer.consume();
        Thread.sleep(5_000);
    }
}