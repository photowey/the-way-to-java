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
package com.photowey.rabbitmq.in.action.domain;

import com.photowey.rabbitmq.in.action.constant.RabbitMqConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * {@code RabbitMqPayload}
 *
 * @author photowey
 * @date 2022/05/29
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMqPayload<T> implements Serializable {

    private static final long serialVersionUID = 3890285964805460810L;

    /**
     * 默认的交换机
     */
    private String exchange = RabbitMqConstants.DEFAULT_EXCHANGE;
    /**
     * 默认的队列
     */
    private String queueName = RabbitMqConstants.DEFAULT_QUEUE;
    /**
     * 消息体
     */
    private T data;
    /**
     * 延时时间,默认300000
     */
    private long delayedTime = 300000;
    /**
     * 时间单位
     */
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
}