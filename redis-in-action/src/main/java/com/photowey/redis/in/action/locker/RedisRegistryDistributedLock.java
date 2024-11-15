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

import org.springframework.beans.factory.BeanFactory;
import org.springframework.integration.support.locks.LockRegistry;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

/**
 * {@code RedisRegistryDistributedLock}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/15
 */
public interface RedisRegistryDistributedLock extends DistributedLock {

    BeanFactory beanFactory();

    default LockRegistry locker() {
        return this.beanFactory().getBean(LockRegistry.class);
    }

    @Override
    default Lock obtain(String lockKey) {
        return this.locker().obtain(lockKey);
    }

    @Override
    default void run(String lockKey, Runnable task) {
        Lock lock = this.locker().obtain(lockKey);

        lock.lock();
        try {
            task.run();
        } catch (Throwable e) {
            this.throwUnchecked(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    default <T> T call(String lockKey, Callable<T> task) {
        Lock lock = this.locker().obtain(lockKey);

        lock.lock();
        try {
            return task.call();
        } catch (Throwable e) {
            return this.throwUnchecked(e);
        } finally {
            lock.unlock();
        }
    }
}
