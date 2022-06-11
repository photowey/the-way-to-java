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
package com.photowey.rabbitmq.in.action.service.impl;

import com.photowey.rabbitmq.in.action.constant.RabbitMqConstants;
import com.photowey.rabbitmq.in.action.domain.RabbitMqPayload;
import com.photowey.rabbitmq.in.action.service.AbstractRabbitMqAdapter;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * {@code RabbitMqServiceImpl}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
public class RabbitMqServiceImpl extends AbstractRabbitMqAdapter {

    public RabbitMqServiceImpl(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    /**
     * 发送消息到指定队列
     *
     * @param queue   队列名称
     * @param message 队列消息
     */
    @Override
    public void toQueue(String queue, Object message) {
        // 标记消息
        String messageId = this.uuid();
        CorrelationData correlationId = new CorrelationData(messageId);
        this.rabbitTemplate.convertAndSend(RabbitMqConstants.DEFAULT_EXCHANGE, queue, message, correlationId);
    }

    /**
     * 发送消息到指定队列
     *
     * @param queue     队列名称
     * @param message   队列消息
     * @param exchanger 交换机名称
     */
    @Override
    public void toQueue(String exchanger, String queue, Object message) {
        String messageId = this.uuid();
        CorrelationData correlationId = new CorrelationData(messageId);
        this.rabbitTemplate.convertAndSend(exchanger, queue, message, correlationId);
    }

    /**
     * 发送延时消息到指定队列
     *
     * @param rabbitMqPayload 消息数据传输对象
     */
    @Override
    public <T> void toDelayedQueue(RabbitMqPayload<T> rabbitMqPayload) {
        TimeUnit timeUnit = rabbitMqPayload.getTimeUnit();
        this.rabbitTemplate.convertAndSend(rabbitMqPayload.getExchange(), rabbitMqPayload.getQueueName(), rabbitMqPayload.getData(), (message) -> {
            message.getMessageProperties().setHeader(DELAYED_HEADER, timeUnit.toMillis(rabbitMqPayload.getDelayedTime()));
            return message;
        });
    }

    private String uuid() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
        return uuid;
    }
}