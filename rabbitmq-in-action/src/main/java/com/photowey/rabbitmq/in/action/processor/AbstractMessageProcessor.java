/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.rabbitmq.in.action.processor;

import com.photowey.rabbitmq.in.action.domain.MessageBody;
import com.photowey.rabbitmq.in.action.enums.RabbitMQAckEnum;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.function.Function;

/**
 * {@code AbstractMessageProcessor}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractMessageProcessor implements MessageProcessor {

    @Override
    public <T extends MessageBody> void handleBodyMessage(
            T message,
            String queue,
            long deliveryTag, Channel channel,
            Function<T, Boolean> function) throws IOException {

        RabbitMQAckEnum ack = RabbitMQAckEnum.ACCEPT;
        try {
            if (!function.apply(message)) {
                ack = RabbitMQAckEnum.REJECT;
            }
        } catch (Exception e) {
            ack = RabbitMQAckEnum.RETRY;
            String body = message.toJSONString();
            if (StringUtils.hasText(body)) {
                log.error("handle the queue:[{}] message exception, message is: {}", queue, body);
            }
        } finally {
            switch (ack) {
                case ACCEPT:
                    channel.basicAck(deliveryTag, false);
                    break;
                case RETRY:
                    channel.basicNack(deliveryTag, false, false);
                    break;
                case REJECT:
                    // channel.basicNack(deliveryTag, false, false);
                    channel.basicReject(deliveryTag, false);
                    break;
                default:
                    break;
            }
        }
    }
}