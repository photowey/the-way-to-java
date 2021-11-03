package com.photowey.rocketmq.in.action.message;

import com.photowey.rocketmq.in.action.engine.IRocketmqEngine;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

/**
 * {@code AbstractMessageConsumer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
public abstract class AbstractMessageConsumer extends AbstractMessageActor {

    @Autowired
    protected IRocketmqEngine rocketmqEngine;

    protected DefaultMQPushConsumer populateMQPushConsumer() {
        return this.populateMQPushConsumer("rocketmq-default-consumer-group");
    }

    protected DefaultMQPushConsumer populateMQPushConsumer(MessageModel messageModel) {
        return this.populateMQPushConsumer("rocketmq-default-consumer-group", messageModel);
    }

    protected DefaultMQPushConsumer populateMQPushConsumer(String consumerGroup) {
        return this.populateMQPushConsumer(consumerGroup, MessageModel.CLUSTERING);
    }

    protected DefaultMQPushConsumer populateMQPushConsumer(String consumerGroup, MessageModel messageModel) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(this.rocketmqEngine.rocketmqProperties().getNameServer());
        consumer.setMaxReconsumeTimes(1);
        consumer.setMessageModel(messageModel);

        return consumer;
    }

    protected DefaultMQPushConsumer populateMQPushOrderConsumer(String consumerGroup) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(this.rocketmqEngine.rocketmqProperties().getNameServer());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        return consumer;
    }

    protected String deserializeMessageBody(byte[] body) {
        return new String(body, StandardCharsets.UTF_8);
    }

}
