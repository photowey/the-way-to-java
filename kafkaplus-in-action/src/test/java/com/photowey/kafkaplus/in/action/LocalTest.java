package com.photowey.kafkaplus.in.action;

import io.github.photowey.kafka.plus.core.enums.Kafka;
import io.github.photowey.kafka.plus.engine.KafkaEngine;
import io.github.photowey.kafka.plus.engine.holder.KafkaEngineHolder;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@code LocalTest}
 *
 * @author photowey
 * @date 2024/04/07
 * @since 1.0.0
 */
public abstract class LocalTest {

    public static final String DEFAULT_BOOTSTRAP_SERVERS = io.github.photowey.kafka.plus.core.enums.Kafka.Bootstrap.Server.DEFAULT_LOCALHOST.value();
    public static final String DEFAULT_HELLO_WORLD_TOPIC = "io.github.photowey.kafkaplus.helloworld.topic";
    public static final String DEFAULT_HELLO_WORLD_GROUP = "io.github.photowey.kafkaplus.helloworld.topic.group";

    // ----------------------------------------------------------------

    @Autowired(required = false)
    protected KafkaEngine kafkaEngine;

    // ----------------------------------------------------------------

    public KafkaEngine kafkaEngine() {
        return KafkaEngineHolder.INSTANCE.kafkaEngine();
    }

    // ----------------------------------------------------------------

    public String defaultTopic() {
        return DEFAULT_HELLO_WORLD_TOPIC;
    }

    public String defaultGroup() {
        return DEFAULT_HELLO_WORLD_GROUP;
    }

    public String defaultBoostrapServers() {
        return DEFAULT_BOOTSTRAP_SERVERS;
    }

    // ----------------------------------------------------------------

    public void tryCreateTopicIfNecessary() {
        KafkaEngine kafkaEngine = this.kafkaEngine();

        try (Admin admin = kafkaEngine.adminService().createAdmin()
                .boostrapServers(this.defaultBoostrapServers())
                .checkConfigs(this::testBoostrapServers)
                .build()) {

            NewTopic topic = kafkaEngine.adminService().createTopic()
                    .topic(this.defaultTopic())
                    .numPartitions(1)
                    .replicationFactor(1)
                    .build();

            try {
                admin.createTopics(Collections.singleton(topic));
            } catch (Exception ignored) {
            }
        }
    }

    // ----------------------------------------------------------------

    public void testBoostrapServers(Map<String, Object> configs) {
        if (null == configs.get(Kafka.Bootstrap.Server.ADDRESS.value())) {
            throw new RuntimeException("The bootstrap server address can't be none/empty");
        }
    }

    public static void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}