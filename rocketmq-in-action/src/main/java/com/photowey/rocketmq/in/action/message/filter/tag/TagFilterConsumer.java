package com.photowey.rocketmq.in.action.message.filter.tag;

import com.photowey.rocketmq.in.action.message.AbstractMessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@code TagFilterConsumer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class TagFilterConsumer extends AbstractMessageConsumer {

    public void consume() throws MQClientException {
        DefaultMQPushConsumer consumer = this.populateMQPushConsumer("rocketmq-tag-filter-consumer-group");
        consumer.subscribe("rocketmq-topic-tag-filter", "rocketmq-topic-tag-filter-tagA || rocketmq-topic-tag-filter-TAGB || rocketmq-topic-tag-filter-TAGC");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                try {
                    for (MessageExt message : messages) {
                        String topic = message.getTopic();
                        String tags = message.getTags();
                        String filterField = message.getProperty("filterField");
                        if (log.isInfoEnabled()) {
                            log.info("handle consume the tag-filter message,topic:[{}],tags:[{}],filterField:[{}]", topic, tags, filterField);
                        }
                    }
                } catch (Exception e) {
                    log.error("handle consume the tag-filter message exception", e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        if (log.isInfoEnabled()) {
            log.info("--- >>> the tag-filter consumer start succeed <<< ---");
        }
    }

}
