package com.photowey.rabbitmq.in.action.config;

import com.photowey.rabbitmq.in.action.factory.RabbitListenerContainerFactory;
import com.photowey.rabbitmq.in.action.service.IRabbitMqService;
import com.photowey.rabbitmq.in.action.service.impl.RabbitMqServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * {@code RabbitMqAutoConfiguration}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class RabbitMqAutoConfiguration {

    public static final String MESSAGE_CONTAINER_FACTORY_NAME = "rabbitmqMessageContainer";

    /**
     * RabbitListenerContainerFactory
     *
     * @param connectionFactory {@link ConnectionFactory}
     * @return SimpleRabbitListenerContainerFactory
     */
    @Bean(MESSAGE_CONTAINER_FACTORY_NAME)
    @ConditionalOnMissingBean(name = MESSAGE_CONTAINER_FACTORY_NAME)
    public SimpleRabbitListenerContainerFactory messageContainer(ConnectionFactory connectionFactory) {
        return RabbitListenerContainerFactory.connectionFactory(connectionFactory);
    }

    /**
     * RabbitTemplate
     *
     * @param connectionFactory ConnectionFactory
     * @return {@link RabbitTemplate}
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @ConditionalOnMissingBean(RabbitTemplate.class)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(RabbitListenerContainerFactory.jackson2JsonMessageConverter());
        template.setConfirmCallback((CorrelationData correlationData, boolean confirm, String messageStr) -> {
            log.info("the message callback,the CorrelationData is:{},confirm is:{},message is:{}", correlationData, confirm, messageStr);
            if (confirm) {
                log.info("----------------------sent the message success-------------------------");
            } else {
                log.info("----------------------sent the message failure-------------------------");
            }
        });

        return template;
    }

    /**
     * IRabbitMqService
     *
     * @return {@link IRabbitMqService}
     */
    @Bean
    @ConditionalOnMissingBean(IRabbitMqService.class)
    public IRabbitMqService rabbitMqService(ConnectionFactory connectionFactory) {
        return new RabbitMqServiceImpl(this.rabbitTemplate(connectionFactory));
    }
}