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
package com.photowey.redisson.in.action.sync.local;

import com.photowey.redisson.in.action.sync.Lock;

import java.util.concurrent.TimeUnit;

/**
 * {@code LocalLock}
 *
 * @author photowey
 * @date 2023/01/30
 * @since 1.0.0
 */
public class LocalLock implements Lock {

    @Override
    public void acquireLock(String lockId) {
        // TODO
    }

    @Override
    public boolean acquireLock(String lockId, long waitTime, TimeUnit unit) {
        // TODO
        return true;
    }

    @Override
    public boolean acquireLock(String lockId, long waitTime, long leaseTime, TimeUnit unit) {
        // TODO
        return true;
    }

    @Override
    public void releaseLock(String lockId) {
        // TODO
    }

    @Override
    public void deleteLock(String lockId) {
        // TODO
    }

}
