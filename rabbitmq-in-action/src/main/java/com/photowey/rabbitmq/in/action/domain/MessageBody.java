package com.photowey.rabbitmq.in.action.domain;

/**
 * {@code MessageBody}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
public interface MessageBody {

    /**
     * convert the message Body to JSON String
     *
     * @return JSON
     */
    String toJSONString();
}
