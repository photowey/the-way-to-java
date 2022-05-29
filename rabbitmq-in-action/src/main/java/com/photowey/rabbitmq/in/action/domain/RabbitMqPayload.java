package com.photowey.rabbitmq.in.action.domain;

import com.photowey.rabbitmq.in.action.constant.RabbitMqConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * {@code RabbitMqPayload}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMqPayload<T> implements Serializable {

    private static final long serialVersionUID = 3890285964805460810L;

    /**
     * 默认的交换机
     */
    private String exchange = RabbitMqConstants.DEFAULT_EXCHANGE;
    /**
     * 默认的队列
     */
    private String queueName = RabbitMqConstants.DEFAULT_QUEUE;
    /**
     * 消息体
     */
    private T data;
    /**
     * 延时时间,默认300000
     */
    private long delayedTime = 300000;
    /**
     * 时间单位
     */
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
}