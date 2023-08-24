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
package com.photowey.rocketmq.in.action.message.normal.oneway;

import com.photowey.rocketmq.in.action.message.AbstractMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

/**
 * {@code OnewayProducer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class OnewayProducer extends AbstractMessageProducer {

    public void produce() throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = this.populateMQProducer();
        producer.start();
        for (int i = 0; i < 20; i++) {
            //  allowing only ^[%|a-zA-Z0-9_-]+$
            String content = ("Hello RocketMQ, Oneway Message, id:" + i);
            Message message = new Message("rocketmq-topic-test", "rocketmq-topic-test-tagA", this.serializeMessageBody(content));
            producer.sendOneway(message);
            log.info("send the oneway message:[{}] succeed...", content);
        }

        Thread.sleep(2_000);
        producer.shutdown();
    }
}
