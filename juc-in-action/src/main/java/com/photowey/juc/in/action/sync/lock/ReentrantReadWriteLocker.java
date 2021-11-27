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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * {@code ReentrantReadWriteLocker}
 *
 * @author photowey
 * @date 2021/11/19
 * @since 1.0.0
 */
@Slf4j
public class ReentrantReadWriteLocker {

    /**
     * 读读并发
     * 读写互斥
     * 写写互斥
     * {@link ReentrantReadWriteLock.ReadLock} 不支持 newCondition() -> {@code throw new UnsupportedOperationException()}
     */
    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock readLock = lock.readLock();
    private static Lock writeLock = lock.writeLock();

    public void doReadWrite() {
        new Thread(() -> {
            readLock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    this.action(i);
                }
            } finally {
                readLock.unlock();
            }

        }, "read").start();

        new Thread(() -> {
            writeLock.lock();
            try {
                for (int i = 0; i < 20; i++) {
                    this.action(i);
                }
            } finally {
                writeLock.unlock();
            }

        }, "write").start();
    }

    public void doReadRead() {
        new Thread(() -> {
            readLock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    this.action(i);
                }
            } finally {
                readLock.unlock();
            }

        }, "read-1").start();

        new Thread(() -> {
            readLock.lock();
            try {
                for (int i = 0; i < 20; i++) {
                    this.action(i);
                }
            } finally {
                readLock.unlock();
            }

        }, "read-2").start();
    }

    public void doWriteWrite() {
        new Thread(() -> {
            writeLock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    this.action(i);
                }
            } finally {
                writeLock.unlock();
            }

        }, "write-1").start();

        new Thread(() -> {
            writeLock.lock();
            try {
                for (int i = 0; i < 20; i++) {
                    this.action(i);
                }
            } finally {
                writeLock.unlock();
            }

        }, "write-2").start();
    }

    private void action(int index) {
        log.info("--- do action ---:{}", (index + 1));
        try {
            Thread.sleep(1_00);
        } catch (InterruptedException e) {
        }
    }

    // =========================================

    private static class RWDictionary {

        private final Map<String, Data> m = new TreeMap<>();
        private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        private final Lock r = rwl.readLock();
        private final Lock w = rwl.writeLock();

        public Data get(String key) {
            r.lock();
            try {
                return m.get(key);
            } finally {
                r.unlock();
            }
        }

        public List<String> allKeys() {
            r.lock();
            try {
                return new ArrayList<>(m.keySet());
            } finally {
                r.unlock();
            }
        }

        public Data put(String key, Data value) {
            w.lock();
            try {
                return m.put(key, value);
            } finally {
                w.unlock();
            }
        }

        public void clear() {
            w.lock();
            try {
                m.clear();
            } finally {
                w.unlock();
            }
        }
    }

    private static class CachedData {
        Object data;
        boolean cacheValid;
        final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

        void processCachedData() {
            rwl.readLock().lock();
            // 没过期
            if (!cacheValid) {
                // 释放读锁-不支持升级--所以需要释放
                rwl.readLock().unlock();
                // 上一把: 写锁
                rwl.writeLock().lock();
                try {
                    // 双重检查
                    if (!cacheValid) {
                        // data = ...
                        data = new Object();
                        cacheValid = true;
                    }
                    // 更新缓存之后--需要读取---->加一把读锁
                    rwl.readLock().lock();
                } finally {
                    rwl.writeLock().unlock();
                }
            }

            // 没过期-缓存可用
            try {
                use(data);
            } finally {
                rwl.readLock().unlock();
            }
        }

        private void use(Object data) {

        }
    }

    @lombok.Data
    private static class Data {
        Long id;
        String name;
    }
}



