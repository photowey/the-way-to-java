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

import com.photowey.redisson.in.action.sync.Lock;
import com.photowey.redisson.in.action.sync.redis.property.RedisLockProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * {@code RedisLock}
 *
 * @author photowey
 * @date 2023/01/30
 * @since 1.0.0
 */
@Slf4j
public class RedisLock implements Lock {

    private static final String DOT = ".";

    // @Getter ?

    private final RedisLockProperties properties;
    private final RedissonClient redisson;
    private final String lockNamespace;

    public RedisLock(RedissonClient redisson, RedisLockProperties properties) {
        this.properties = properties;
        this.redisson = redisson;
        this.lockNamespace = properties.getNamespace();
        Assert.notNull(this.lockNamespace, "the lock namespace can't be null");
    }

    @Override
    public void acquireLock(String lockId) {
        RLock lock = this.redisson.getLock(this.parseLockId(lockId));
        lock.lock();
    }

    @Override
    public boolean acquireLock(String lockId, long waitTime, TimeUnit unit) {
        RLock lock = this.redisson.getLock(this.parseLockId(lockId));
        try {
            return lock.tryLock(waitTime, unit);
        } catch (Exception e) {
            return this.handleAcquireLockFailure(lockId, e);
        }
    }

    @Override
    public boolean acquireLock(String lockId, long waitTime, long leaseTime, TimeUnit unit) {
        RLock lock = this.redisson.getLock(this.parseLockId(lockId));
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (Exception e) {
            return this.handleAcquireLockFailure(lockId, e);
        }
    }

    @Override
    public void releaseLock(String lockId) {
        RLock lock = this.redisson.getLock(this.parseLockId(lockId));
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException ignored) {
            log.error("lockId:{} release lock failure, maybe duplicate.", lockId);
        }
    }

    @Override
    public void deleteLock(String lockId) {
        // do nothing
    }

    private String parseLockId(String lockId) {
        if (StringUtils.isEmpty(lockId)) {
            throw new IllegalArgumentException("lockId cannot be NULL or empty: lockId=" + lockId);
        }
        return this.lockNamespace + DOT + lockId;
    }

    private boolean handleAcquireLockFailure(String lockId, Exception e) {
        if (log.isInfoEnabled()) {
            log.info("lockId:{} acquire lock failure", lockId);
        }
        return this.properties.isIgnoreLockingExceptions();
    }
}