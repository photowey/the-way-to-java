package com.photowey.rocketmq.in.action.message.batch;

import com.photowey.rocketmq.in.action.message.AbstractMessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@code BatchConsumer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class BatchConsumer extends AbstractMessageConsumer {

    public void consume() throws MQClientException {
        DefaultMQPushConsumer consumer = this.populateMQPushConsumer("rocketmq-batch-consumer-group");
        consumer.subscribe("rocketmq-topic-batch", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                try {
                    for (MessageExt message : messages) {
                        String topic = message.getTopic();
                        String body = deserializeMessageBody(message.getBody());
                        String tags = message.getTags();
                        if (log.isInfoEnabled()) {
                            log.info("handle consume the batch message,topic:[{}],tags:[{}],body:[{}],thread:[{}]", topic, tags, body, Thread.currentThread().getName());
                        }
                    }
                } catch (Exception e) {
                    log.error("handle consume the batch message exception", e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        if (log.isInfoEnabled()) {
            log.info("--- >>> the batch consumer start succeed <<< ---");
        }
    }

}
