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
package com.photowey.micro.integrated.message.handler.sender;

import org.springframework.core.Ordered;

/**
 * {@code MessageSender}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
public interface MessageSender extends Sender, Ordered {

    /**
     * 发送主题类消息
     *
     * @param topic   主题
     * @param payload 消息体
     */
    default <T> void send(String topic, T payload) {}

    /**
     * 发送交换机类消息
     *
     * @param exchange   交换机
     * @param routingKey 路由键
     * @param payload    消息体
     */
    default <T> void send(String exchange, String routingKey, T payload) {}
}
