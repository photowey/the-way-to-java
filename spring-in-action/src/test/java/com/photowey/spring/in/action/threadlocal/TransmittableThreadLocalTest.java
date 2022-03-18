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
