package com.photowey.kafkaplus.in.action.engine;

import com.photowey.kafkaplus.in.action.LocalTest;
import io.github.photowey.kafka.plus.autoconfigure.engine.SpringKafkaEngineImpl;
import io.github.photowey.kafka.plus.engine.KafkaEngine;
import org.junit.jupiter.api.Assertions;

/**
 * {@code KafkaEngineTest}
 *
 * @author photowey
 * @date 2024/04/07
 * @since 1.0.0
 */
//@SpringBootTest
class KafkaEngineTest extends LocalTest {

    //@Test
    void testKafkaEngine_spring_impl() {
        Assertions.assertNotNull(this.kafkaEngine);
        Assertions.assertEquals(SpringKafkaEngineImpl.class.getName(), this.kafkaEngine.getClass().getName());
    }

    //@Test
    void testKafkaEngine_builtin_impl() {
        KafkaEngine kafkaEngine = this.kafkaEngine();
        Assertions.assertNotNull(kafkaEngine);
        Assertions.assertEquals(SpringKafkaEngineImpl.class.getName(), kafkaEngine.getClass().getName());
    }
}