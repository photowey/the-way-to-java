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
package com.photowey.redis.in.action.locker;

import com.photowey.common.in.action.util.ObjectUtils;
import org.redisson.api.RedissonClient;
import org.springframework.integration.support.locks.LockRegistry;

/**
 * {@code RedissonLockRegistry}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/15
 */
public class RedissonLockRegistry implements LockRegistry {

    private final RedissonClient redisson;
    private final String registryKey;

    public RedissonLockRegistry(RedissonClient redisson, String registryKey) {
        this.redisson = redisson;
        this.registryKey = registryKey;
    }

    @Override
    public java.util.concurrent.locks.Lock obtain(Object lockKey) {
        String key = this.populateLockKey(lockKey);
        return this.redisson.getLock(key);
    }

    private String populateLockKey(Object lockKey) {
        if (ObjectUtils.isNullOrEmpty(this.registryKey)) {
            return lockKey.toString();
        }

        // @see org.springframework.integration.redis.util.RedisLockRegistry.RedisLock#constructLockKey
        return String.format("%s:%s", this.registryKey, lockKey.toString());
    }
}
