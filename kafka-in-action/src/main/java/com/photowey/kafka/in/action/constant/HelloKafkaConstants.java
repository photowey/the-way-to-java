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
package com.photowey.kafka.in.action.constant;

/**
 * {@code HelloKafkaConstants}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/12
 */
public interface HelloKafkaConstants {

    String KAFKA_BOOTSTRAP_SERVERS = "localhost:9092";
    String TOPIC_NAME = "io.github.photowey.kafka.in.action.topic";
    String KEY_NAME = "io.github.photowey.kafka.in.action.key";
    String CONSUMER_GROUP_ID = "io.github.photowey.kafka.in.action.topic.group";

    String TRANSACTION_KEY = "io.github.photowey.kafka.in.action.transaction.key";
    String TRANSACTION_TOPIC_NAME = "io.github.photowey.kafka.in.action.transaction.topic";
    String TRANSACTION_KEY_NAME = "io.github.photowey.kafka.in.action.transaction.key";
    String TRANSACTION_CONSUMER_GROUP_ID = "io.github.photowey.kafka.in.action.transaction.topic.group";
}
