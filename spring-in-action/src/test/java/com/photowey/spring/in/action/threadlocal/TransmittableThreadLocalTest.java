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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    private ExecutorService executorService;

    @BeforeEach
    public void init() {
        executorService = Executors.newFixedThreadPool(2);
    }

    @AfterEach
    public void shutdown() {
        executorService.shutdown();
    }

    @Test
    @SneakyThrows
    void testHelloTransmittableThreadLocal() {
        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
        context.set("value-set-in-parent");
        executorService.execute(() -> {
            String value = context.get();
            log.info("the sub thread get value is:{}", value);
            Assertions.assertEquals("value-set-in-parent", value);
        });

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    @SneakyThrows
    void testTtl() {
        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
        context.set("value-set-in-parent");

        Runnable ttlRunnable = TtlRunnable.get(() -> {
            String value = context.get();
            log.info("the sub thread:[ttlRunnable] get value is:{}", value);
            Assertions.assertEquals("value-set-in-parent", value);
        });

        Runnable ttlRunnable2 = TtlRunnable.get(() -> {
            String value = context.get();
            log.info("the sub thread:[ttlRunnable2] get value is:{}", value);
            Assertions.assertEquals("value-set-in-parent", value);
        });

        Runnable ttlRunnable3 = TtlRunnable.get(() -> {
            String value = context.get();
            log.info("the sub thread:[ttlRunnable3] get value is:{}", value);
            Assertions.assertEquals("value-set-in-parent", value);
        });

        executorService.submit(ttlRunnable);
        executorService.submit(ttlRunnable2);
        executorService.submit(ttlRunnable3);

        TimeUnit.SECONDS.sleep(3);
    }
}
