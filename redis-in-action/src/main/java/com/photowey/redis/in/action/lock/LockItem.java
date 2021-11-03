package com.photowey.redis.in.action.lock;

import lombok.Getter;

/**
 * {@code LockItem}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Getter
public class LockItem {

    private final String key;
    private final String value;
    // true: delay
    // false: clear
    private final boolean delayed;

    private final Thread owner;

    public LockItem(String key, String value, boolean delayed, Thread owner) {
        this.key = key;
        this.value = value;
        this.delayed = delayed;
        this.owner = owner;
    }
}
