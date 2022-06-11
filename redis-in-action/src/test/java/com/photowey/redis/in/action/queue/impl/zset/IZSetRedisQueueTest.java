/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.redis.in.action.queue.impl.zset;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code IZSetRedisQueueTest}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@SpringBootTest
class IZSetRedisQueueTest {

    @Autowired
    private IZSetRedisQueueConsumer consumer;
    @Autowired
    private IZSetRedisQueueProducer producer;

    private static final String QUEUE_NAME = "redis:zset:mq:queue:key";

    @Test
    void testProduceDelayedMessage() {
        for (int i = 0; i < 10; i++) {
            long delayedMillis = System.currentTimeMillis() + 10 * 1000;
            this.producer.produceDelayed(QUEUE_NAME, "我是ZSetQueue message. id:" + (i + 1), delayedMillis);
        }
    }

    @Test
    @SneakyThrows
    void testConsumeMessage() {
        this.consumer.consume(QUEUE_NAME);
        Thread.sleep(15000);
    }

}