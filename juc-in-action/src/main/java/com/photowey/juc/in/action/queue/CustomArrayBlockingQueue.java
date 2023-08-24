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
package com.photowey.juc.in.action.queue;

/**
 * {@code CustomArrayBlockingQueue}
 *
 * @author photowey
 * @date 2022/08/14
 * @since 1.0.0
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomArrayBlockingQueue<E> {

    private final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;
    private final Object[] items;
    private int takeIndex;
    private int putIndex;
    private int count;

    public CustomArrayBlockingQueue(int size) {
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
        this.takeIndex = 0;
        this.putIndex = 0;
        this.count = 0;
        if (size <= 0) {
            throw new RuntimeException("size can't be < 1");
        }

        this.items = new Object[size];
    }

    public void put(E x) {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            enqueue(x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public boolean add(E e) {
        if (offer(e)) {
            return true;
        } else {
            throw new RuntimeException("Queue full");
        }
    }

    public boolean offer(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (count == items.length) {
                return false;
            } else {
                enqueue(e);
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (count == 0) ? null : dequeue();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    private void enqueue(E x) {
        this.items[putIndex] = x;
        if (++putIndex == items.length) {
            putIndex = 0;
        }
        count++;
        notEmpty.signal();
    }

    private E dequeue() {
        final Object[] items = this.items;
        E x = (E) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length) {
            takeIndex = 0;
        }
        count--;
        notFull.signal();
        return x;
    }

    @Override
    public String toString() {
        StringBuilder ctx = new StringBuilder();
        ctx.append("[");
        lock.lock();
        try {
            if (count == 0) {
                ctx.append("]");
            } else {
                int current = 0;
                while (current != count) {
                    ctx.append(items[(current + takeIndex) % items.length].toString()).append(", ");
                    current += 1;
                }
                ctx.delete(ctx.length() - 2, ctx.length());
                ctx.append(']');
            }
        } finally {
            lock.unlock();
        }

        return ctx.toString();
    }
}