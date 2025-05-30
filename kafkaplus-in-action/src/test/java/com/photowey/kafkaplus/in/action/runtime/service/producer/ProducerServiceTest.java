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
package com.photowey.kafkaplus.in.action.runtime.service.producer;

import com.photowey.kafkaplus.in.action.LocalTest;
import io.github.photowey.kafka.plus.engine.KafkaEngine;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Assertions;

/**
 * {@code ProducerServiceTest}
 *
 * @author photowey
 * @date 2024/04/07
 * @since 1.0.0
 */
//@SpringBootTest
class ProducerServiceTest extends LocalTest {

    //@Test
    void testProducer() {
        StringSerializer keySerializer = new StringSerializer();
        StringSerializer valueSerializer = new StringSerializer();

        KafkaEngine kafkaEngine = super.kafkaEngine;

        try (KafkaProducer<String, String> producer = kafkaEngine.producerService().createProducer()
                .boostrapServers(this.defaultBoostrapServers())
                .keySerializer(keySerializer)
                .valueSerializer(valueSerializer)
                .build()) {

            Assertions.assertNotNull(producer);
        }
    }

    //@Test
    void testProducerRecord() {
        KafkaEngine kafkaEngine = super.kafkaEngine;

        ProducerRecord<String, String> record = kafkaEngine.producerService().createProducerRecord()
                .topic(this.defaultTopic())
                .key("key-9527")
                .value("value-9527")
                .build();

        Assertions.assertNotNull(record);
    }

    //@Test
    void testProducer_produce() {
        StringSerializer keySerializer = new StringSerializer();
        StringSerializer valueSerializer = new StringSerializer();

        KafkaEngine kafkaEngine = super.kafkaEngine;

        try (KafkaProducer<String, String> producer = kafkaEngine.producerService().createProducer()
                .boostrapServers(this.defaultBoostrapServers())
                .keySerializer(keySerializer)
                .valueSerializer(valueSerializer)
                .build()) {

            for (int i = 0; i < 100; i++) {
                ProducerRecord<String, String> record = kafkaEngine.producerService().createProducerRecord()
                        .topic(this.defaultTopic())
                        .key("key-" + i)
                        .value("value-" + i)
                        .build();

                producer.send(record);
            }
        }

        sleep(1_000L);
    }
}