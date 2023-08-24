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
package com.photowey.redis.in.action.queue.impl.pubsub;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code IPubSubRedisQueueTest}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@SpringBootTest
class IPubSubRedisQueueTest {

    @Autowired
    private IPubSubRedisQueueSubscriber subscriber;
    @Autowired
    private IPubSubRedisQueuePublisher publisher;

    private static final String CHANNEL_NAME = "redis:pub:sub:mq:key:channel";

    @Test
    void testPublishMessage() {
        for (int i = 0; i < 10; i++) {
            this.publisher.publish(CHANNEL_NAME, "我是PubSubQueue message. id:" + (i + 1));
        }
    }

    @Test
    @SneakyThrows
    void testSubscribeMessage() {
        this.subscriber.subscribe(CHANNEL_NAME);
    }

}