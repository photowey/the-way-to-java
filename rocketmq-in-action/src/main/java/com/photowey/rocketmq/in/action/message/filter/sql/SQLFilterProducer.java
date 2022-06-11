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
package com.photowey.rocketmq.in.action.message.filter.sql;

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
 * {@code SQLFilterProducer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class SQLFilterProducer extends AbstractMessageProducer {

    public void produce() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = this.populateMQProducer("rocketmq-sql-filter-producer-group");
        producer.start();
        for (int i = 0; i < 20; i++) {
            String content = ("Hello RocketMQ, SQL-Filter Message, id:" + i);
            Message message = new Message("rocketmq-topic-sql-filter", TOPIC_SQL_FILTER_MESSAGE_TAGS[i % TOPIC_SQL_FILTER_MESSAGE_TAGS.length], this.serializeMessageBody(content));
            message.putUserProperty("filterField", String.valueOf(i));
            SendResult sendResult = producer.send(message);
            if (log.isInfoEnabled()) {
                log.info("send the sql-filter message:[{}] succeed...,the result is:\n{}", content, JSON.toJSONString(sendResult, SerializerFeature.PrettyFormat));
            }
        }

        Thread.sleep(2_000);
        producer.shutdown();
    }

}
