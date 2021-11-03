package com.photowey.rocketmq.in.action.message.delay;

import com.photowey.rocketmq.in.action.message.AbstractMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

/**
 * {@code DelayProducer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayProducer extends AbstractMessageProducer {

    public void produce() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = this.populateMQProducer("rocketmq-delay-producer-group");
        producer.start();
        for (int i = 0; i < 20; i++) {
            String content = ("Hello RocketMQ, Delay Message, id:" + i);
            Message message = new Message("rocketmq-topic-delay", "rocketmq-topic-delay-tagA", this.serializeMessageBody(content));
            // (1~18个等级) "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"
            // org.apache.rocketmq.store.config.MessageStoreConfig#messageDelayLevel
            message.setDelayTimeLevel(3);
            producer.send(message);
        }

        Thread.sleep(2_000);
        producer.shutdown();
    }

}
