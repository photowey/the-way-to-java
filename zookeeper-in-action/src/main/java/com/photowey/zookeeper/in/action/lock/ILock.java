package com.photowey.zookeeper.in.action.lock;

/**
 * {@code ILock}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
public interface ILock {

    void lock();

    void unlock();
}
