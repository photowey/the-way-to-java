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
package com.photowey.juc.in.action.stamped;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * {@code StampedLockerTest}
 *
 * @author photowey
 * @date 2021/11/29
 * @since 1.0.0
 */
@Slf4j
class StampedLockerTest {

    @Test
    void testStampedLockRR() throws InterruptedException {
        StampedLocker stampedLocker = new StampedLocker(1010);

        Thread t1 = new Thread(() -> {
            int shared = stampedLocker.read();
            log.info("the thread:{} read the shared parameter:I:{}", Thread.currentThread().getName(), shared);
        }, "t1");
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        Thread t2 = new Thread(() -> {
            int shared = stampedLocker.read();
            log.info("the thread:{} read the shared parameter:I:{}", Thread.currentThread().getName(), shared);
        }, "t2");
        t2.start();

        TimeUnit.SECONDS.sleep(15);
    }

    @Test
    void testStampedLockRW() throws InterruptedException {
        StampedLocker stampedLocker = new StampedLocker(1010);

        Thread t1 = new Thread(() -> {
            int shared = stampedLocker.read();
            log.info("the thread:{} read the shared parameter:I:{}", Thread.currentThread().getName(), shared);
        }, "t1");
        t1.start();

        TimeUnit.SECONDS.sleep(1);

        Thread t2 = new Thread(() -> {
            stampedLocker.write(0xCafe);
            log.info("the thread:{} write the shared parameter:I:{}", Thread.currentThread().getName(), 0xCafe);
        }, "t2");
        t2.start();

        TimeUnit.SECONDS.sleep(15);
    }

}