package com.photowey.redis.in.action.queue.event.zset;

import com.photowey.redis.in.action.queue.event.IRedisEvent;
import org.springframework.context.ApplicationEvent;

/**
 * {@code ZSetEvent}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public class ZSetEvent extends ApplicationEvent implements IRedisEvent {

    public ZSetEvent(String source) {
        super(source);
    }
}
