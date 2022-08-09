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

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
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
     * - 如果加锁失败-->进入队列(还未睡眠)
     * - 这个时候-"不死心" 还会进行自旋-再次去获取锁
     * - 如果失败-则随眠
     * <p>
     * -- 一朝排队,永远排队
     * <p>
     * -- -------------------------------- 公平锁
     * 第一次加锁的时候-他不会去尝试加索,他会去看一下我前面有没有人排队,
     * 如果有人排队,我则进入队列(并不等于排队),然后不死心,再次看一下我有没有获取锁的资格,
     * 如果有,继续拿锁
     * 如果没有,则睡眠(排队)
     * <p>
     * -- --------------------------------
     * Node t = tail
     * Node h = head
     * 都等于空,表示队列还没有初始化
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

    /**
     * 1.可打断.可重入
     * 2.可以设置超时时间
     * 3.可以设置公平锁
     * 4.支持多个条件变量
     * 5.支持读写锁
     * <p>
     * -- -------------------------------- 可打断
     * // t1 线程
     * lock.lockInterruptibly();
     * 表示: 可被打断
     * t1.interrupt();
     * -- 打断之后是由一个异常去响应，也可以再异常中再次尝试获取锁.
     * 唤醒-是一定获取锁--不一样。
     * -- --------------------------------
     * -- -------------------------------- 可超时
     * tryLock() 超时获取锁,可以待定指定参数;
     * -- 如果不带参数: 表示立即获取锁,获取不到直接返回;
     * -- -- {@code java.util.concurrent.locks.Lock#tryLock()}
     * -- 如果带参数:表示等待指定时间内,获取不到锁直接返回
     * -- -- {@code java.util.concurrent.locks.Lock#tryLock(long, java.util.concurrent.TimeUnit)}
     */
    private static Lock lock = new ReentrantLock();

    /**
     * 必须是 同一把锁 Condition()
     */
    private static Condition lockConditionA = lock.newCondition();
    private static Condition lockConditionB = lock.newCondition();
    private static Condition lockConditionC = lock.newCondition();

    public boolean conditionA = false;
    public boolean conditionB = false;

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

    public void reentry() {
        lock.lock();
        try {
            log.info("execution the reentry1");
            // 重入
            this.reentry2();
        } finally {
            lock.unlock();
        }
    }

    public void reentry2() {
        lock.lock();
        try {
            log.info("execution the reentry2");
        } finally {
            lock.unlock();
        }
    }

    public void interruptable() throws InterruptedException {
        new Thread(() -> {
            try {
                lock.lock();
                log.info("--- t2 获取到锁 ---");
                TimeUnit.SECONDS.sleep(5);
                log.info("--- t2 5s 之后,继续执行 ---");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2").start();

        // 让 t2 先拿到锁
        TimeUnit.SECONDS.sleep(1);

        Thread t1 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                log.info("--- t1 获取到锁 ---");
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.info("--- t1 被打断了,没有获取到锁 ---");
                return;
            } finally {
                lock.unlock();
            }
        }, "t1");

        t1.start();

        // 由于 t1 可以被打断,故 2s后打断t1 不在等待t2 释放锁
        try {
            TimeUnit.SECONDS.sleep(2);
            log.info("--- main 2s 后打断 t1 ---");
            t1.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void tryLock() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            if (!lock.tryLock()) {
                log.info("--- t1 获取不到锁,直接返回 ---");
                return;
            }
            try {
                log.info("--- t1 获取到锁 ---");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.info("--- main 获取到锁 ---");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } finally {
            lock.unlock();
        }
    }

    public void tryLockTimeout(long timeout, TimeUnit timeUnit) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                if (!lock.tryLock(timeout, timeUnit)) {
                    log.info("--- t1 获取不到锁,直接返回 ---");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                log.info("--- t1 获取到锁 ---");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.info("--- main 获取到锁 ---");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } finally {
            lock.unlock();
        }
    }

    public void lockCondition() {
        new Thread(() -> {
            try {
                lock.lock();
                while (!conditionA) {
                    try {
                        log.info("--- Jack --- the conditionA:false, do wait()");
                        lockConditionA.await();
                        log.info("--- Jack --- the conditionA:true, after wait()");
                    } catch (InterruptedException e) {
                    }
                }
            } finally {
                lock.unlock();
            }
        }, "Jack").start();

        new Thread(() -> {
            try {
                lock.lock();
                while (!conditionB) {
                    try {
                        log.info("--- Tom --- the conditionB:false, do wait()");
                        lockConditionB.await();
                        log.info("--- Tom --- the conditionB:true, after wait()");
                    } catch (InterruptedException e) {
                    }
                }
            } finally {
                lock.unlock();
            }

        }, "Tom").start();

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
        }

        new Thread(() -> {
            lock.lock();
            try {
                conditionA = true;
                log.info("--- Boss --- the conditionA:true");
                lockConditionA.signalAll();
                conditionB = true;
                log.info("--- Boss --- the conditionB:true");
                lockConditionB.signalAll();
            } finally {
                lock.unlock();
            }
        }, "Boss").start();
    }

    public void releaseLockProcess() {
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                log.info("---------- t1 acquire lock ----------");
            } finally {
                lock.unlock();
            }
        }, "t1");

        // 主线程 - 先拿到锁
        lock.lock();
        // t1 启动 - 进入队列
        t1.start();
        t1.interrupt();
        try {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
            }
        } finally {
            // 释放锁 - 唤醒 t1 {@link java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt}
            // {@link LockSupport.park(this)}
            lock.unlock();
        }
    }

    int monitor = 1;

    /**
     * 三个线程交替打印ABC {@code times} 次
     *
     * @param times 打印次数
     */
    public void alternate(int times) {
        new Thread(() -> this.print("A", 1, 2, times), "A").start();
        new Thread(() -> this.print("B", 2, 3, times), "B").start();
        new Thread(() -> this.print("C", 3, 1, times), "C").start();
    }

    private void print(String content, int wait, int next, int times) {
        for (int i = 0; i < times; i++) {
            try {
                lock.lock();
                while (monitor != wait) {
                    if (wait == 1) {
                        lockConditionA.await();
                    } else if (wait == 2) {
                        lockConditionB.await();
                    } else {
                        lockConditionC.await();
                    }
                }

                log.info(content);
                monitor = next;

                if (next == 1) {
                    lockConditionA.signal();
                } else if (next == 2) {
                    lockConditionB.signal();
                } else {
                    lockConditionC.signal();
                }
            } catch (InterruptedException e) {
                // Ignore
            } finally {
                lock.unlock();
            }
        }
    }
}
