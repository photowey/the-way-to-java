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
package com.photowey.rabbitmq.in.action.processor;

import com.alibaba.fastjson.JSON;
import com.photowey.rabbitmq.in.action.config.RabbitMQAutoConfiguration;
import com.photowey.rabbitmq.in.action.constant.RabbitMQConstants;
import com.photowey.rabbitmq.in.action.domain.MessageBody;
import com.rabbitmq.client.Channel;
import lombok.Data;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * {@code OrderProcessor}
 *
 * @author photowey
 * @date 2022/07/31
 * @since 1.0.0
 */
@Component
public class OrderProcessor extends AbstractMessageProcessor {

    @RabbitHandler
    @RabbitListener(
            queues = RabbitMQConstants.DEFAULT_QUEUE,
            containerFactory = RabbitMQAutoConfiguration.MESSAGE_CONTAINER_FACTORY_NAME
    )
    public void processExchangeOrder(
            @Payload OrderMessage message,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
            Channel channel) throws IOException {
        super.handleBodyMessage(
                message,
                RabbitMQConstants.DEFAULT_QUEUE,
                deliveryTag,
                channel,
                this::handleMessage
        );
    }

    public boolean handleMessage(OrderMessage body) {
        return true;
    }


    @Data
    public static class OrderMessage implements MessageBody {

        private Long orderId;

        @Override
        public String toJSONString() {
            return JSON.toJSONString(this);
        }
    }

}
