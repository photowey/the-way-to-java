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
package io.github.photowey.redisson.delayed.queue.in.action.delayed;

import com.photowey.common.in.action.shared.json.jackson.Jackson;
import io.github.photowey.redisson.delayed.queue.in.action.App;
import io.github.photowey.redisson.delayed.queue.in.action.core.task.RedissonDelayedTask;
import io.github.photowey.redisson.delayed.queue.in.action.core.task.TaskContext;
import io.github.photowey.redisson.delayed.queue.in.action.listener.AbstractAntDelayedQueueEventListener;
import io.github.photowey.redisson.delayed.queue.in.action.listener.DelayedQueueEventListener;
import io.github.photowey.redisson.delayed.queue.in.action.queue.RedissonDelayedQueue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * {@code RedissonDelayedQueueTests}
 *
 * @author photowey
 * @date 2024/03/14
 * @since 1.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {App.class, RedissonDelayedQueueTests.RedissonDelayedQueueEventListenerConfigure.class})
class RedissonDelayedQueueTests {

    @Autowired
    private Counter counter;

    @Autowired
    private ApplicationContext applicationContext;

    public ApplicationContext applicationContext() {
        return applicationContext;
    }

    @Test
    @Order(1)
    void testRedissonDelayedQueue_max_delayed() {
        this.counter.clean();

        RedissonDelayedQueue delayedQueue = this.applicationContext().getBean(RedissonDelayedQueue.class);
        RedissonDelayedTask<Serializable> task = RedissonDelayedTask.builder()
                .taskId("hello.redisson.delayqueue")
                .payload("hello.redisson.delayqueue")
                // Config: 7 days
                .delayed(TimeUnit.DAYS.toMillis(8))
                .timeUnit(TimeUnit.SECONDS.name())
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            delayedQueue.offer(task);
        });

        Assertions.assertEquals(0, this.counter.registers().size());
    }

    @Test
    @Order(2)
    void testRedissonDelayedQueue_string_payload() {
        this.counter.clean();

        RedissonDelayedQueue delayedQueue = this.applicationContext().getBean(RedissonDelayedQueue.class);
        for (int i = 0; i < 2; i++) {
            RedissonDelayedTask<Serializable> task = RedissonDelayedTask.builder()
                    .taskId(String.valueOf((i + 1)))
                    .payload(String.valueOf((i + 1)))
                    .delayed((i + 2))
                    .timeUnit(TimeUnit.SECONDS.name())
                    .build();

            delayedQueue.offer(task);
        }

        sleep(5_000);

        Assertions.assertEquals(1, this.counter.registers().size());
        Assertions.assertTrue(this.counter.registers().contains(DefaultTopicStringPayloadDelayedQueueEventListener.class.getSimpleName()));
    }

    @Test
    @Order(3)
    void testRedissonDelayedQueue_body_payload_default_topic() {
        this.counter.clean();

        RedissonDelayedQueue delayedQueue = this.applicationContext().getBean(RedissonDelayedQueue.class);

        String prefix = UUID.randomUUID().toString().replaceAll("-", "");

        for (int i = 0; i < 2; i++) {
            HelloPayload payload = HelloPayload.builder()
                    .id(1760223724043808770L)
                    .name("photowey" + (i + 1))
                    .age(18 + i)
                    .build();

            RedissonDelayedTask<Serializable> task = RedissonDelayedTask.builder()
                    .taskId(prefix + (i + 1))
                    .payload(payload)
                    .delayed((i + 1) * 5)
                    .timeUnit(TimeUnit.SECONDS.name())
                    .build();

            delayedQueue.offer(task);
        }

        sleep(12_000);
        Assertions.assertEquals(1, this.counter.registers().size());
        Assertions.assertTrue(this.counter.registers().contains(DefaultTopicDelayedQueueEventListener.class.getSimpleName()));
    }

    @Test
    @Order(4)
    void testRedissonDelayedQueue_body_payload_topic_and_task_with_multi_listener() {
        this.counter.clean();

        RedissonDelayedQueue delayedQueue = this.applicationContext().getBean(RedissonDelayedQueue.class);

        for (int i = 0; i < 2; i++) {
            HelloPayload payload = HelloPayload.builder()
                    .id(1760223724043808770L)
                    .name("photowey" + (i + 1))
                    .age(18 + i)
                    .build();

            RedissonDelayedTask<Serializable> task = RedissonDelayedTask.builder()
                    .topic("io.github.photowey.hello.world.delayed.query.delayqueue.topic")
                    .taskId("io.github.photowey.hello.world" + "." + (i + 1))
                    .payload(payload)
                    .delayed((i + 1) * 5)
                    .timeUnit(TimeUnit.SECONDS.name())
                    .build();

            delayedQueue.offer(task);
        }

        sleep(12_000);
        Assertions.assertEquals(2, this.counter.registers().size());
        Assertions.assertTrue(this.counter.registers().contains(Multi1DelayedQueueEventListener.class.getSimpleName()));
        Assertions.assertTrue(this.counter.registers().contains(Multi2DelayedQueueEventListener.class.getSimpleName()));
    }

    @Test
    @Order(5)
    void testRedissonDelayedQueue_body_payload_topic_and_task_with_single_listener() {
        this.counter.clean();

        RedissonDelayedQueue delayedQueue = this.applicationContext().getBean(RedissonDelayedQueue.class);

        for (int i = 0; i < 2; i++) {
            HelloPayload payload = HelloPayload.builder()
                    .id(1760223724043808770L)
                    .name("photowey" + (i + 1))
                    .age(18 + i)
                    .build();

            RedissonDelayedTask<Serializable> task = RedissonDelayedTask.builder()
                    .topic("io.github.photowey.hello.world.delayed.query.delayqueue.topic")
                    .taskId("io.github.photowey.delayed.queue.single." + (i + 1))
                    .payload(payload)
                    .delayed((i + 1) * 5)
                    .timeUnit(TimeUnit.SECONDS.name())
                    .build();

            delayedQueue.offer(task);
        }

        sleep(12_000);
        Assertions.assertEquals(1, this.counter.registers().size());
        Assertions.assertTrue(this.counter.registers().contains(SingleDelayedQueueEventListener.class.getSimpleName()));
    }

    @Test
    @Order(6)
    void testRedissonDelayedQueue_payload_ant_multi_world_listener() {
        this.counter.clean();

        RedissonDelayedQueue delayedQueue = this.applicationContext().getBean(RedissonDelayedQueue.class);

        for (int i = 0; i < 2; i++) {
            HelloPayload payload = HelloPayload.builder()
                    .id(1760223724043808770L)
                    .name("photowey" + (i + 1))
                    .age(18 + i)
                    .build();

            RedissonDelayedTask<Serializable> task = RedissonDelayedTask.builder()
                    .topic("io.github.photowey.hello.world.delayed.query.delayqueue.topic")
                    // io.github.photowey.ant.*
                    .taskId("io.github.photowey.ant.hello.world." + (i + 1))
                    .payload(payload)
                    .delayed((i + 1) * 5)
                    .timeUnit(TimeUnit.SECONDS.name())
                    .build();

            delayedQueue.offer(task);
        }

        sleep(12_000);
        Assertions.assertEquals(1, this.counter.registers().size());
        Assertions.assertTrue(this.counter.registers().contains(AntMultiDelayedQueueEventListener.class.getSimpleName()));
    }

    @Test
    @Order(7)
    void testRedissonDelayedQueue_payload_ant_single_world_listener() {
        this.counter.clean();

        RedissonDelayedQueue delayedQueue = this.applicationContext().getBean(RedissonDelayedQueue.class);

        for (int i = 0; i < 2; i++) {
            HelloPayload payload = HelloPayload.builder()
                    .id(1760223724043808770L)
                    .name("photowey" + (i + 1))
                    .age(18 + i)
                    .build();

            RedissonDelayedTask<Serializable> task = RedissonDelayedTask.builder()
                    .topic("io.github.photowey.hello.world.delayed.query.delayqueue.topic")
                    // io.github.photowey.ant.#
                    .taskId("io.github.photowey.ant." + (i + 1))
                    .payload(payload)
                    .delayed((i + 1) * 5)
                    .timeUnit(TimeUnit.SECONDS.name())
                    .build();

            delayedQueue.offer(task);
        }

        sleep(12_000);
        Assertions.assertEquals(2, this.counter.registers().size());
        Assertions.assertTrue(this.counter.registers().contains(AntSingleDelayedQueueEventListener.class.getSimpleName()));
        Assertions.assertTrue(this.counter.registers().contains(AntMultiDelayedQueueEventListener.class.getSimpleName()));
    }

    @Configuration
    public static class RedissonDelayedQueueEventListenerConfigure {

        @Bean
        public Counter counter() {
            return new Counter();
        }

        @Bean
        public DefaultTopicStringPayloadDelayedQueueEventListener defaultTopicStringPayloadDelayedQueueEventListener() {
            return new DefaultTopicStringPayloadDelayedQueueEventListener();
        }

        @Bean
        public DefaultTopicDelayedQueueEventListener defaultTopicDelayedQueueEventListener() {
            return new DefaultTopicDelayedQueueEventListener();
        }

        @Bean
        public Multi1DelayedQueueEventListener multi1DelayedQueueEventListener() {
            return new Multi1DelayedQueueEventListener();
        }

        @Bean
        public Multi2DelayedQueueEventListener multi2DelayedQueueEventListener() {
            return new Multi2DelayedQueueEventListener();
        }

        @Bean
        public SingleDelayedQueueEventListener singleDelayedQueueEventListener() {
            return new SingleDelayedQueueEventListener();
        }

        @Bean
        public AntSingleDelayedQueueEventListener antSingleDelayedQueueEventListener() {
            return new AntSingleDelayedQueueEventListener();
        }

        @Bean
        public AntMultiDelayedQueueEventListener antMultiDelayedQueueEventListener() {
            return new AntMultiDelayedQueueEventListener();
        }
    }

    public static class Counter {

        private final Set<String> listeners = new HashSet<>();

        public void clean() {
            this.listeners.clear();
        }

        public void register(String listener) {
            this.listeners.add(listener);
        }

        public Set<String> registers() {
            return Collections.unmodifiableSet(this.listeners);
        }
    }

    @Slf4j
    public static class DefaultTopicStringPayloadDelayedQueueEventListener implements DelayedQueueEventListener {

        @Autowired
        private Counter counter;

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }

        @Override
        public boolean supports(TaskContext<?> ctx) {
            return ctx.topic().equals("io.github.photowey.global.redisson.delayqueue.topic")
                    && ctx.getPayload() instanceof String;
        }

        @Override
        public void handle(TaskContext<?> ctx) {
            this.counter.register(this.getClass().getSimpleName());

            String payload = (String) ctx.getPayload();
            log.info("json.generic: default.topic.string.payload.listener:[{},{}]", ctx.taskId(), payload);
        }
    }

    @Slf4j
    public static class DefaultTopicDelayedQueueEventListener implements DelayedQueueEventListener {

        @Autowired
        private Counter counter;

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }

        @Override
        public boolean supports(TaskContext<?> ctx) {
            return ctx.topic().equals("io.github.photowey.global.redisson.delayqueue.topic")
                    && !(ctx.getPayload() instanceof String);
        }

        @Override
        public void handle(TaskContext<?> ctx) {
            this.counter.register(this.getClass().getSimpleName());

            HelloPayload payload = (HelloPayload) ctx.getPayload();
            log.info("json.generic: default.topic.listener:[{}:{}]", ctx.taskId(), Jackson.toJSONString(payload));
        }
    }

    @Slf4j
    public static class Multi1DelayedQueueEventListener implements DelayedQueueEventListener {

        @Autowired
        private Counter counter;

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE + 100;
        }

        @Override
        public boolean supports(TaskContext<?> ctx) {
            return ctx.topic().startsWith("io.github.photowey.hello.world")
                    && ctx.taskId().startsWith("io.github.photowey.hello.world");
        }

        @Override
        public void handle(TaskContext<?> ctx) {
            this.counter.register(this.getClass().getSimpleName());

            HelloPayload payload = (HelloPayload) ctx.getPayload();
            log.info("json.generic: custom.topic.multi1.listener:[{}:{}]", ctx.taskId(), Jackson.toJSONString(payload));
        }
    }

    @Slf4j
    public static class Multi2DelayedQueueEventListener implements DelayedQueueEventListener {

        @Autowired
        private Counter counter;

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE + 200;
        }

        @Override
        public boolean supports(TaskContext<?> ctx) {
            return ctx.topic().startsWith("io.github.photowey.hello.world")
                    && ctx.taskId().startsWith("io.github.photowey.hello.world");
        }

        @Override
        public void handle(TaskContext<?> ctx) {
            this.counter.register(this.getClass().getSimpleName());

            HelloPayload payload = (HelloPayload) ctx.getPayload();
            log.info("json.generic: custom.topic.multi2.listener:[{}:{}]", ctx.taskId(), Jackson.toJSONString(payload));
        }
    }

    @Slf4j
    public static class SingleDelayedQueueEventListener implements DelayedQueueEventListener {

        @Autowired
        private Counter counter;

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE + 300;
        }

        @Override
        public boolean supports(TaskContext<?> ctx) {
            return ctx.topic().startsWith("io.github.photowey.hello.world")
                    && ctx.taskId().startsWith("io.github.photowey.delayed.queue.single");
        }

        @Override
        public void handle(TaskContext<?> ctx) {
            this.counter.register(this.getClass().getSimpleName());

            HelloPayload payload = (HelloPayload) ctx.getPayload();
            log.info("json.generic: custom.topic.single.listener:[{}:{}]", ctx.taskId(), Jackson.toJSONString(payload));
        }
    }

    @Slf4j
    public static class AntSingleDelayedQueueEventListener extends AbstractAntDelayedQueueEventListener {

        @Autowired
        private Counter counter;

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE + 300;
        }

        @Override
        public boolean supports(TaskContext<?> ctx) {
            return this.matches("io.github.photowey:hello:world:*", ctx.topic())
                    && this.matches("io.github.photowey.ant.#", ctx.taskId());
        }

        @Override
        public void handle(TaskContext<?> ctx) {
            this.counter.register(this.getClass().getSimpleName());

            HelloPayload payload = (HelloPayload) ctx.getPayload();
            log.info("json.generic: ant.single.listener:[{}:{}]", ctx.taskId(), Jackson.toJSONString(payload));
        }
    }

    @Slf4j
    public static class AntMultiDelayedQueueEventListener extends AbstractAntDelayedQueueEventListener {

        @Autowired
        private Counter counter;

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE + 300;
        }

        @Override
        public boolean supports(TaskContext<?> ctx) {
            return this.matches("io.github.photowey:hello:world:*", ctx.topic())
                    && this.matches("io.github.photowey.ant.*", ctx.taskId());
        }

        @Override
        public void handle(TaskContext<?> ctx) {
            this.counter.register(this.getClass().getSimpleName());

            HelloPayload payload = (HelloPayload) ctx.getPayload();
            log.info("json.generic: ant.multi.listener:[{}:{}]", ctx.taskId(), Jackson.toJSONString(payload));
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HelloPayload implements Serializable {

        private static final long serialVersionUID = -8181543765373501685L;

        private Long id;
        private String name;
        private Integer age;
    }

    public static void sleep(final long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
