package com.photowey.redis.in.action.lock;

import lombok.Getter;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * {@code LockData}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Getter
public class LockData<T> implements Delayed {

    private long expect;
    private T data;

    public LockData(long expirationTime, T data) {
        super();
        this.expect = expirationTime + System.currentTimeMillis() - 100;
        this.data = data;
    }

    public long getDelay(TimeUnit unit) {
        long delta = unit.convert(this.expect - System.currentTimeMillis(), unit);
        return delta;
    }

    public int compareTo(Delayed delayed) {
        long delta = (this.getDelay(TimeUnit.MILLISECONDS) - delayed.getDelay(TimeUnit.MILLISECONDS));
        return delta == 0 ? 0 : delta < 0 ? -1 : 1;
    }

}
