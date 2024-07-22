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
package com.photowey.spring.project.infras.in.action.rust.optional;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * {@code CompletableFutureTest}
 *
 * @author weichangjun
 * @version 1.0.0
 * @since 2024/07/22
 */
@Slf4j
class CompletableFutureTest {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Test
    void testCompletableFutureCancel() {
        CompletableFuture<Integer> promise = this.run();
        sleep(5_000);
        promise.cancel(true);
        scheduler.shutdownNow();
    }

    private <T> CompletableFuture<T> run() {
        CompletableFuture<T> promise = new CompletableFuture<>();

        ScheduledFuture<?> scheduledTask = scheduler.scheduleAtFixedRate(() -> {
            if (promise.isCancelled()) {
                return;
            }
            log.info("{} task is running...", LocalDateTime.now());
        }, 0, 1, TimeUnit.SECONDS);

        promise.whenComplete((result, error) -> {
            scheduledTask.cancel(true);
            if (error != null && !(error instanceof CancellationException)) {
                throw new RuntimeException(error);
            }
        });

        return promise;
    }

    public static void sleep(final long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
