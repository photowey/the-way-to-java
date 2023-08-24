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
package com.photowey.juc.in.action.stamped;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * {@code StampedLocker}
 *
 * @author photowey
 * @date 2021/11/29
 * @since 1.0.0
 */
@Slf4j
public class StampedLocker {

    private int i;
    private long stampw;

    public StampedLocker(int i) {
        this.i = i;
    }

    /**
     * 特点:
     * 1.不支持重入
     * 2.不支持条件
     * 3.存在一定的并发问题
     */
    private final StampedLock lock = new StampedLock();

    @SneakyThrows
    public int read() {
        // Optimistic read
        long stamp = this.lock.tryOptimisticRead();
        log.info("--- acquire the stamp:{} ---", stamp);
        TimeUnit.SECONDS.sleep(2);
        if (this.lock.validate(stamp)) {
            // 存在线程安全问题?
            log.info("--- validate the stamp:{} ---", stamp);
            return i;
        }
        log.info("--- validate the stamp:{}, modified:{} by other write-thread ---", stamp, stampw);
        try {
            stamp = this.lock.readLock();
            log.info("--- acquire read-lock:{} ---", stamp);
            TimeUnit.SECONDS.sleep(2);
            return i;
        } finally {
            this.lock.unlockRead(stamp);
            log.info("--- release read-lock:{} ---", stamp);
        }
    }

    @SneakyThrows
    public void write(int i) {
        // CAS
        long stamp = this.lock.writeLock();
        log.info("--- acquire the write-lock stamp:{} ---", stamp);
        try {
            TimeUnit.SECONDS.sleep(5);
            this.i = i;
        } finally {
            this.lock.unlockWrite(stamp);
            log.info("--- release write-lock:{} ---", stamp);
        }

    }

}
