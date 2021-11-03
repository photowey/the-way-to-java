package com.photowey.rocketmq.in.action.message.filter.sql;

import com.photowey.rocketmq.in.action.message.AbstractMessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@code SQLFilterConsumer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class SQLFilterConsumer extends AbstractMessageConsumer {

    public void consume() throws MQClientException {
        DefaultMQPushConsumer consumer = this.populateMQPushConsumer("rocketmq-sql-filter-consumer-group");
        // Don't forget to set enablePropertyFilter=true in broker
        consumer.subscribe("rocketmq-topic-sql-filter",
                MessageSelector.bySql("(TAGS is not null and TAGS in ('rocketmq-topic-sql-filter-tagA', 'rocketmq-topic-sql-filter-tagB')) and (filterField is not null and filterField between 0 and 5)"));
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
                            log.info("handle consume the sql-filter message,topic:[{}],tags:[{}],filterField:[{}]", topic, tags, filterField);
                        }
                    }
                } catch (Exception e) {
                    log.error("handle consume the sql-filter message exception", e);
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
