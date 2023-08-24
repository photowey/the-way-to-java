/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.rocketmq.in.action.message;

import com.photowey.rocketmq.in.action.engine.IRocketmqEngine;
import com.photowey.rocketmq.in.action.message.transaction.TransactionListenerImpl;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@code AbstractMessageProducer}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
public abstract class AbstractMessageProducer extends AbstractMessageActor {

    @Autowired
    protected IRocketmqEngine rocketmqEngine;

    protected DefaultMQProducer populateMQProducer() {
        return this.populateMQProducer("rocketmq-default-producer-group");
    }

    protected DefaultMQProducer populateMQProducer(String producerGroup) {
        //  allowing only ^[%|a-zA-Z0-9_-]+$
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(this.rocketmqEngine.rocketmqProperties().getNameServer());

        return producer;
    }

    protected TransactionMQProducer populateTransactionMQProducer(String producerGroup) {
        TransactionMQProducer producer = new TransactionMQProducer(producerGroup);
        producer.setNamesrvAddr(this.rocketmqEngine.rocketmqProperties().getNameServer());
        producer.setExecutorService(this.populateExecutorService());
        producer.setTransactionListener(this.populateTransactionListener());

        return producer;
    }

    protected byte[] serializeMessageBody(String body) {
        return body.getBytes(StandardCharsets.UTF_8);
    }

    // ============================================================================== Private

    private TransactionListenerImpl populateTransactionListener() {
        return new TransactionListenerImpl();
    }

    private ExecutorService populateExecutorService() {
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                5,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1024), (r) -> {
            Thread thread = new Thread(r);
            thread.setName("client-transaction-msg-check-thread");

            return thread;
        });

        return executorService;
    }

}
