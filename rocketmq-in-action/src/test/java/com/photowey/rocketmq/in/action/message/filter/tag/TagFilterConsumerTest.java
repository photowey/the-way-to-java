package com.photowey.rocketmq.in.action.message.filter.tag;

import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code TagFilterConsumerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class TagFilterConsumerTest {

    @Autowired
    private TagFilterConsumer tagFilterConsumer;

    @Test
    void teatTagFilterMessage() throws MQClientException, InterruptedException {
        this.tagFilterConsumer.consume();
        Thread.sleep(5_000);
    }
}