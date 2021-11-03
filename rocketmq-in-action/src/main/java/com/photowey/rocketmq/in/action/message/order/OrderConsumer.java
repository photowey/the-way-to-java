package com.photowey.rocketmq.in.action.message.order;

import com.photowey.rocketmq.in.action.message.AbstractMessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * {@code OrderConsumer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class OrderConsumer extends AbstractMessageConsumer {

    public void consume() throws MQClientException {
        DefaultMQPushConsumer consumer = this.populateMQPushOrderConsumer("rocketmq-part-order-consumer-group");

        String tags = String.join(" || ", TOPIC_PART_ORDER_MESSAGE_TAGS);
        consumer.subscribe("rocketmq-topic-part-order", tags);

        consumer.registerMessageListener(new MessageListenerOrderly() {
            final Random random = new SecureRandom();

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> messages, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                for (MessageExt message : messages) {
                    String topic = message.getTopic();
                    String body = deserializeMessageBody(message.getBody());
                    String tags = message.getTags();
                    if (log.isInfoEnabled()) {
                        // 可以看到每个 queue 有唯一的 consume 线程来消费, 订单对每个 queue (分区)有序
                        log.info("handle consume the part-order message,topic:[{}],tags:[{}],body:[{}],thread:[{}]", topic, tags, body, Thread.currentThread().getName());
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(300));
                } catch (Exception e) {
                    log.error("handle consume the part-order message exception", e);
                    // 这个点要注意: 意思是先等一会,一会儿再处理这批消息,而不是放到重试队列里
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();
        if (log.isInfoEnabled()) {
            log.info("--- >>> the part-order consumer start succeed <<< ---");
        }
    }
}
