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
package com.photowey.micro.integrated.message.rabbitmq.handler.sender;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * {@code DefaultRabbitMessageSender}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
public class DefaultRabbitMessageSender extends AbstractRabbitSender implements RabbitMessageSender {

    @Override
    public void send(String topic, Object message) {
        throw new UnsupportedOperationException("Unsupported now");
    }

    @Override
    public <T> void send(String exchange, String routingKey, T payload) {
        String messageId = this.populateNormalMessageId(payload);
        CorrelationData correlationData = new CorrelationData(messageId);
        this.rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                payload,
                (message) -> {
                    MessageProperties props = message.getMessageProperties();
                    props.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    props.setCorrelationId(messageId);

                    return message;
                }, correlationData);
    }
}
