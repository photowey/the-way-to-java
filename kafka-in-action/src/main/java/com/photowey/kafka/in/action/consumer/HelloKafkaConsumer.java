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
package com.photowey.kafka.in.action.consumer;

import com.photowey.kafka.in.action.constant.HelloKafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * {@code HelloKafkaConsumer}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/12
 */
@Slf4j
@Component
public class HelloKafkaConsumer {

    @KafkaListener(id = HelloKafkaConstants.CONSUMER_GROUP_ID, topics = {
            HelloKafkaConstants.TOPIC_NAME,
    })
    public void onNormalMessage(String message) {
        log.info("Received kafka message: {}", message);
    }

    @KafkaListener(id = HelloKafkaConstants.TRANSACTION_CONSUMER_GROUP_ID, topics = {
            HelloKafkaConstants.TRANSACTION_TOPIC_NAME,
    })
    public void onTxMessage(String message) {
        log.info("Received kafka tx.message: {}", message);
    }
}