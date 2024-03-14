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

import io.github.photowey.redisson.delayed.queue.in.action.App;
import io.github.photowey.redisson.delayed.queue.in.action.core.task.RedissonDelayedTask;
import io.github.photowey.redisson.delayed.queue.in.action.event.RedissonDelayedTaskEvent;
import io.github.photowey.redisson.delayed.queue.in.action.queue.RedissonDelayedQueue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * {@code RedissonDelayedQueueTests}
 *
 * @author photowey
 * @date 2024/03/14
 * @since 1.0.0
 */
@SpringBootTest(classes = {App.class, RedissonDelayedQueueTests.RedissonDelayedQueueEventListenerConfigure.class})
class RedissonDelayedQueueTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testRedissonDelayedQueue() {
        RedissonDelayedQueue delayedQueue = this.applicationContext.getBean(RedissonDelayedQueue.class);
        for (int i = 0; i < 10; i++) {
            RedissonDelayedTask<Serializable> task = RedissonDelayedTask.builder()
                    .taskId(String.valueOf((i + 1)))
                    .payload(String.valueOf((i + 1)))
                    .delayed((i + 1))
                    .timeUnit(TimeUnit.SECONDS.name())
                    .build();

            delayedQueue.offer(task);
        }

        sleep(15_000);
    }

    @Test
    void testRedissonDelayedQueue_payload() {
        RedissonDelayedQueue delayedQueue = this.applicationContext.getBean(RedissonDelayedQueue.class);

        for (int i = 0; i < 10; i++) {
            HelloPayload payload = HelloPayload.builder()
                    .id(1760223724043808770L)
                    .name("王大锤" + ((i + 1)))
                    .age(10086)
                    .build();

            RedissonDelayedTask<Serializable> task = RedissonDelayedTask.builder()
                    .taskId(String.valueOf((i + 1)))
                    .payload(payload)
                    .delayed((i + 1) * 5)
                    .timeUnit(TimeUnit.SECONDS.name())
                    .build();

            delayedQueue.offer(task);
        }

        sleep(65_000);
    }

    @Configuration
    public static class RedissonDelayedQueueEventListenerConfigure {

        @Bean
        public RedissonDelayedQueueEventListener redissonDelayedQueueEventListener() {
            return new RedissonDelayedQueueEventListener();
        }
    }

    @Slf4j
    public static class RedissonDelayedQueueEventListener implements ApplicationListener<RedissonDelayedTaskEvent> {

        @Override
        public void onApplicationEvent(RedissonDelayedTaskEvent event) {
            RedissonDelayedTask<Serializable> task = event.getTask();
            log.info("receive.redisson.delayed.task.event:[{}:{}]", task.getTaskId(), task.getPayload());

            //HelloPayload payload1 = (HelloPayload) task.getPayload();
            //HelloPayload payload2 = task.getGenericPayload();

            //log.info("json::---receive.redisson.delayed.task.event:[{}:{}]", task.getTaskId(), JSON.toJSONString(payload1));
            //log.info("json::generic::---receive.redisson.delayed.task.event:[{}:{}]", task.getTaskId(), JSON.toJSONString(payload2));
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
