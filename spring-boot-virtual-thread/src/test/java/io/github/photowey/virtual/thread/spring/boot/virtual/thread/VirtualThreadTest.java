package io.github.photowey.virtual.thread.spring.boot.virtual.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.VirtualThreadTaskExecutor;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

/**
 * {@code VirtualThreadTest}
 *
 * @author photowey
 * @date 2023/11/26
 * @since 1.0.0
 */
@Slf4j
@SpringBootTest
class VirtualThreadTest {

    @Test
    void testVirtualThread() {
        VirtualThreadTaskExecutor executor = new VirtualThreadTaskExecutor("ptw-");
        for (int i = 0; i < 1000; i++) {
            executor.execute(() -> {
                log.info("the thread.id:[{},{}]", Thread.currentThread().isVirtual(), Thread.currentThread().threadId());
            });
        }

        await().atLeast(Duration.ofSeconds(3));
    }

}
