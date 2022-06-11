/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.juc.in.action.sync.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * {@code CustomLock}
 *
 * @author photowey
 * @date 2021/11/20
 * @since 1.0.0
 */
public class CustomLock implements Lock {

    private CustomSynchronizer synchronizer = new CustomSynchronizer();

    @Override
    public void lock() {
        this.synchronizer.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("Not support!");
    }

    @Override
    public boolean tryLock() {
        return this.synchronizer.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException("Not support!");
    }

    @Override
    public void unlock() {
        this.synchronizer.release(1);
    }

    @Override
    public Condition newCondition() {
        return this.synchronizer.newCondition();
    }

}
