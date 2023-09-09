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
package com.photowey.micro.integrated.message.rabbitmq.autoconfigure.collback;

import com.photowey.hicoomore.integrated.message.rabbitmq.core.message.MessageRabbitmq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

/**
 * {@code RabbitmqCallbackHandler}
 *
 * @author photowey
 * @date 2022/08/19
 * @since 1.0.0
 */
@Slf4j
public class RabbitmqCallbackHandler implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitProperties rabbitProperties;

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        if (null != returned.getMessage()) {
            log.error("message.rabbit: received.broker.message.return.callback,exchange:[{}], routingKey:[{}]",
                    returned.getExchange(), returned.getRoutingKey());
            // this.populateReturnedMessageRabbitmq(message, replyCode, replyText, exchange, routingKey);
            // TODO save
        }
    }

    /**
     * 处理消息回退
     *
     * @param message    消息
     * @param replyCode  返回码
     * @param replyText  返回描述
     * @param exchange   交换机
     * @param routingKey 路由键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        this.returnedMessage(new ReturnedMessage(message, replyCode, replyText, exchange, routingKey));
    }

    @Override
    public void confirm(CorrelationData data, boolean ack, String cause) {
        // ack: 消息投递到 broker 的状态
        this.handleConfirmLog(data, ack, cause);
        if (null != data) {
            // this.populateConfirmMessageRabbitmq(data, ack, cause);
            // TODO save
        }
    }

    private void handleConfirmLog(CorrelationData data, boolean ack, String cause) {
        if (!ack) {
            if (null != data) {
                String dataId = data.getId();
                log.error("message.rabbit: message.ack.callback.failed, data.id:[{}], cause is:{}", dataId, cause);
            } else {
                log.error("message.rabbit: message.ack.callback.failed cause is:{}", cause);
            }
        } else {
            if (null != data) {
                String dataId = data.getId();
                log.error("message.rabbit: message.ack.callback.successfully, data.id:[{}]", dataId);
            } else {
                log.error("message.rabbit: message.ack.callback.successfully");
            }
        }
    }

    private MessageRabbitmq populateConfirmMessageRabbitmq(CorrelationData data, boolean ack, String cause) {
        return MessageRabbitmq.builder().build();
    }

    private MessageRabbitmq populateReturnedMessageRabbitmq(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        return MessageRabbitmq.builder().build();
    }
}
