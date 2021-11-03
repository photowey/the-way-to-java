package com.photowey.rocketmq.in.action.message.filter.sql;

import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code SQLFilterConsumerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class SQLFilterConsumerTest {

    @Autowired
    private SQLFilterConsumer sqlFilterConsumer;

    @Test
    void teatTagFilterMessage() throws MQClientException, InterruptedException {
        this.sqlFilterConsumer.consume();
        Thread.sleep(5_000);

        // [rocketmq-topic-sql-filter-tagB],filterField:[4]
        // [rocketmq-topic-sql-filter-tagB],filterField:[1]
        // [rocketmq-topic-sql-filter-tagA],filterField:[0]
        // [rocketmq-topic-sql-filter-tagA],filterField:[3]
    }

}