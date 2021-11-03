package com.photowey.rocketmq.in.action.message.transaction;

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
 * {@code TransactionConsumer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Slf4j
@Component
public class TransactionConsumer extends AbstractMessageConsumer {

    public void consume() throws MQClientException {
        DefaultMQPushConsumer consumer = this.populateMQPushConsumer("rocketmq-transaction-consumer-group");
        consumer.subscribe("rocketmq-topic-transaction", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                try {
                    for (MessageExt message : messages) {
                        // TODO 开启事务

                        //TODO 执行本地事务 update B...(幂等性)
                        log.info("update B ... where transactionId:{}", message.getTransactionId());

                        // TODO 本地事务成功
                        log.info("handle consume the transaction message, the result is:commit");

                        // TODO 执行本地事务成功,确认消息
                    }
                } catch (Exception e) {
                    log.error("handle consume the transaction message exception", e);
                    // TODO 执行本地事务失败,重试消费,尽量确保B处理成功
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        if (log.isInfoEnabled()) {
            log.info("--- >>> the transaction consumer start succeed <<< ---");
        }
    }

}
