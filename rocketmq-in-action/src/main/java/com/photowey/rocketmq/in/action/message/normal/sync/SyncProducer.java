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
package com.photowey.rocketmq.in.action.message.normal.sync;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.photowey.rocketmq.in.action.message.AbstractMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

/**
 * {@code SyncProducer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class SyncProducer extends AbstractMessageProducer {

    /**
     * 发送同步消息
     *
     * <pre>
     *     {
     * 	"messageQueue":{
     * 		"brokerName":"photowey",
     * 		"queueId":2,
     * 		"topic":"rocketmq-topic-test"
     *        },
     * 	"msgId":"7F00000135CC2437C6DC9E229D7D0002",
     * 	"offsetMsgId":"C0A8000500002A9F0000000000005D8C",
     * 	"queueOffset":25,
     * 	"regionId":"DefaultRegion",
     * 	"sendStatus":"SEND_OK",
     * 	"traceOn":true
     * }
     * </pre>
     *
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    public void produce() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = this.populateMQProducer();
        producer.setSendLatencyFaultEnable(true);
        producer.start();
        for (int i = 0; i < 20; i++) {
            String content = ("Hello RocketMQ, Sync Message, id:" + i);
            Message message = new Message("rocketmq-topic-test", "rocketmq-topic-test-tagA", this.serializeMessageBody(content));
            SendResult sendResult = producer.send(message);
            if (log.isInfoEnabled()) {
                log.info("send the sync message:[{}] succeed...,the result is:\n{}", content, JSON.toJSONString(sendResult, SerializerFeature.PrettyFormat));
            }
        }

        Thread.sleep(2_000);
        producer.shutdown();
    }

}
