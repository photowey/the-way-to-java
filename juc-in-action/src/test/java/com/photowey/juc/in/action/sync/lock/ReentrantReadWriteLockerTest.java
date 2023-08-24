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
package com.photowey.juc.in.action.sync.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * {@code ReentrantReadWriteLockerTest}
 *
 * @author photowey
 * @date 2021/11/19
 * @since 1.0.0
 */
class ReentrantReadWriteLockerTest {

    @Test
    void testReadWrite() throws InterruptedException {
        ReentrantReadWriteLocker reentrantReadWriteLocker = new ReentrantReadWriteLocker();
        reentrantReadWriteLocker.doReadWrite();

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    void testReadRead() throws InterruptedException {
        ReentrantReadWriteLocker reentrantReadWriteLocker = new ReentrantReadWriteLocker();
        reentrantReadWriteLocker.doReadRead();

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    void testWriteWrite() throws InterruptedException {
        ReentrantReadWriteLocker reentrantReadWriteLocker = new ReentrantReadWriteLocker();
        reentrantReadWriteLocker.doWriteWrite();

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    void testReadWriteFullAction() throws InterruptedException {
        ReentrantReadWriteLocker reentrantReadWriteLocker = new ReentrantReadWriteLocker();
        reentrantReadWriteLocker.doReadWriteFullAction();

        TimeUnit.SECONDS.sleep(15);
        /**
         * 22:58:41.783 [t1] INFO ** - ---------- t1 acquire lock ----------
         * 22:58:46.795 [t1] INFO ** - ---------- t1 after 5s ----------
         * 22:58:46.796 [t1] INFO ** - ---------- t1 release lock ----------
         *
         * # t2 t3 - 读 - t1 释放锁之后--可以同时获取锁
         * 22:58:46.798 [t2] INFO ** - ---------- t2 acquire lock ----------
         * 22:58:46.800 [t3] INFO ** - ---------- t3 acquire lock ----------
         * 22:58:47.801 [t2] INFO ** - ---------- t2 release lock ----------
         * 22:58:47.801 [t3] INFO ** - ---------- t3 release lock ----------
         *
         * # t4 - 写 - 必须等 t3 释放锁了之后-才能获取到锁
         * 22:58:47.803 [t4] INFO ** - ---------- t4 acquire lock ----------
         * 22:58:57.812 [t4] INFO ** - ---------- t4 after sleep ----------
         * 22:58:57.813 [t4] INFO ** - ---------- t4 release lock ----------
         *
         * # t5 前面有写锁 - 必须等 t4 释放锁之后- 才能拿到锁,不能和 t2 t3 一起执行
         * 22:58:57.814 [t5] INFO ** - ---------- t5 acquire lock ----------
         * 22:58:57.815 [t5] INFO ** - ---------- t5 release lock ----------
         */
    }
}