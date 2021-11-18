/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.juc.in.action.sync;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code Synchronizer}
 *
 * @author photowey
 * @date 2021/11/18
 * @since 1.0.0
 */
@Slf4j
public class Synchronizer {

    private static Object lock = new Object();

    public void doSync() throws InterruptedException {
        int threadSize = 10;
        Thread[] threads = new Thread[threadSize];
        for (int i = 0; i < threadSize; i++) {
            threads[i] = new Thread(() -> {
                synchronized (lock) {
                    // 10 -> 1 倒序打印
                    log.info("the current thread:{} acquire the sync lock", Thread.currentThread().getName());
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                    }
                }
            }, "t" + (i + 1));
        }

        // ========================================= main
        // synchronized 是一把 非公平锁
        synchronized (lock) {
            for (int i = 0; i < threadSize; i++) {
                threads[i].start();
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                }
            }
        }
        // ========================================= main

        for (int i = 0; i < threadSize; i++) {
            threads[i].join();
        }

        Thread.sleep(1_000);
    }
}
