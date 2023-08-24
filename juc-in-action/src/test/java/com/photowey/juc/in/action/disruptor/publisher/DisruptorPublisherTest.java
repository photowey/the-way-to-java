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
package com.photowey.juc.in.action.disruptor.publisher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * {@code DisruptorPublisherTest}
 *
 * @author photowey
 * @date 2021/12/04
 * @since 1.0.0
 */
@SpringBootTest
class DisruptorPublisherTest {

    @Autowired
    private DisruptorPublisher disruptorPublisher;

    @Test
    void testDisruptorPublisher() {
        for (int i = 0; i < 1_000; i++) {
            this.disruptorPublisher.publish("Disruptor publish content:index:" + (i + 1));
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
        }
    }

}