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

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.BeanFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

/**
 * {@code RedissonDistributedLock}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/15
 */
public interface RedissonDistributedLock extends DistributedLock {

    BeanFactory beanFactory();

    default RedissonClient locker() {
        return this.beanFactory().getBean(RedissonClient.class);
    }

    @Override
    default Lock obtain(String lockKey) {
        return this.locker().getLock(lockKey);
    }

    @Override
    default void run(String key, Runnable task) {
        RLock lock = this.locker().getLock(key);

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
    default <T> T call(String key, Callable<T> task) {
        RLock lock = this.locker().getLock(key);

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
