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
package com.photowey.rabbitmq.in.action.service;

/**
 * {@code IRabbitMQService}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
public interface IRabbitMQService {

    /**
     * 发送消息到指定队列
     *
     * @param routingKey 队列名称
     * @param message    队列消息
     */
    void toQueue(String routingKey, Object message);

    /**
     * 发送消息到指定队列
     *
     * @param routingKey 队列名称
     * @param message    队列消息
     * @param exchange   交换机名称
     */
    void toQueue(String exchange, String routingKey, Object message);
}