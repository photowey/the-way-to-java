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
package com.photowey.rocketmq.in.action.message.delay;

import com.photowey.rocketmq.in.action.message.AbstractMessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@code DelayConsumer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayConsumer extends AbstractMessageConsumer {

    public void consume() throws MQClientException {
        DefaultMQPushConsumer consumer = this.populateMQPushConsumer(MessageModel.BROADCASTING);
        consumer.subscribe("rocketmq-topic-delay", "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                for (MessageExt message : messages) {
                    String topic = message.getTopic();
                    String body = deserializeMessageBody(message.getBody());
                    String tags = message.getTags();
                    long delta = (message.getStoreTimestamp() - message.getBornTimestamp());
                    if (log.isInfoEnabled()) {
                        log.info("handle consume the delay message,topic:[{}],tags:[{}],message:[{}:{}],delta:[{}] ms", topic, tags, message.getMsgId(), body, delta);
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        if (log.isInfoEnabled()) {
            log.info("--- >>> the delay consumer start succeed <<< ---");
        }
    }
}
