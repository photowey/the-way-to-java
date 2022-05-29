package com.photowey.rabbitmq.in.action.service;

/**
 * {@code IRabbitMqService}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
public interface IRabbitMqService {

    /**
     * 发送消息到指定队列
     *
     * @param queue   队列名称
     * @param message 队列消息
     */
    void toQueue(String queue, Object message);

    /**
     * 发送消息到指定队列
     *
     * @param queue     队列名称
     * @param message   队列消息
     * @param exchanger 交换机名称
     */
    void toQueue(String exchanger, String queue, Object message);
}