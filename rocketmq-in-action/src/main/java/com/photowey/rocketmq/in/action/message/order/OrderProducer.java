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
package com.photowey.rocketmq.in.action.message.order;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * {@code OrderProducer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class OrderProducer extends AbstractMessageProducer {

    public void produce() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = this.populateMQProducer("rocketmq-order-producer-group");
        producer.start();

        // 订单列表
        List<TMallOrder> orderList = TMallOrderFactory.buildOrders();

        String now = now();
        for (int i = 0; i < orderList.size(); i++) {
            TMallOrder tMallOrder = orderList.get(i);
            String body = now + ":Order:" + JSON.toJSONString(tMallOrder);
            Message message = new Message("rocketmq-topic-part-order", TOPIC_PART_ORDER_MESSAGE_TAGS[i % TOPIC_PART_ORDER_MESSAGE_TAGS.length], "rocketmq-topic-part-order-key-" + i, this.serializeMessageBody(body));
            SendResult sendResult = producer.send(message, (mqs, msg, orderId) -> {
                Long id = (Long) orderId;
                long index = id % mqs.size();
                return mqs.get((int) index);
            }, tMallOrder.getOrderId());
            if (log.isInfoEnabled()) {
                log.info("send the Order message:[{}] succeed...,the result is:\n{}", body, JSON.toJSONString(sendResult, SerializerFeature.PrettyFormat));
            }
        }

        Thread.sleep(2_000);
        producer.shutdown();
    }

    public static String now() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(now);
    }

}
