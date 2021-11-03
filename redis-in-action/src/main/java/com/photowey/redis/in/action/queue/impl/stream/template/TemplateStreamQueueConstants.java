package com.photowey.redis.in.action.queue.impl.stream.template;

/**
 * {@code TemplateStreamQueueConstants}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public interface TemplateStreamQueueConstants {

    String TEMPLATE_STREAM_QUEUE_NAME = "redis:stream:mq:key:template:queue";
    String TEMPLATE_STREAM_CONSUMER_NAME = "redis:stream:mq:key:template:consumer";
    String TEMPLATE_STREAM_GROUP_NAME = "redis:stream:mq:key:template:group";
}
