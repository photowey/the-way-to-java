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
package com.photowey.juc.in.action.sync.aqs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * {@code CustomLockTest}
 *
 * @author photowey
 * @date 2021/11/20
 * @since 1.0.0
 */
@Slf4j
class CustomLockTest {

    CustomLock lock = new CustomLock();

    @Test
    void testCustomLock() {

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                log.info("--- >>> thread:t1 acquire the lock <<< ---");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.info("--- >>> thread:main acquire the lock <<< ---");
        t1.start();
        log.info("--- >>> thread:main start t1 <<< ---");
        try {
            log.info("--- >>> thread:main do something <<< ---");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
            }
        } finally {
            log.info("--- >>> thread:main release the lock <<< ---");
            lock.unlock();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
        }

        // --- >>> thread:main acquire the lock <<< ---
        // --- >>> thread:main start t1 <<< ---
        // --- >>> thread:main do something <<< ---
        // --- >>> thread:main release the lock <<< ---
        // --- >>> thread:t1 acquire the lock <<< ---
    }

}