package com.photowey.rabbitmq.in.action.enums;

/**
 * {@code RabbitmqAckEnum}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
public enum RabbitmqAckEnum {
    /**
     * 接收消息
     */
    ACCEPT,
    /**
     * 可重试-重新打回队列
     */
    RETRY,
    /**
     * 拒绝重试-直接丢弃
     */
    REJECT;
}
