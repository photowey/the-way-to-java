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
package com.photowey.juc.in.action.pool;

import com.photowey.juc.in.action.pool.exception.NamedException;
import com.photowey.juc.in.action.pool.queue.NamedBlockingQueue;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * {@code NamedThreadPoolTest}
 *
 * @author photowey
 * @date 2021/11/29
 * @since 1.0.0
 */
@Slf4j
class NamedThreadPoolTest {

    @Test
    @SneakyThrows
    void testNamedThreadPool() {
        NamedThreadPool namedThreadPool = new NamedThreadPool(2, 2);
        for (int i = 0; i < 5; i++) {
            int index = i;
            namedThreadPool.submit(new NamedTask("task" + (index + 1), 200L));
        }

        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * 队列满了-直接拒绝-抛弃
     */
    @Test
    @SneakyThrows
    void testNamedThreadPoolRejectedStrategyWait() {
        NamedThreadPool namedThreadPool = new NamedThreadPool(2, 2, NamedBlockingQueue::put);
        for (int i = 0; i < 5; i++) {
            int index = i;
            namedThreadPool.submit(new NamedTask("task" + (index + 1), 200L));
        }

        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * 队列满了-直接拒绝-抛弃
     */
    @Test
    @SneakyThrows
    void testNamedThreadPoolRejectedStrategyAbort() {
        // 01:24:47.105 [main] INFO com.photowey.juc.in.action.pool.NamedThreadPoolTest - discard the target:[task5] task, when the taskQueue is full
        NamedThreadPool namedThreadPool = new NamedThreadPool(2, 2,
                (taskQueue, target) -> log.info("discard the target:[{}] task, when the taskQueue is full", target.getName()));
        for (int i = 0; i < 5; i++) {
            int index = i;
            namedThreadPool.submit(new NamedTask("task" + (index + 1), 200L));
        }

        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * 队列满了-调用线程执行
     */
    @Test
    @SneakyThrows
    void testNamedThreadPoolRejectedStrategyInvokerRun() {
        // 01:23:32.869 [main] INFO com.photowey.juc.in.action.pool.NamedTask - the task:task5 execution now...
        NamedThreadPool namedThreadPool = new NamedThreadPool(2, 2, (taskQueue, target) -> target.run());
        for (int i = 0; i < 5; i++) {
            int index = i;
            namedThreadPool.submit(new NamedTask("task" + (index + 1), 200L));
        }

        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * 队列满了-抛出异常
     */
    @Test
    @SneakyThrows
    void testNamedThreadPoolRejectedStrategyThrowException() {
        Assertions.assertThrows(NamedException.class, () -> {
            NamedThreadPool namedThreadPool = new NamedThreadPool(2, 2, (taskQueue, target) -> {
                throw new NamedException("the taskQueue is full, and do reject strategy");
            });
            for (int i = 0; i < 5; i++) {
                int index = i;
                namedThreadPool.submit(new NamedTask("task" + (index + 1), 200L));
            }

            TimeUnit.SECONDS.sleep(5);
        });

    }

}