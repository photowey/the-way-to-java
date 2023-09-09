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
package com.photowey.micro.integrated.message.rabbitmq.handler.sender;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.photowey.commom.in.action.formatter.StringFormatter;
import com.photowey.commom.in.action.util.ObjectUtils;
import com.photowey.micro.integrated.message.handler.sender.Sender;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * {@code RabbitSender}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
public interface RabbitSender extends Sender, SmartInitializingSingleton {

    String RABBIT_MESSAGE_SENDER_NAME = "Rabbit";
    String RABBIT_DELAYED_MESSAGE_SENDER_NAME = "Rabbit.delayed";
    String RABBIT_MESSAGE_TYPE_NORMAL = "normal";
    String RABBIT_MESSAGE_TYPE_DELAYED = "delayed";
    String RABBIT_MESSAGE_ID_TEMPLATE = "{}@{}@{}";

    int DEFAULT_DATA_ID_SIZE = 21;

    @Override
    default String name() {
        return RABBIT_MESSAGE_SENDER_NAME;
    }

    default <T> String populateNormalMessageId(T payload) {
        return this.buildMessageId(RABBIT_MESSAGE_TYPE_NORMAL, payload.getClass().getName());
    }

    default <T> String populateDelayedMessageId(T payload) {
        return this.buildMessageId(RABBIT_MESSAGE_TYPE_DELAYED, payload.getClass().getName());
    }

    default String buildMessageId(String type, String data) {
        String dataId = IdWorker.getIdStr();
        if (ObjectUtils.isNotNullOrEmpty(data)) {
            return StringFormatter.format(RABBIT_MESSAGE_ID_TEMPLATE, dataId, type, data);
        }

        return dataId;
    }
}
