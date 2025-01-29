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
package com.photowey.kafkaplus.in.action.runtime.service.consumer;

import com.photowey.kafkaplus.in.action.LocalTest;
import io.github.photowey.kafka.plus.core.enums.Kafka;
import io.github.photowey.kafka.plus.engine.KafkaEngine;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Collections;

/**
 * {@code ConsumerServiceTest}
 *
 * @author photowey
 * @date 2024/04/07
 * @since 1.0.0
 */
@SpringBootTest
class ConsumerServiceTest extends LocalTest {

    @Test
    void testConsumer() {
        KafkaEngine kafkaEngine = super.kafkaEngine;

        try (KafkaConsumer<String, String> consumer = kafkaEngine.consumerService().createConsumer()
                .boostrapServers(this.defaultBoostrapServers())
                .keyDeserializer(StringDeserializer.class)
                .valueDeserializer(StringDeserializer.class)
                .autoOffsetReset(Kafka.Consumer.AutoOffsetReset.EARLIEST)
                .groupId(this.defaultGroup())
                .autoCommit(true)
                .subscribe(this.defaultTopic())
                .checkConfigs(super::testBoostrapServers)
                .build()) {

            consumer.subscribe(Collections.singletonList(this.defaultTopic()));

            Assertions.assertNotNull(consumer);
        }
    }

    @Test
    void testConsumer_with_subscribe() {
        KafkaEngine kafkaEngine = super.kafkaEngine;

        try (KafkaConsumer<String, String> consumer = kafkaEngine.consumerService().createConsumer()
                .boostrapServers(this.defaultBoostrapServers())
                .keyDeserializer(StringDeserializer.class)
                .valueDeserializer(StringDeserializer.class)
                .autoOffsetReset(Kafka.Consumer.AutoOffsetReset.EARLIEST)
                .groupId(this.defaultGroup())
                .autoCommit(true)
                .subscribe(this.defaultTopic())
                .checkConfigs(super::testBoostrapServers)
                .build()) {

            Assertions.assertNotNull(consumer);
        }
    }

    @Test
    void testConsumer_consume_pull() {
        KafkaEngine kafkaEngine = super.kafkaEngine;

        try (KafkaConsumer<String, String> consumer = kafkaEngine.consumerService().createConsumer()
                .boostrapServers(this.defaultBoostrapServers())
                .keyDeserializer(StringDeserializer.class)
                .valueDeserializer(StringDeserializer.class)
                .autoOffsetReset(Kafka.Consumer.AutoOffsetReset.EARLIEST)
                .groupId(this.defaultGroup())
                .autoCommit(true)
                .subscribe(this.defaultTopic())
                .checkConfigs(super::testBoostrapServers)
                .build()) {

            for (int i = 0; i < 5; i++) {

                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Key = " + record.key() + ", Value = " + record.value());
                }

                sleep(1_000L);
            }
        }

        sleep(1_000L);
    }

}