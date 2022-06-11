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
package com.photowey.juc.in.action.pool.queue;

import com.photowey.juc.in.action.pool.NamedNode;
import com.photowey.juc.in.action.pool.NamedTask;
import com.photowey.juc.in.action.pool.exception.NamedException;
import com.photowey.juc.in.action.pool.strategy.RejectedStrategyHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@code NamedBlockingQueue}
 * The task queue
 *
 * @author photowey
 * @date 2021/11/29
 * @since 1.0.0
 */
@Slf4j
public class NamedBlockingQueue {

    private final Deque<NamedTask> taskQueue;
    private int capacity;

    @Getter
    @Setter
    private RejectedStrategyHandler strategyHandler;

    private final Lock lock = new ReentrantLock();
    private final Condition busyWaitSet = lock.newCondition();

    public NamedBlockingQueue(int capacity) {
        this(capacity, new ArrayDeque<>());
    }

    public NamedBlockingQueue(int capacity, RejectedStrategyHandler strategyHandler) {
        this(capacity, new ArrayDeque<>(), strategyHandler);
    }

    public NamedBlockingQueue(int capacity, Deque<NamedTask> taskQueue) {
        this(capacity, taskQueue, null);
    }

    public NamedBlockingQueue(int capacity, Deque<NamedTask> taskQueue, RejectedStrategyHandler strategyHandler) {
        this.capacity = capacity;
        this.taskQueue = taskQueue;
        this.strategyHandler = strategyHandler;
    }

    public void put(NamedTask target) {
        // 1.判断队列是否达到上限
        lock.lock();
        try {
            while (this.taskQueue.size() == capacity) {
                // 队列已经达到上限 -> 阻塞
                this.busyWaitSet.await();
            }
            // 2.队列还可以存放,则直接入队
            this.taskQueue.addLast(target);
            log.info("put a new task:[{}] into task-queue", target.getName());
            this.busyWaitSet.signal();
        } catch (Exception e) {
            this.handleException(e);
            return;
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(NamedTask target, long timeout) {
        this.tryPut(target, timeout, TimeUnit.MILLISECONDS);
    }

    public void tryPut(NamedTask target, long timeout, TimeUnit timeUnit) {
        // 判断队列是否达到上限
        lock.lock();
        try {
            long awaitNanos = timeUnit.toNanos(timeout);
            while (this.taskQueue.size() == capacity) {
                if (awaitNanos <= 0) {
                    // 如果-等待超时 - 直接返回
                    log.info("tryPut a new task:[{}] time-out and give-up", target.getName());
                    return;
                }
                // 队列已经达到上限 -> 阻塞
                awaitNanos = this.busyWaitSet.awaitNanos(awaitNanos);
            }
            // 队列还可以存放,则直接入队
            this.taskQueue.addLast(target);
            log.info("put a new task:[{}] into task-queue", target.getName());
            this.busyWaitSet.signal();
        } catch (Exception e) {
            this.handleException(e);
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(NamedTask target) {
        this.tryPut(target, this.strategyHandler);
    }

    public void tryPut(NamedTask target, RejectedStrategyHandler strategyHandler) {
        // 判断队列是否达到上限
        lock.lock();
        try {
            while (this.taskQueue.size() == capacity) {
                if (strategyHandler != null) {
                    // 执行拒绝策略
                    strategyHandler.doRejected(this, target);
                    // 执行拒绝策略 - 之后 - 直接返回
                    log.info("------------------------- after strategyHandler#doRejected(target:{}) -------------------------", target.getName());
                    return;
                } else {
                    // 没有执行-拒绝策略 - 直接阻塞
                    this.busyWaitSet.await();
                }
            }
            // 队列还可以存放,则直接入队
            this.taskQueue.addLast(target);
            log.info("put a new task:[{}] into task-queue", target.getName());
            this.busyWaitSet.signal();
        } catch (Throwable e) {
            // await 异常
            // doRejected 异常
            // TODO throw?
            this.handleException(e);
        } finally {
            lock.unlock();
        }
    }

    public NamedTask poll() {
        lock.lock();
        try {
            while (this.taskQueue.isEmpty()) {
                this.busyWaitSet.await();
            }
            NamedTask target = this.taskQueue.removeFirst();
            log.info("poll a task:[{}] from task-queue", target.getName());
            this.busyWaitSet.signal();

            return target;
        } catch (Exception e) {
            this.handleException(e);
        } finally {
            lock.unlock();
        }

        return null;
    }

    public NamedTask tryPoll(long timeout) {
        return this.tryPoll(timeout, TimeUnit.MILLISECONDS);
    }

    public NamedTask tryPoll(long timeout, TimeUnit timeUnit) {
        lock.lock();
        try {
            long awaitNanos = timeUnit.toNanos(timeout);
            while (this.taskQueue.isEmpty()) {
                if (awaitNanos < 0) {
                    log.info("tryPoll a task time-out:[{}-{}] and give-up", timeout, timeUnit);
                    return null;
                }
                awaitNanos = this.busyWaitSet.awaitNanos(awaitNanos);
            }
            NamedTask target = this.taskQueue.removeFirst();
            log.info("poll a task:[{}] from task-queue", target.getName());
            this.busyWaitSet.signal();

            return target;
        } catch (Exception e) {
            this.handleException(e);
        } finally {
            lock.unlock();
        }

        return null;
    }

    public void remove(NamedNode task) {
        lock.lock();
        try {
            this.taskQueue.remove(task);
        } catch (Exception e) {
            this.handleException(e);
        } finally {
            lock.unlock();
        }
    }

    private void handleException(Throwable e) {
        if (e instanceof NamedException) {
            throw (NamedException) e;
        } else if (e instanceof RuntimeException) {
            throw new RuntimeException(e);
        } else {
            throw new RuntimeException(e);
        }
    }
}
