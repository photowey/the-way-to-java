package com.photowey.rabbitmq.in.action.service;

import com.photowey.rabbitmq.in.action.domain.RabbitMqPayload;

/**
 * {@code IRabbitMqDelayedService}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
public interface IRabbitMqDelayedService extends IRabbitMqService {


    /**
     * 发送延时消息到指定队列
     *
     * @param rabbitMqPayload 消息数据传输对象
     */
    <T> void toDelayedQueue(RabbitMqPayload<T> rabbitMqPayload);
}
