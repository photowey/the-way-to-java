/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.delayqueue.in.action.netty;

import com.alibaba.fastjson2.JSON;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * {@code SharedNettyDelayQueueTest}
 *
 * @author photowey
 * @date 2023/03/26
 * @since 1.0.0
 */
@Slf4j
class SharedNettyDelayQueueTest {

    private SharedNettyDelayQueue delayQueue;

    @BeforeEach
    void init() {
        this.delayQueue = new SharedNettyDelayQueue();
    }

    @Test
    @SneakyThrows
    void testDelayMillis() {
        OrderData order = OrderData.builder().orderId(UUID.randomUUID().toString()).build();
        OrderDelayListener listener = new OrderDelayListener(order);
        log.info("-----------------------------------------start");
        this.delayQueue.delayMillis(listener, SharedNettyDelayQueue.DEFAULT_TICK_DURATION * 5);
        log.info("-----------------------------------------submit");

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    @SneakyThrows
    void testDelaySeconds() {
        OrderData order = OrderData.builder().orderId(UUID.randomUUID().toString()).build();
        OrderDelayListener listener = new OrderDelayListener(order);
        log.info("-----------------------------------------start");
        this.delayQueue.delaySeconds(listener, 2L);
        log.info("-----------------------------------------submit");

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    @SneakyThrows
    void testDelayAt() {
        OrderData order = OrderData.builder().orderId(UUID.randomUUID().toString()).build();
        OrderDelayListener listener = new OrderDelayListener(order);
        log.info("-----------------------------------------start");
        this.delayQueue.delayAt(listener, 1L, TimeUnit.MINUTES);
        log.info("-----------------------------------------submit");

        TimeUnit.SECONDS.sleep(63);
    }

    @Test
    @SneakyThrows
    void testFutureAt() {
        OrderData order = OrderData.builder().orderId(UUID.randomUUID().toString()).build();
        OrderDelayListener listener = new OrderDelayListener(order);
        LocalDateTime now = LocalDateTime.now();
        log.info("-----------------------------------------start");
        this.delayQueue.futureAt(listener, now.plusSeconds(5));
        log.info("-----------------------------------------submit");

        TimeUnit.SECONDS.sleep(8);
    }

    @Slf4j
    public static class OrderDelayListener extends AbstractSharedNettyDelayedQueueListener<OrderData> {

        public OrderDelayListener(OrderData order) {
            this.buildTask(order);
        }

        @Override
        public void execute(Task<OrderData> task) {
            log.info("Listen the delay queue task:[{}]", JSON.toJSONString(task));
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderData implements Serializable {

        private static final long serialVersionUID = 5321902215654280367L;

        private String orderId;
    }

}