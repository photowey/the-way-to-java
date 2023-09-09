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
package com.photowey.micro.integrated.message.rabbitmq.autoconfigure.config;

import com.photowey.hicoomore.integrated.message.rabbitmq.core.constant.RabbitConstants;
import com.photowey.micro.integrated.message.rabbitmq.autoconfigure.collback.RabbitmqCallbackHandler;
import com.photowey.micro.integrated.message.rabbitmq.autoconfigure.factory.ContainerFactory;
import com.photowey.micro.integrated.message.rabbitmq.handler.sender.DefaultRabbitDelayedMessageSender;
import com.photowey.micro.integrated.message.rabbitmq.handler.sender.DefaultRabbitMessageSender;
import com.photowey.micro.integrated.message.rabbitmq.handler.sender.RabbitDelayedMessageSender;
import com.photowey.micro.integrated.message.rabbitmq.handler.sender.RabbitMessageSender;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * {@code IntegratedRabbitMessageAutoConfigure}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
@AutoConfiguration
@ConditionalOnProperty(name = "spring.rabbitmq.enabled", matchIfMissing = true)
public class IntegratedRabbitMessageAutoConfigure {

    @Bean
    public RabbitmqCallbackHandler rabbitmqCallbackHandler() {
        return new RabbitmqCallbackHandler();
    }

    /**
     * {@link ContainerFactory}
     *
     * @param connectionFactory {@link ConnectionFactory}
     * @return {@link SimpleRabbitListenerContainerFactory}
     */
    @Bean(RabbitConstants.LISTENER_CONTAINER_FACTORY_NAME)
    public SimpleRabbitListenerContainerFactory containerFactory(ConnectionFactory connectionFactory) {
        return ContainerFactory.containerFactory(connectionFactory);
    }

    /**
     * 接受字符串通知
     *
     * @param connectionFactory {@link ConnectionFactory}
     * @return {@link SimpleRabbitListenerContainerFactory}
     */
    @Bean(RabbitConstants.STRING_LISTENER_CONTAINER_FACTORY_NAME)
    public SimpleRabbitListenerContainerFactory stringContainerFactory(ConnectionFactory connectionFactory) {
        return ContainerFactory.stringContainerFactory(connectionFactory);
    }

    /**
     * {@link RabbitTemplate}
     *
     * @param connectionFactory {@link ConnectionFactory}
     * @return {@link RabbitTemplate}
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @ConditionalOnMissingBean(RabbitTemplate.class)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, RabbitProperties rabbitProperties) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(ContainerFactory.jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(rabbitProperties.getTemplate().getMandatory());
        rabbitTemplate.setConfirmCallback(this.rabbitmqCallbackHandler());
        rabbitTemplate.setReturnsCallback(this.rabbitmqCallbackHandler());

        return rabbitTemplate;
    }

    /**
     * 配置 {@link RabbitMessageSender}
     *
     * @return {@link RabbitMessageSender}
     */
    @Bean
    @ConditionalOnMissingBean(RabbitMessageSender.class)
    public RabbitMessageSender rabbitMessageSender() {
        return new DefaultRabbitMessageSender();
    }

    /**
     * 配置 {@link RabbitMessageSender}
     *
     * @return {@link RabbitMessageSender}
     */
    @Bean
    @ConditionalOnMissingBean(RabbitDelayedMessageSender.class)
    public RabbitDelayedMessageSender rabbitDelayedMessageSender() {
        return new DefaultRabbitDelayedMessageSender();
    }
}
