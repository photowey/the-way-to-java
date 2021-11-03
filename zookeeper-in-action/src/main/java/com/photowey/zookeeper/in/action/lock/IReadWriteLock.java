package com.photowey.zookeeper.in.action.lock;

/**
 * {@code IReadWriteLock}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public interface IReadWriteLock {

    String ROOT = "/RWLock";

    IWriteLock writeLock();

    IReadLock readLock();
}
