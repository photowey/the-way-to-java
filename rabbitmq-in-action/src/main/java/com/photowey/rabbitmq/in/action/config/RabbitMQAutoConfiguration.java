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
package com.photowey.rabbitmq.in.action.config;

import com.photowey.rabbitmq.in.action.factory.RabbitListenerContainerFactory;
import com.photowey.rabbitmq.in.action.service.IRabbitMQService;
import com.photowey.rabbitmq.in.action.service.impl.RabbitMQServiceImpl;
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
 * {@code RabbitMQAutoConfiguration}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class RabbitMQAutoConfiguration {

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
        template.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            log.info("the message callback,the CorrelationData is:{},confirm is:{},message is:{}", correlationData, ack, cause);
            String messageId = null != correlationData ? correlationData.getId() : "";
            if (ack) {
                log.info("sent the message success, id:[{}]", messageId);
            } else {
                if (null != correlationData && null != correlationData.getReturned() && null != correlationData.getReturned().getMessage()) {
                    log.error("sent the message failure, id:[{}],message:[{}], cause: [{}]", messageId, correlationData.getReturned().getMessage().toString(), cause);
                } else {
                    log.error("sent the message failure, id:[{}] cause: [{}]", messageId, cause);
                }
            }
        });
        template.setReturnsCallback((returned) -> {
            String message = returned.getMessage().toString();
            int replyCode = returned.getReplyCode();
            String replyText = returned.getReplyText();
            String exchange = returned.getExchange();
            String routingKey = returned.getRoutingKey();
            log.error("message returned, message:[{}],replyCode:[{}],replyCode:[{}],exchange:[{}],routingKey:[{}]", message, replyCode, replyText, exchange, routingKey);
        });

        return template;
    }

    /**
     * IRabbitMQService
     *
     * @return {@link IRabbitMQService}
     */
    @Bean
    @ConditionalOnMissingBean(IRabbitMQService.class)
    public IRabbitMQService rabbitMqService(ConnectionFactory connectionFactory) {
        return new RabbitMQServiceImpl(this.rabbitTemplate(connectionFactory));
    }
}