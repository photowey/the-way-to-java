/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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