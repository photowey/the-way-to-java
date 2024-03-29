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
package com.photowey.rabbitmq.in.action.enums;

/**
 * {@code RabbitMQAckEnum}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
public enum RabbitMQAckEnum {
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
