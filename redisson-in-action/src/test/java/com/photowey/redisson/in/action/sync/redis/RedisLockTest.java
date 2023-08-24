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
package com.photowey.redisson.in.action.sync.redis;

import com.photowey.redisson.in.action.sync.redis.mode.RedisModeEnum;
import com.photowey.redisson.in.action.sync.redis.property.RedisLockProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * {@code RedisLockTest}
 *
 * @author photowey
 * @date 2023/02/01
 * @since 1.0.0
 */
@Slf4j
class RedisLockTest {

    @Setter
    @Getter
    private RedisLock redisLock;

    @Setter
    @Getter
    private Config config;

    @Setter
    @Getter
    private RedissonClient redisson;

    @BeforeEach
    void setUp() throws Exception {
        String testServerAddress = "redis://192.168.19.250:6379";

        RedisLockProperties properties = mock(RedisLockProperties.class);
        when(properties.getMode()).thenReturn(RedisModeEnum.SINGLE);
        when(properties.getAddress()).thenReturn(testServerAddress);
        when(properties.getMasterName()).thenReturn("master");
        when(properties.getPassword()).thenReturn("root");
        when(properties.getNamespace()).thenReturn("photowey.lock.redis");
        when(properties.isIgnoreLockingExceptions()).thenReturn(false);

        Config redissonConfig = new Config();
        redissonConfig.useSingleServer().setAddress(testServerAddress).setDatabase(0).setPassword(properties.getPassword()).setTimeout(10000);
        redisLock = new RedisLock(Redisson.create(redissonConfig), properties);

        this.config = new Config();
        this.config.useSingleServer().setAddress(testServerAddress).setDatabase(0).setPassword(properties.getPassword()).setTimeout(10000);
        redisson = Redisson.create(this.config);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testLocking() {
        redisson.getKeys().flushall();
        String lockId = "hello";
        assertTrue(redisLock.acquireLock(lockId, 1000, 1000, TimeUnit.MILLISECONDS));
    }

    @Test
    void testLockExpiration() throws InterruptedException {
        redisson.getKeys().flushall();
        String lockId = "hello";
        boolean isLocked = redisLock.acquireLock(lockId, 1000, 1000, TimeUnit.MILLISECONDS);
        assertTrue(isLocked);

        Thread.sleep(2000);

        RLock lock = redisson.getLock(lockId);
        assertFalse(lock.isLocked());
    }

    @Test
    void testLockReentry() throws InterruptedException {
        redisson.getKeys().flushall();
        String lockId = "hello";
        boolean isLocked = redisLock.acquireLock(lockId, 1000, 60000, TimeUnit.MILLISECONDS);
        assertTrue(isLocked);

        Thread.sleep(1000);

        // get the lock back
        isLocked = redisLock.acquireLock(lockId, 1000, 1000, TimeUnit.MILLISECONDS);
        assertTrue(isLocked);

        RLock lock = redisson.getLock(lockId);
        assertTrue(isLocked);
    }

    @Test
    void testReleaseLock() {
        redisson.getKeys().flushall();
        String lockId = "hello";

        boolean isLocked = redisLock.acquireLock(lockId, 1000, 10000, TimeUnit.MILLISECONDS);
        assertTrue(isLocked);

        redisLock.releaseLock(lockId);

        RLock lock = redisson.getLock(lockId);
        assertFalse(lock.isLocked());
    }

    @Test
    void testLockReleaseAndAcquire() throws InterruptedException {
        redisson.getKeys().flushall();
        String lockId = "hello";

        boolean isLocked = redisLock.acquireLock(lockId, 1000, 10000, TimeUnit.MILLISECONDS);
        assertTrue(isLocked);

        redisLock.releaseLock(lockId);

        Worker worker1 = new Worker(redisLock, lockId);

        worker1.start();
        worker1.join();

        assertTrue(worker1.isLocked);
    }

    @Test
    void testLockingDuplicateThreads() throws InterruptedException {
        redisson.getKeys().flushall();
        String lockId = "hello";

        Worker worker1 = new Worker(redisLock, lockId);
        Worker worker2 = new Worker(redisLock, lockId);

        worker1.start();
        worker2.start();

        worker1.join();
        worker2.join();

        // Ensure only one of them had got the lock.
        assertFalse(worker1.isLocked && worker2.isLocked);
        assertTrue(worker1.isLocked || worker2.isLocked);
    }

    @Test
    void testDuplicateLockAcquireFailure() throws InterruptedException {
        redisson.getKeys().flushall();
        String lockId = "hello";
        Worker worker1 = new Worker(redisLock, lockId, 100L, 60000L);

        worker1.start();
        worker1.join();

        boolean isLocked = redisLock.acquireLock(lockId, 500L, 1000L, TimeUnit.MILLISECONDS);

        // Ensure only one of them had got the lock.
        assertFalse(isLocked);
        assertTrue(worker1.isLocked);
    }

    @Test
    void testReacquireLostKey() {
        redisson.getKeys().flushall();
        String lockId = "hello";

        boolean isLocked = redisLock.acquireLock(lockId, 1000, 10000, TimeUnit.MILLISECONDS);
        assertTrue(isLocked);

        // Delete key from the cluster to reacquire
        // Simulating the case when cluster goes down and possibly loses some keys.
        redisson.getKeys().flushall();

        isLocked = redisLock.acquireLock(lockId, 100, 10000, TimeUnit.MILLISECONDS);
        assertTrue(isLocked);
    }

    @Test
    void testReleaseLockTwice() {
        redisson.getKeys().flushall();
        String lockId = "hello";

        boolean isLocked = redisLock.acquireLock(lockId, 1000, 10000, TimeUnit.MILLISECONDS);
        assertTrue(isLocked);

        redisLock.releaseLock(lockId);
        redisLock.releaseLock(lockId);
    }

    private static class Worker extends Thread {

        private final RedisLock lock;
        private final String lockID;
        boolean isLocked;
        private Long timeToTry = 50L;
        private Long leaseTime = 1000L;

        Worker(RedisLock lock, String lockID) {
            super("TestWorker-" + lockID);
            this.lock = lock;
            this.lockID = lockID;
        }

        Worker(RedisLock lock, String lockID, Long timeToTry, Long leaseTime) {
            super("TestWorker-" + lockID);
            this.lock = lock;
            this.lockID = lockID;
            this.timeToTry = timeToTry;
            this.leaseTime = leaseTime;
        }

        @Override
        public void run() {
            isLocked = lock.acquireLock(lockID, timeToTry, leaseTime, TimeUnit.MILLISECONDS);
        }
    }
}
