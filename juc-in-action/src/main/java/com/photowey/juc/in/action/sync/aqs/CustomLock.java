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
