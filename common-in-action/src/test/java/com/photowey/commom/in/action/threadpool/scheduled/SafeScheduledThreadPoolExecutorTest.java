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
package com.photowey.commom.in.action.threadpool.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@code SafeScheduledThreadPoolExecutorTest}
 *
 * @author photowey
 * @date 2023/07/11
 * @since 1.0.0
 */
@Slf4j
class SafeScheduledThreadPoolExecutorTest {

    @Test
    void testSafeExecutorScheduleWithFixedDelay() {
        SafeScheduledThreadPoolExecutor safeExecutor = new SafeScheduledThreadPoolExecutor(1);

        safeExecutor.scheduleWithFixedDelay(() -> {
            log.info("----------------------------------------------------------------safeExecutor: Prepare do.task");
            this.doTask();
            log.info("----------------------------------------------------------------safeExecutor: Post do.task");
        }, 1, 3, TimeUnit.SECONDS, (ex) -> {
            log.error("----------------------------------------------------------------safeExecutor: Occur exception", ex);
        });

        log.info("----------------------------------------------------------------main: blocking...");

        this.sleepOn(100_000L);
        safeExecutor.shutdownNow();
    }

    //@Test
    void testSafeExecutorBadScheduleWithFixedDelay() {
        SafeScheduledThreadPoolExecutor safeExecutor = new SafeScheduledThreadPoolExecutor(1);

        safeExecutor.scheduleWithFixedDelay(() -> {
            log.info("----------------------------------------------------------------safeExecutor: Prepare do.task");
            this.doTask();
            log.info("----------------------------------------------------------------safeExecutor: Post do.task");
        }, 1, 3, TimeUnit.SECONDS, (ex) -> {
            throw new RuntimeException(ex);
        });

        log.info("----------------------------------------------------------------main: blocking...");

        this.sleepOn(5_000L);
        safeExecutor.submit(this::doTask);

        this.sleepOn(16_000L);

        safeExecutor.shutdownNow();
    }

    //@Test
    void testExecutorScheduleWithFixedDelay() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleWithFixedDelay(() -> {
            log.info("----------------------------------------------------------------executor: Prepare do.task");
            this.doTask();
            log.info("----------------------------------------------------------------executor: Post do.task");
        }, 1, 3, TimeUnit.SECONDS);

        log.info("----------------------------------------------------------------main: blocking...");

        this.sleepOn(16_000L);

        executor.shutdownNow();
    }

    // ----------------------------------------------------------------

    //@Test
    void testSafeExecutorScheduleAtFixedRate() {
        SafeScheduledThreadPoolExecutor safeExecutor = new SafeScheduledThreadPoolExecutor(1);

        safeExecutor.scheduleAtFixedRate(() -> {
            log.info("----------------------------------------------------------------safeExecutor: Prepare do.task");
            this.doTask();
            log.info("----------------------------------------------------------------safeExecutor: Post do.task");
        }, 1, 3, TimeUnit.SECONDS, (ex) -> {
            log.error("----------------------------------------------------------------safeExecutor: Occur exception", ex);
        });

        log.info("----------------------------------------------------------------main: blocking...");

        this.sleepOn(16_000L);
        safeExecutor.shutdownNow();
    }

    //@Test
    void testSafeExecutorBadScheduleAtFixedRate() {
        SafeScheduledThreadPoolExecutor safeExecutor = new SafeScheduledThreadPoolExecutor(1);

        safeExecutor.scheduleAtFixedRate(() -> {
            log.info("----------------------------------------------------------------safeExecutor: Prepare do.task");
            this.doTask();
            log.info("----------------------------------------------------------------safeExecutor: Post do.task");
        }, 1, 3, TimeUnit.SECONDS, (ex) -> {
            throw new RuntimeException(ex);
        });

        log.info("----------------------------------------------------------------main: blocking...");

        this.sleepOn(16_000L);

        safeExecutor.shutdownNow();
    }

    //@Test
    void testExecutorScheduleAtFixedRate() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(() -> {
            log.info("----------------------------------------------------------------executor: Prepare do.task");
            this.doTask();
            log.info("----------------------------------------------------------------executor: Post do.task");
        }, 1, 3, TimeUnit.SECONDS);

        log.info("----------------------------------------------------------------main: blocking...");

        this.sleepOn(16_000L);

        executor.shutdownNow();
    }

    // ----------------------------------------------------------------

    private void sleepOn(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception ignored) {
        }
    }

    private void doTask() {
        Random random = new Random(100);
        if (random.nextInt() % 2 == 0) {
            throw new RuntimeException("触发异常啦");
        }
    }
}