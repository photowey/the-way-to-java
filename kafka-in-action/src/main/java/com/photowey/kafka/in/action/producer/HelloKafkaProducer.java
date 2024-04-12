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
package com.photowey.kafka.in.action.producer;

import com.photowey.kafka.in.action.constant.HelloKafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * {@code HelloKafkaProducer}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/12
 */
@Slf4j
@Component
public class HelloKafkaProducer implements ApplicationListener<ContextRefreshedEvent> {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Executor taskExecutor;

    public HelloKafkaProducer(KafkaTemplate<String, String> kafkaTemplate, Executor taskExecutor) {
        this.kafkaTemplate = kafkaTemplate;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.taskExecutor.execute(() -> {
            for (int i = 0; i < 100; i++) {
                this.sendSync(HelloKafkaConstants.KEY_NAME, String.format("Hello,Kafka.sync.%d!", i));
                this.sendAsync(HelloKafkaConstants.KEY_NAME, String.format("Hello,Kafka.async.%d!", i));

                sleep(100);
            }
        });
    }

    private void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendSync(String key, String message) {
        try {
            ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(HelloKafkaConstants.TOPIC_NAME, key, message);
            future.get(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendAsync(String key, String message) {
        ListenableFuture<SendResult<String, String>> f1 = this.kafkaTemplate.send(HelloKafkaConstants.TOPIC_NAME, key, message);
        f1.addCallback((ok) -> {
            RecordMetadata metadata = ok.getRecordMetadata();
            log.info("Succeed: rvt:[topic: [{}] offset: [{}]]", metadata.topic(), metadata.offset());
        }, (failed) -> {
            log.error("Failed", failed);
        });
    }
}