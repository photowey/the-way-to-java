package com.photowey.rocketmq.in.action.message.filter.tag;

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
 * {@code TagFilterProducer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class TagFilterProducer extends AbstractMessageProducer {

    public void produce() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = this.populateMQProducer("rocketmq-tag-filter-producer-group");
        producer.start();
        for (int i = 0; i < 20; i++) {
            String content = ("Hello RocketMQ, Tag-Filter Message, id:" + i);
            Message message = new Message("rocketmq-topic-tag-filter", TOPIC_TAG_FILTER_MESSAGE_TAGS[i % TOPIC_TAG_FILTER_MESSAGE_TAGS.length], this.serializeMessageBody(content));
            message.putUserProperty("filterField", String.valueOf(i));
            SendResult sendResult = producer.send(message);
            if (log.isInfoEnabled()) {
                log.info("send the tag-filter message:[{}] succeed...,the result is:\n{}", content, JSON.toJSONString(sendResult, SerializerFeature.PrettyFormat));
            }
        }

        Thread.sleep(2_000);
        producer.shutdown();
    }

}
