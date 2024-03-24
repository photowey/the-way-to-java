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
