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
package com.photowey.juc.in.action.sync.aqs;

/**
 * {@code Aqs}
 *
 * @author photowey
 * @date 2021/11/20
 * @since 1.0.0
 */
public class Aqs {

    /**
     * 描述:
     * 1.全称 {@link java.util.concurrent.locks.AbstractQueuedSynchronizer}
     * 2.阻塞式锁和相关的同步器工具的框架
     * 3.AQS用一个变量(volatile state) 属性来表示锁的状态,子类去维护这个状态
     * 4.compareAndSetState() cas 改变这个变量(state)
     * 5.独占模式: 是只有一个线程能够访问资源
     * 6.共享模式: 允许多个线程访问资源(读写锁)
     * 7.内部维护了一个FIFO等待队列,类似于 {@code synchronized} 关键字 中的 ObjectMonitor 的 {@code EntryList}
     * 8.通过条件变量来实现等待、唤醒机制.支持多个条件变量.类似于 ObjectMonitor 的 {@code WaitSet}
     * 8.内部维护了一个 {@code exclusiveOwnerThread} 线程,来记录当前持有锁的那个线程
     */

    /**
     * 功能:
     * 1.实现阻塞获取锁 `acquire()` - 拿不到锁就阻塞,等待或被释放,再次获取锁
     * 2.实现非阻塞尝试获取锁 `tryAcquire()` -- 拿不到锁就直接放弃
     * 3.实现获取锁超时机制
     * 4.实现通过打断来取消
     * 5.实现独占锁和共享锁
     * 6.实现条件不满足的时候等待
     */
}
