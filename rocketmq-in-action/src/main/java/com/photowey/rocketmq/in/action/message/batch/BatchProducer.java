package com.photowey.rocketmq.in.action.message.batch;

import com.photowey.rocketmq.in.action.message.AbstractMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code BatchProducer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class BatchProducer extends AbstractMessageProducer {

    public void produce() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = this.populateMQProducer("rocketmq-batch-producer-group");
        producer.start();
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String content = ("Hello RocketMQ, Batch Message, id:" + i);
            Message message = new Message("rocketmq-topic-batch", "rocketmq-topic-batch-tagA", this.serializeMessageBody(content));
            messages.add(message);
        }
        producer.send(messages);

        Thread.sleep(2_000);
        producer.shutdown();
    }

}
