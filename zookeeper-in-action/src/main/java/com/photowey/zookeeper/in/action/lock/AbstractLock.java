package com.photowey.zookeeper.in.action.lock;

import org.I0Itec.zkclient.ZkClient;

/**
 * {@code AbstractLock}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public abstract class AbstractLock implements ILock {

    public static final String LOCK_PATH_ROOT = "/lock";

    protected ZkClient zkClient;

    public AbstractLock(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public void lock() {
        if (!this.tryLock()) {
            this.waitLock();
            this.lock();
        }
    }

    @Override
    public void unlock() {
        this.zkClient.close();
    }

    public abstract boolean tryLock();

    public abstract void waitLock();
}
