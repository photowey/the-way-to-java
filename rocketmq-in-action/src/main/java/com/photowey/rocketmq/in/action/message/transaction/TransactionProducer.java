package com.photowey.rocketmq.in.action.message.transaction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.photowey.rocketmq.in.action.message.AbstractMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

/**
 * {@code TransactionProducer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class TransactionProducer extends AbstractMessageProducer {

    public void produce() throws MQClientException, InterruptedException {
        DefaultMQProducer producer = this.populateTransactionMQProducer("rocketmq-transaction-producer-group");
        producer.start();

        for (int i = 0; i < 3; i++) {
            String content = ("A transfers 100 YUAN to the B system");
            Message message = new Message("rocketmq-topic-transaction", "rocketmq-topic-transaction-tagA", this.serializeMessageBody(content));
            SendResult sendResult = producer.sendMessageInTransaction(message, null);
            if (log.isInfoEnabled()) {
                log.info("send the Transaction message:[{}:{}] succeed...,the result is:\n{}", (i + 1), content, JSON.toJSONString(sendResult, SerializerFeature.PrettyFormat));
            }
        }

        Thread.sleep(1000_000);
        producer.shutdown();
    }

}
