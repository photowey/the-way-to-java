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
package com.photowey.micro.integrated.message.rabbitmq.autoconfigure.factory;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

/**
 * {@code ContainerFactory}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
public class ContainerFactory {

    public static final int NCPU;
    private static final int PREFETCH_COUNT = 2;

    static {
        NCPU = Runtime.getRuntime().availableProcessors();
    }

    /**
     * 当 {@code RabbitMQ} 要将队列中的一条消息投递给消费者时,会遍历该队列上的消费者列表,选一个合适的消费者,然后将消息投递出去。
     * 其中挑选消费者的一个依据就是看消费者对应的 {@code channel} 上未 {@code ack} 的消息数是否达到设置的 {@code prefetch_count} 个数,
     * 如果未 {@code ack} 的消息数达到了 {@code prefetch_count} 的个数,则不符合要求。
     * 当挑选到合适的消费者后,中断后续的遍历
     *
     * @param connectionFactory {@link ConnectionFactory}
     * @return {@link SimpleRabbitListenerContainerFactory}
     */
    public static SimpleRabbitListenerContainerFactory containerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setMaxConcurrentConsumers(NCPU << 1);
        containerFactory.setConcurrentConsumers(NCPU);
        containerFactory.setPrefetchCount(PREFETCH_COUNT);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        containerFactory.setMessageConverter(jackson2JsonMessageConverter());

        return containerFactory;
    }

    public static SimpleRabbitListenerContainerFactory stringContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setMaxConcurrentConsumers(NCPU << 1);
        containerFactory.setConcurrentConsumers(NCPU);
        containerFactory.setPrefetchCount(PREFETCH_COUNT);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        containerFactory.setMessageConverter(simpleMessageConverter());

        return containerFactory;
    }

    public static Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public static SimpleMessageConverter simpleMessageConverter() {
        return new SimpleMessageConverter();
    }
}