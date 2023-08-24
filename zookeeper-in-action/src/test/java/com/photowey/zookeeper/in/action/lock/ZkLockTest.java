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
package com.photowey.zookeeper.in.action.lock;

import org.I0Itec.zkclient.ZkClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

/**
 * {@code ZkLockTest}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@SpringBootTest
class ZkLockTest {

    @Autowired
    private ZkClient zkClient;
    private OrderNumFactory orderNumFactory;

    public static final int THREAD_COUNT = 50;

    @BeforeEach
    public void init() {
        this.orderNumFactory = new OrderNumFactory();
    }

    @Test
    void testZkMutexLock() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            ZkMutexLock lock = new ZkMutexLock(zkClient);
            new Thread(new OrderThread(lock, latch, orderNumFactory)).start();
            latch.countDown();
        }

        Thread.sleep(5_000);
    }

    @Test
    void testZkSequenceLock() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            ZkSequenceLock lock = new ZkSequenceLock(zkClient);
            new Thread(new OrderThread(lock, latch, orderNumFactory)).start();
            latch.countDown();
        }

        Thread.sleep(5_000);
    }

}