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
package com.photowey.rabbitmq.in.action.service;

import com.photowey.rabbitmq.in.action.domain.RabbitMQPayload;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * {@code AbstractRabbitMQAdapter}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
public abstract class AbstractRabbitMQAdapter implements IRabbitMQDelayedService {

    protected final RabbitTemplate rabbitTemplate;
    protected static final String DELAYED_HEADER = "x-delay";

    public AbstractRabbitMQAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void toQueue(String routingKey, Object message) {

    }

    @Override
    public void toQueue(String exchange, String routingKey, Object message) {

    }

    @Override
    public <T> void toDelayedQueue(RabbitMQPayload<T> rabbitMqPayload) {

    }
}
