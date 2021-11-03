package com.photowey.zookeeper.in.action.lock;

import org.I0Itec.zkclient.ZkClient;

/**
 * {@code ReadWriteLock}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public class ReadWriteLock implements IReadWriteLock {

    private final ZkClient zkClient;
    private final IWriteLock writeLock;
    private final IReadLock readLock;

    public ReadWriteLock(ZkClient zkClient) {
        this.zkClient = zkClient;
        this.writeLock = new WriteLock(this.zkClient, ROOT);
        this.readLock = new ReadLock(this.zkClient, ROOT);
    }

    public ReadWriteLock(ZkClient zkClient, String root) {
        this.zkClient = zkClient;
        this.writeLock = new WriteLock(this.zkClient, root);
        this.readLock = new ReadLock(this.zkClient, root);
    }

    @Override
    public IWriteLock writeLock() {
        return this.writeLock;
    }

    @Override
    public IReadLock readLock() {
        return this.readLock;
    }
}
