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
package com.photowey.event.bus.in.action.event.bus;

import com.photowey.event.bus.in.action.event.bus.hello.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@code GuavaEventBusTest}
 *
 * @author photowey
 * @date 2021/11/14
 * @since 1.0.0
 */
@SpringBootTest
class GuavaEventBusTest {

    @Test
    void testOneSubscriber() {
        AtomicInteger counter = new AtomicInteger(0);
        EventBus eventBus = new GuavaEventBus("test");

        TestSubscriber testSubscriber = new TestSubscriber(counter);
        eventBus.register(testSubscriber);

        eventBus.post(new TestEvent(1));

        // Test result log
        // process the TestEvent in TestSubscriber Subscriber

        eventBus.unregister(testSubscriber);
        eventBus.post(new TestEvent(2));

        // Test result log
        // NULL

        new Synchronizer().doAwait(3, TimeUnit.SECONDS);
    }

    @Test
    void testTwoSubscriber() {
        AtomicInteger counter = new AtomicInteger(0);
        EventBus eventBus = new GuavaEventBus("test");

        TestSubscriber testSubscriber = new TestSubscriber(counter);
        TestAnotherSubscriber testAnotherSubscriber = new TestAnotherSubscriber(counter);
        eventBus.register(testSubscriber);
        eventBus.register(testAnotherSubscriber);

        eventBus.post(new TestEvent(1));

        // Test result log
        // process the TestEvent in TestSubscriber Subscriber
        // process the TestEvent in TestAnotherSubscriber Subscriber

        eventBus.unregister(testSubscriber);
        eventBus.post(new TestEvent(1));

        // Test result log
        // process the TestEvent in TestAnotherSubscriber Subscriber

        new Synchronizer().doAwait(3, TimeUnit.SECONDS);
    }

    @Test
    void testThreeSubscriber() {
        AtomicInteger counter = new AtomicInteger(0);
        EventBus eventBus = new GuavaEventBus("test");

        TestSubscriber testSubscriber = new TestSubscriber(counter);
        TestAnotherSubscriber testAnotherSubscriber = new TestAnotherSubscriber(counter);
        HelloSubscriber helloSubscriber = new HelloSubscriber();
        eventBus.register(testSubscriber);
        eventBus.register(testAnotherSubscriber);
        eventBus.register(helloSubscriber);

        eventBus.post(new TestEvent(1));

        // Test result log
        // process the TestEvent in TestSubscriber Subscriber
        // process the TestEvent in TestAnotherSubscriber Subscriber

        eventBus.unregister(testSubscriber);
        eventBus.post(new TestEvent(1));

        // Test result log
        // process the TestEvent in TestAnotherSubscriber Subscriber

        eventBus.post(new HelloEvent("Hello World!"));

        // Test result log
        // process the HelloEvent in HelloSubscriber Subscriber,the world is:Hello World!

        new Synchronizer().doAwait(3, TimeUnit.SECONDS);
    }

    @Slf4j
    public static class Synchronizer {

        private final ReentrantLock lock = new ReentrantLock();
        private final Condition stop = lock.newCondition();

        public Synchronizer() {
        }

        public void doAwait() {
            this.doAwait(-1, null);
        }

        public void doAwait(long time) {
            this.doAwait(time, TimeUnit.MILLISECONDS);
        }

        public void doAwait(long time, TimeUnit timeUnit) {
            lock.lock();
            try {
                if (time > 0) {
                    stop.await(time, timeUnit);
                } else {
                    stop.await();
                }
            } catch (InterruptedException e) {
                log.error("interrupted error ", e);
            } finally {
                lock.unlock();
            }
        }
    }


}