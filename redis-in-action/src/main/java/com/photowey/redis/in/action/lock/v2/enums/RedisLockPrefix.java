package com.photowey.redis.in.action.lock.v2.enums;

/**
 * {@code RedisLockPrefix}
 *
 * @author photowey
 * @date 2022/10/04
 * @since 1.0.0
 */
public enum RedisLockPrefix {

    ORDER("photowey:redis:loker:order", "order"),

    PAYMENT("photowey:redis:loker:payment", "payment");

    private String code;
    private String desc;

    RedisLockPrefix(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public String getUniqueKey(String key) {
        return String.format("%s:%s", this.code(), key);
    }
}
}
