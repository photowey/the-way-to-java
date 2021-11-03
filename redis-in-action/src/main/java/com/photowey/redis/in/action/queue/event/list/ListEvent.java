package com.photowey.redis.in.action.queue.event.list;

import com.photowey.redis.in.action.queue.event.IRedisEvent;
import org.springframework.context.ApplicationEvent;

/**
 * {@code ListEvent}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public class ListEvent extends ApplicationEvent implements IRedisEvent {

    public ListEvent(String source) {
        super(source);
    }

}
