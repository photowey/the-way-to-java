package com.photowey.mongo.in.action.event.time;

/**
 * {@code DefaultTimeServiceImpl}
 *
 * @author photowey
 * @date 2022/10/27
 * @since 1.0.0
 */
public class DefaultTimeServiceImpl implements TimeService {

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
