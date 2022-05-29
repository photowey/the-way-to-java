/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.rabbitmq.in.action.processor;

import com.photowey.rabbitmq.in.action.domain.MessageBody;
import com.photowey.rabbitmq.in.action.enums.RabbitmqAckEnum;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.function.Function;

/**
 * {@code AbstractMessageProcessor}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractMessageProcessor implements MessageProcessor {

    @Override
    public <T extends MessageBody> void doProcess(
            T messageBody,
            String queue,
            long deliveryTag, Channel channel,
            Function<T, Boolean> function) throws IOException {

        RabbitmqAckEnum rabbitmqAck = RabbitmqAckEnum.ACCEPT;
        try {
            Boolean exchangeOrderBroadcastNotice = function.apply(messageBody);
            if (exchangeOrderBroadcastNotice) {
                rabbitmqAck = RabbitmqAckEnum.ACCEPT;
            } else {
                // TODO 可以重试 - 理论上永远不会返回 FALSE - 仅仅作为 MQ 处理的例子
                rabbitmqAck = RabbitmqAckEnum.RETRY;
            }
        } catch (Exception e) {
            String body = messageBody.toJSONString();
            if (StringUtils.hasText(body)) {
                log.error("handle the queue:[{}] message,exception,info is:{}", queue, body);
            }
            rabbitmqAck = RabbitmqAckEnum.RETRY;
        } finally {
            // TODO 保证 100% 投递与处理
            switch (rabbitmqAck) {
                case ACCEPT:
                    channel.basicAck(deliveryTag, false);
                    break;
                case RETRY:
                    // void basicNack(long deliveryTag, boolean multiple, boolean requeue)
                    channel.basicNack(deliveryTag, false, true);
                    break;
                case REJECT:
                    channel.basicNack(deliveryTag, false, false);
                    break;
                default:
                    break;
            }
        }
    }
}