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

import com.photowey.micro.integrated.message.core.domain.body.MessageBody;
import com.photowey.micro.integrated.message.handler.processor.MessageProcessor;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.function.Function;

/**
 * {@code RabbitMessageProcessor}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
public interface RabbitMessageProcessor extends MessageProcessor {

    /**
     * 处理-消息体类型的消息
     *
     * @param body     消息体
     * @param queue    队列名称
     * @param callback 回调函数
     * @param <T>      消息类型
     * @throws IOException
     */
    default <T extends MessageBody> void onObjectMessage(
            T body, String queue,
            Function<T, Boolean> callback) throws IOException {

    }

    /**
     * 处理-消息体类型的消息
     *
     * @param body        消息体
     * @param queue       队列名称
     * @param deliveryTag 投递标识
     * @param channel     通道
     * @param callback    回调函数
     * @param <T>         消息类型
     * @throws IOException
     */
    default <T extends MessageBody> void onObjectMessage(
            /*@Payload*/ T body, String queue,
            /*@Header(AmqpHeaders.DELIVERY_TAG)*/ long deliveryTag, Channel channel, Function<T, Boolean> callback) throws IOException {

    }

    /**
     * 处理-字符串类型的消息
     *
     * @param body     字符串消息
     * @param queue    队列名称
     * @param callback 回调函数
     * @throws IOException
     */
    default void onTextMessage(
            String body, String queue,
            Function<String, Boolean> callback) throws IOException {

    }

    /**
     * 处理-字符串类型的消息
     *
     * @param body        字符串消息
     * @param queue       队列名称
     * @param deliveryTag 投递标识
     * @param channel     通道
     * @param callback    回调函数
     * @throws IOException
     */
    default void onTextMessage(
            /*@Payload*/ String body, String queue,
            /*@Header(AmqpHeaders.DELIVERY_TAG)*/ long deliveryTag, Channel channel, Function<String, Boolean> callback) throws IOException {

    }
}
