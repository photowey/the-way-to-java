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