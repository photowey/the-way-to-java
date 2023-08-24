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
package com.photowey.rocketmq.in.action.message.normal.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.photowey.rocketmq.in.action.message.AbstractMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

/**
 * {@code AsyncProducer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class AsyncProducer extends AbstractMessageProducer {

    /**
     * 发送异步步消息
     * <pre>
     *     {
     * 	"messageQueue":{
     * 		"brokerName":"photowey",
     * 		"queueId":0,
     * 		"topic":"rocketmq-topic-test"
     *        },
     * 	"msgId":"7F00000136382437C6DC9E51EFDD0002",
     * 	"offsetMsgId":"C0A8000500002A9F00000000000001F4",
     * 	"queueOffset":1,
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
    public void produce() throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = this.populateMQProducer();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        producer.setSendLatencyFaultEnable(true);
        producer.start();
        for (int i = 0; i < 20; i++) {
            String content = ("Hello RocketMQ, Async Message, id:" + i);
            Message message = new Message("rocketmq-topic-test", "rocketmq-topic-test-tagA", this.serializeMessageBody(content));
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    if (log.isInfoEnabled()) {
                        log.info("send the Async message:[{}] succeed...,the result is:\n{}", content, JSON.toJSONString(sendResult, SerializerFeature.PrettyFormat));
                    }
                }

                @Override
                public void onException(Throwable e) {
                    log.info("send the Async message:[{}] failed...", content, e);
                }
            });
        }

        Thread.sleep(2_000);
        producer.shutdown();
    }
}
