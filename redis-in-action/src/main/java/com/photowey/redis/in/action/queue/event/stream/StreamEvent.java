package com.photowey.redis.in.action.queue.event.stream;

import com.photowey.redis.in.action.queue.event.IRedisEvent;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * {@code StreamEvent}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public class StreamEvent extends ApplicationEvent implements IRedisEvent {

    public StreamEvent(Map<String, String> source) {
        super(source);
    }
}
