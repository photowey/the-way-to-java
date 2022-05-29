/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.rabbitmq.in.action.factory;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

/**
 * {@code RabbitListenerContainerFactory}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
public class RabbitListenerContainerFactory {

    public static SimpleRabbitListenerContainerFactory connectionFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        // hard code
        container.setMaxConcurrentConsumers(4);
        container.setConcurrentConsumers(2);
        container.setPrefetchCount(2);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageConverter(jackson2JsonMessageConverter());

        return container;
    }

    public static Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}