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
package com.photowey.juc.in.action.sync;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * {@code WaitTest}
 *
 * @author photowey
 * @date 2021/11/18
 * @since 1.0.0
 */
@Slf4j
class WaitTest {

    /**
     * 持有锁的线程发现条件不满足,调用 wait,即可进入 WaitSet 变为 WAITING 状态
     * BLOCKED 和 WAITING 的线程都处于阻塞状态,不占用 CPU 时间片
     * BLOCKED 线程会在持有锁的线程释放锁时唤醒
     * WAITING 线程会在持有锁的线程调用 notify 或 notifyAll 时唤醒,但唤醒后并不意味者立刻获得锁仍需
     * 进入EntryList 重新竞争
     * <p>
     * wait 和 sleep 的区别
     * 相同:线程的状态相同；都是阻塞状态
     * 区别:
     * 1、wait 是 Object 的方法；任何对象都可以直接调用；sleep 是 Thread 的静态方法
     * 2、wait 必须配合 synchronized 关键字一起使用；如果一个对象没有获取到锁直接调用 wait 会异常；sleep 则不需 要
     * 3、wait 可以通过 notify 主动唤醒；sleep 只能通过打断主动叫醒
     * 4、wait 会释放锁、sleep 在阻塞的阶段是不会释放锁的
     */

    public static Object lock = new Object();
    public static boolean conditionA = false;
    public static boolean conditionB = false;

    @Test
    void testWait() {
        new Thread(() -> {
            synchronized (lock) {
                // while(!condition) {}
                if (conditionA) {
                    log.info("--- Jack --- the condition:true");
                } else {
                    try {
                        log.info("--- Jack --- the condition:false, do wait()");
                        // 调用 wait() synchronized 会升级为重量锁(010)
                        lock.wait();
                        String printable = ClassLayout.parseInstance(lock).toPrintable();
                        log.info(printable);
                        /**
                         * 21:13:24.629 [Jack] INFO com.photowey.juc.in.action.sync.WaitTest - java.lang.Object object internals:
                         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
                         *       0     4        (object header)                           02 b8 2b ed (00000010 10111000 00101011 11101101) (-315901950)
                         *       4     4        (object header)                           11 02 00 00 (00010001 00000010 00000000 00000000) (529)
                         *       8     4        (object header)                           00 02 00 20 (00000000 00000010 00000000 00100000) (536871424)
                         *      12     4        (loss due to the next object alignment)
                         * Instance size: 16 bytes
                         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
                         */
                    } catch (InterruptedException e) {
                    }
                }

                if (conditionA) {
                    log.info("--- Jack --- after sleep, the condition:true");
                } else {
                    log.info("--- Jack --- after sleep, the condition:false");
                }
            }

        }, "Jack").start();

        for (int i = 0; i < 10; i++) {
            final int index = (i + 1);
            new Thread(() -> {

                synchronized (lock) {
                    log.info("--- the Other --- do work:{}", index);
                }

            }, "Other").start();
        }

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
        }
        new Thread(() -> {
            synchronized (lock) {
                log.info("--- Boss --- the conditionA:true");
                conditionA = true;
                // 随机叫醒一个
                // lock.notify();
                // 叫醒所有
                lock.notifyAll();
            }

        }, "Boss").start();

        try {
            Thread.sleep(6_000);
        } catch (InterruptedException e) {
        }
    }

    @Test
    void testWaitWhile() {
        new Thread(() -> {
            synchronized (lock) {
                while (!conditionA) {
                    log.info("--- Jack --- the conditionA:true");
                    try {
                        log.info("--- Jack --- the conditionA:false, do wait()");
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }
                if (conditionA) {
                    log.info("--- Jack --- after sleep, the conditionA:true");
                } else {
                    log.info("--- Jack --- after sleep, the conditionA:false");
                }
            }

        }, "Jack").start();

        new Thread(() -> {
            synchronized (lock) {
                while (!conditionB) {
                    log.info("--- Tom --- the conditionB:true");
                    try {
                        log.info("--- Tom --- the conditionB:false, do wait()");
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }
                if (conditionA) {
                    log.info("--- Tom --- after sleep, the conditionB:true");
                } else {
                    log.info("--- Tom --- after sleep, the conditionB:false");
                }
            }

        }, "Tom").start();

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
        }
        new Thread(() -> {
            synchronized (lock) {
                log.info("--- Boss --- the conditionB:true");
                conditionB = true;
                lock.notifyAll();
            }

        }, "Boss").start();

        try {
            Thread.sleep(6_000);
        } catch (InterruptedException e) {
        }
    }

}
