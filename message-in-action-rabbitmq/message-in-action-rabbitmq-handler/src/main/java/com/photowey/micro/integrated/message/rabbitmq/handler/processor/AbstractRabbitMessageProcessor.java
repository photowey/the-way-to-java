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
package com.photowey.micro.integrated.message.rabbitmq.handler.processor;

import com.rabbitmq.client.Channel;
import com.photowey.micro.integrated.message.core.domain.body.MessageBody;
import com.photowey.micro.integrated.message.core.enums.MessageAckEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.function.Function;

/**
 * {@code AbstractRabbitMessageProcessor}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractRabbitMessageProcessor implements RabbitMessageProcessor {

    private boolean requeue;

    public void setRequeue(boolean requeue) {
        this.requeue = requeue;
    }

    public boolean determineNeedRequeue() {
        return this.requeue;
    }

    @Override
    public <T extends MessageBody> void onObjectMessage(
            T body, String queue, long deliveryTag,
            Channel channel, Function<T, Boolean> callback) throws IOException {
        MessageAckEnum ack = MessageAckEnum.REJECT;
        try {
            if (callback.apply(body)) {
                ack = MessageAckEnum.ACCEPT;
            }
        } catch (Exception e) {
            ack = MessageAckEnum.RETRY;
            log.error("message.rabbit: on.broker.push.body.message, queue:[{}], body:[{}]", queue, body.toJSONString(), e);
        } finally {
            switch (ack) {
                case ACCEPT:
                    channel.basicAck(deliveryTag, false);
                    log.info("message.rabbit: ack.broker.push.body.message.succeed, queue:[{}], action:[{}]", queue, ack.name());
                    break;
                case RETRY:
                    channel.basicNack(deliveryTag, false, this.determineNeedRequeue());
                    break;
                case REJECT:
                    channel.basicReject(deliveryTag, this.determineNeedRequeue());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onTextMessage(
            String body, String queue,
            long deliveryTag, Channel channel, Function<String, Boolean> callback) throws IOException {
        MessageAckEnum ack = MessageAckEnum.REJECT;
        try {
            if (callback.apply(body)) {
                ack = MessageAckEnum.ACCEPT;
            }
        } catch (Exception e) {
            ack = MessageAckEnum.RETRY;
            log.error("message.rabbit: on.broker.push.text.message, queue:[{}], body:[{}]", queue, body, e);
        } finally {
            switch (ack) {
                case ACCEPT:
                    channel.basicAck(deliveryTag, false);
                    log.info("message.rabbit: ack.broker.push.text.message.succeed, queue:[{}], action:[{}]", queue, ack.name());
                    break;
                case RETRY:
                    channel.basicNack(deliveryTag, false, this.determineNeedRequeue());
                    break;
                case REJECT:
                    channel.basicReject(deliveryTag, this.determineNeedRequeue());
                    break;
                default:
                    break;
            }
        }
    }
}
