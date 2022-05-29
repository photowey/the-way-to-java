package com.photowey.rabbitmq.in.action.service;

import com.photowey.rabbitmq.in.action.domain.RabbitMqPayload;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * {@code AbstractRabbitMqAdapter}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
public abstract class AbstractRabbitMqAdapter implements IRabbitMqDelayedService {

    protected final RabbitTemplate rabbitTemplate;

    protected static final String RABBITMQ_TEMPLATE_NAME = "rabbitTemplate";
    protected static final long DELAYED_TIME = 30000;
    protected static final String DELAYED_HEADER = "x-delay";

    public AbstractRabbitMqAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void toQueue(String queue, Object message) {

    }

    @Override
    public void toQueue(String exchanger, String queue, Object message) {

    }

    @Override
    public <T> void toDelayedQueue(RabbitMqPayload<T> rabbitMqPayload) {

    }
}
