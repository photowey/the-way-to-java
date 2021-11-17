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
package com.photowey.juc.in.action.sync.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@code ReentrantLocker}
 *
 * @author photowey
 * @date 2021/11/18
 * @since 1.0.0
 */
@Slf4j
public class ReentrantLocker {

    /**
     * -- --------------------------------
     * 所谓的公平锁和非公平锁
     * 他们首先会在加锁的时候去抢锁
     * - 如果加锁失败-->进入队列(还未随眠)
     * - 这个时候-"不死心" 还会进行自旋-再次去获取锁
     * - 如果失败-则随眠
     * <p>
     * -- 一朝排队,永远排队
     * <p>
     * -- -------------------------------- 公平锁
     * 第一次加锁的时候-他不会去尝试加索,他会去看一下我前面有没有人排队,
     * 如果有人排队,我则进入队列(并不等于排队),然后不死心,再次看一下我有没有获取锁的资格,
     * 如果有,继续拿锁
     * 如果没有,则随眠(排队)
     * <p>
     * -- --------------------------------
     * Node t = tail
     * Node h = head
     * 都等于 空,表示队列还没有初始化
     * --
     * 公平锁:
     * -- 第一次加锁,连队列都不需要进入(初始化),直接加锁,性能比较好,
     * 1.如果是-交替轮流执行,则永远不会初始化队列
     * --> 交替执行--> t1 和 tn 一摸一样
     * 2.如果资源竞争:
     * t2: 第二个会初始化队列,如果他前面是 head 还会自旋一次
     * t3: 直接 park
     * -- --------------------------------
     */

    private static Lock lock = new ReentrantLock();

    public void doSync() throws InterruptedException {
        int threadSize = 10;
        Thread[] threads = new Thread[threadSize];
        for (int i = 0; i < threadSize; i++) {
            threads[i] = new Thread(() -> {
                lock.lock();
                // 1 -> 10 正序打印
                log.info("the current thread:{} acquire the sync lock", Thread.currentThread().getName());
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                }
                lock.unlock();

            }, "t" + (i + 1));
        }

        // ========================================= main
        lock.lock();
        for (int i = 0; i < threadSize; i++) {
            threads[i].start();
            try {
                Thread.sleep(200);
            } catch (Exception e) {
            }
        }
        lock.unlock();
        // ========================================= main

        for (int i = 0; i < threadSize; i++) {
            threads[i].join();
        }

        Thread.sleep(5_000);
    }
}
