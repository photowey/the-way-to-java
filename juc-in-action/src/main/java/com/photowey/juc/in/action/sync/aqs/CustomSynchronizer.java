package com.photowey.juc.in.action.sync.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

/**
 * {@code CustomSynchronizer}
 *
 * @author photowey
 * @date 2021/11/20
 * @since 1.0.0
 */
public class CustomSynchronizer extends AbstractQueuedSynchronizer {

    // TODO 暂时-不支持重入

    /**
     * {@link .../doc/reentrantlock.jpg}
     */

    @Override
    protected boolean tryAcquire(int arg) {
        if (0 == this.getState()) {
            boolean succeed = this.compareAndSetState(0, 1);
            if (succeed) {
                // 加锁成功
                this.setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
        }

        return false;
    }

    @Override
    protected boolean tryRelease(int arg) {
        if (0 == this.getState()) {
            throw new IllegalMonitorStateException("Illegal state!");
        }

        this.setState(0);
        this.setExclusiveOwnerThread(null);

        return true;
    }

    @Override
    protected boolean isHeldExclusively() {
        return this.getState() != 0;
    }

    public Condition newCondition() {
        return new ConditionObject();
    }
}
