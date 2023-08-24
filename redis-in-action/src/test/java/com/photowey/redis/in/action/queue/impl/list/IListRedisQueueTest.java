/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.redis.in.action.queue.impl.list;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code IListRedisQueueTest}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@SpringBootTest
class IListRedisQueueTest {

    @Autowired
    private IListRedisQueueConsumer consumer;
    @Autowired
    private IListRedisQueueProducer producer;

    private static final String QUEUE_NAME = "redis:list:mq:key:queue";

    @Test
    void testProduceMessage() {
        for (int i = 0; i < 10; i++) {
            this.producer.produce(QUEUE_NAME, "我是ListQueue message. id:" + (i + 1));
        }
    }

    @Test
    @SneakyThrows
    void testConsumeMessage() {
        this.consumer.consume(QUEUE_NAME);
        Thread.sleep(10000);
    }

}