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
package com.photowey.juc.in.action.sync.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * {@code ReentrantLockerTest}
 *
 * @author photowey
 * @date 2021/11/18
 * @since 1.0.0
 */
class ReentrantLockerTest {

    @Test
    void testSync() throws InterruptedException {
        ReentrantLocker reentrantLocker = new ReentrantLocker();
        reentrantLocker.doSync();
    }

    /**
     * 重入
     *
     * @throws InterruptedException
     */
    @Test
    void testReentry() throws InterruptedException {
        ReentrantLocker reentrantLocker = new ReentrantLocker();
        reentrantLocker.reentry();

        TimeUnit.SECONDS.sleep(3);
        // execution the reentry1
        // execution the reentry2
    }

    /**
     * {@link Lock#lockInterruptibly()}
     *
     * @throws InterruptedException
     */
    @Test
    void testInterruptable() throws InterruptedException {
        ReentrantLocker reentrantLocker = new ReentrantLocker();
        reentrantLocker.interruptable();
        TimeUnit.SECONDS.sleep(3);

        // --- t2 获取到锁 ---
        // --- main 2s 后打断 t1 ---
        // --- t1 被打断了,没有获取到锁 ---
    }

    /**
     * {@link Lock#tryLock()}
     *
     * @throws InterruptedException
     */
    @Test
    void testTryLock() throws InterruptedException {
        ReentrantLocker reentrantLocker = new ReentrantLocker();
        reentrantLocker.tryLock();
        TimeUnit.SECONDS.sleep(3);

        // --- main 获取到锁 ---
        // --- t1 获取不到锁,直接返回 ---
    }

    /**
     * {@link Lock#tryLock(long, java.util.concurrent.TimeUnit)}
     *
     * @throws InterruptedException
     */
    @Test
    void testTryLockTimeout() throws InterruptedException {
        ReentrantLocker reentrantLocker = new ReentrantLocker();
        // reentrantLocker.tryLockTimeout(2, TimeUnit.SECONDS);
        // TimeUnit.SECONDS.sleep(3);

        // --- main 获取到锁 ---
        // --- t1 获取不到锁,直接返回 ---

        reentrantLocker.tryLockTimeout(4, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(3);

        // --- main 获取到锁 ---
        // --- t1 获取到锁 ---
    }

    /**
     * {@link java.util.concurrent.locks.Lock} 的 多条件
     *
     * @throws InterruptedException
     */
    @Test
    void testLockCondition() throws InterruptedException {
        ReentrantLocker reentrantLocker = new ReentrantLocker();

        reentrantLocker.lockCondition();
        TimeUnit.SECONDS.sleep(3);

        // --- Jack --- the conditionA:false, do wait()
        // --- Tom --- the conditionB:false, do wait()
        // --- Boss --- the conditionA:true
        // --- Boss --- the conditionB:true
        // --- Jack --- the conditionA:true, after wait()
        // --- Tom --- the conditionB:true, after wait()

    }

    /**
     * 交替打印 ABC 4遍
     *
     * @throws InterruptedException
     */
    @Test
    void testAlternate() throws InterruptedException {
        ReentrantLocker reentrantLocker = new ReentrantLocker();
        reentrantLocker.alternate(4);
        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * 释放锁流程
     *
     * @throws InterruptedException
     */
    @Test
    void testReleaseLockProcess() throws InterruptedException {
        ReentrantLocker reentrantLocker = new ReentrantLocker();

        reentrantLocker.releaseLockProcess();
        TimeUnit.SECONDS.sleep(10);
    }
}