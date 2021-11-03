package com.photowey.redis.in.action.queue.event.pubsub;

import com.photowey.redis.in.action.queue.event.IRedisEvent;
import org.springframework.context.ApplicationEvent;

/**
 * {@code PubsubEvent}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public class PubsubEvent extends ApplicationEvent implements IRedisEvent {

    public PubsubEvent(String source) {
        super(source);
    }
}
