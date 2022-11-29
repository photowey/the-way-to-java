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
package com.photowey.spring.in.action.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * {@code TransmittableThreadLocalTest}
 *
 * @author photowey
 * @date 2022/03/18
 * @since 1.0.0
 */
@Slf4j
class TransmittableThreadLocalTest {

    private static final TransmittableThreadLocal<String> CONTEXT = new TransmittableThreadLocal<>();

    private ExecutorService executorService;

    @BeforeEach
    public void init() {
        executorService = Executors.newFixedThreadPool(2);
    }

    @AfterEach
    public void shutdown() {
        CONTEXT.remove();
        executorService.shutdown();
    }

    @Test
    @SneakyThrows
    void testHelloTransmittableThreadLocal() {
        CONTEXT.set("value-set-in-parent");
        executorService.execute(() -> {
            String value = CONTEXT.get();
            log.info("the sub thread get value is:{}", value);
            Assertions.assertEquals("value-set-in-parent", value);
        });

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    @SneakyThrows
    void testTtl() {
        CONTEXT.set("value-set-in-parent");

        Runnable t1 = TtlRunnable.get(() -> {
            String value = CONTEXT.get();
            log.info("the sub thread:[t1] get value is:{}", value);
            Assertions.assertEquals("value-set-in-parent", value);
        });

        Runnable t2 = TtlRunnable.get(() -> {
            String value = CONTEXT.get();
            log.info("the sub thread:[t2] get value is:{}", value);
            Assertions.assertEquals("value-set-in-parent", value);
        });

        Runnable t3 = TtlRunnable.get(() -> {
            String value = CONTEXT.get();
            log.info("the sub thread:[t3] get value is:{}", value);
            Assertions.assertEquals("value-set-in-parent", value);
        });

        executorService.submit(t1);
        executorService.submit(t2);
        executorService.submit(t3);

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    @SneakyThrows
    void testTtl_v2() {
        CONTEXT.set("China");

        Runnable t1 = TtlRunnable.get(() -> {
            Thread thread = Thread.currentThread();
            log.info("{}-------------------------------- start", thread.getName());
            String countryCode = CONTEXT.get();
            log.info("{}-------------------------------- value={}", thread.getName(), countryCode);
            CONTEXT.set("US");
            log.info("{}-------------------------------- end", thread.getName());
        });

        Runnable t2 = TtlRunnable.get(() -> {
            Thread thread = Thread.currentThread();
            log.info("{}-------------------------------- start", thread.getName());
            String countryCode = CONTEXT.get();
            log.info("{}-------------------------------- value={}", thread.getName(), countryCode);
            log.info("{}-------------------------------- end", thread.getName());
        });

        CompletableFuture<Void> future = CompletableFuture.runAsync(t1, executorService).thenRunAsync(t2, executorService);
        future.get();

        String countryCode = CONTEXT.get();

        log.info("{}-------------------------------- value={}", "main", countryCode);

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    @SneakyThrows
    void testTtl_v3() {
        ExecutorService executor = TtlExecutors.getTtlExecutorService(executorService);

        CONTEXT.set("China");

        Runnable t1 = () -> {
            Thread thread = Thread.currentThread();
            log.info("{}-------------------------------- start", thread.getName());
            String countryCode = CONTEXT.get();
            log.info("{}-------------------------------- value={}", thread.getName(), countryCode);
            CONTEXT.set("US");
            log.info("{}-------------------------------- end", thread.getName());
        };

        Runnable t2 = () -> {
            Thread thread = Thread.currentThread();
            log.info("{}-------------------------------- start", thread.getName());
            String countryCode = CONTEXT.get();
            log.info("{}-------------------------------- value={}", thread.getName(), countryCode);
            log.info("{}-------------------------------- end", thread.getName());
        };

        CompletableFuture<Void> future = CompletableFuture.runAsync(t1, executor).thenRunAsync(t2, executor);
        future.get();

        String countryCode = CONTEXT.get();

        log.info("{}-------------------------------- value={}", "main", countryCode);

        TimeUnit.SECONDS.sleep(3);

        CONTEXT.remove();
        executor.shutdownNow();
    }
}
