package com.photowey.rabbitmq.in.action.processor;

import com.photowey.rabbitmq.in.action.domain.MessageBody;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.function.Function;

/**
 * {@code MessageProcessor}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
public interface MessageProcessor {

    <T extends MessageBody> void doProcess(
            T messageBody,
            String queue,
            long deliveryTag, Channel channel,
            Function<T, Boolean> function) throws IOException;

}
