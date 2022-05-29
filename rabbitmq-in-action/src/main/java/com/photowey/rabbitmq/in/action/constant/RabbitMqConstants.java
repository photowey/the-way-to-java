package com.photowey.rabbitmq.in.action.constant;

/**
 * {@code RabbitMqConstants}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
public interface RabbitMqConstants {

    /**
     * 默认交换机名称
     */
    String DEFAULT_EXCHANGE = "rabbitmq_default_exchange";
    /**
     * 延时队列交换机
     */
    String DELAYED_EXCHANGE = "rabbitmq_default_delayed_exchange";

    /**
     * 默认队列
     */
    String DEFAULT_QUEUE = "rabbitmq_default_queue";

    /**
     * 默认延迟队列
     */
    String DEFAULT_DELAYED_QUEUE = "rabbitmq_default_delayed_queue";

}