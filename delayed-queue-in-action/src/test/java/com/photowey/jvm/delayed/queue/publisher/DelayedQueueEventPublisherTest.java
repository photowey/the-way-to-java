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
package com.photowey.jvm.delayed.queue.publisher;

import com.photowey.jvm.delayed.queue.event.EventData;
import com.photowey.jvm.delayed.queue.event.TestEvent1;
import com.photowey.jvm.delayed.queue.event.TestEvent2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * {@code DelayedQueueEventPublisherTest}
 *
 * @author photowey
 * @date 2022/08/07
 * @since 1.0.0
 */
@Slf4j
@SpringBootTest
class DelayedQueueEventPublisherTest {

    @Autowired
    private DelayedQueueEventPublisher delayedQueueEventPublisher;

    @Test
    void testPublishEvent() {

        TestEvent1 event1 = new TestEvent1();
        event1.setEventId("9527");
        event1.setEventType("101");
        event1.setData("say hello");
        EventData eventData = new EventData(9527L, "sharkchili", 18);
        TestEvent2 event2 = new TestEvent2();
        event2.setEventId("8848");
        event2.setEventType("102");
        event2.setData(eventData);

        LocalDateTime now = LocalDateTime.now();
        event1.setRunAt(now.plusSeconds(3).toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        event2.setRunAt(now.plusSeconds(5).toInstant(ZoneOffset.ofHours(8)).toEpochMilli());

        this.delayedQueueEventPublisher.publishEvent(event1);
        log.info("publisher published event1");
        this.delayedQueueEventPublisher.publishEvent(event2);
        log.info("publisher published event2");

        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (Exception ignored) {
        }
    }
}