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

import java.util.concurrent.Callable;

/**
 * {@code Locker}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/15
 */
public interface Locker {

    BeanFactory beanFactory();

    default DistributedLock locker() {
        return this.beanFactory().getBean(DistributedLock.class);
    }

    default void run(String lockKey, Runnable task) {
        this.locker().run(lockKey, task);
    }

    default <T> T call(String lockKey, Callable<T> task) {
        return this.locker().call(lockKey, task);
    }
}
