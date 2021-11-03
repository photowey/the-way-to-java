package com.photowey.redis.in.action.lock;

import java.util.concurrent.locks.Lock;

/**
 * {@code IRedisLock}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public interface IRedisLock extends Lock {

    void lock(String lockName);

    boolean tryLock(String lockName);
}
