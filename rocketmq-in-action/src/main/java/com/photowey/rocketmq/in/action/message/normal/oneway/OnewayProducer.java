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
