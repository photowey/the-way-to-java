/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.juc.in.action.samephore;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * {@code SemaphoreHandler}
 *
 * @author photowey
 * @date 2021/11/29
 * @since 1.0.0
 */
@Slf4j
public class SemaphoreHandler {

    /**
     * {@link Semaphore}
     * 来限制对资源访问的线程的上限
     */
    private Semaphore semaphore;

    public SemaphoreHandler(int permits) {
        // permits 线程上线
        this.semaphore = new Semaphore(permits);
    }

    @SneakyThrows
    public void walk() {
        for (int i = 0; i < 15; i++) {
            new Thread(() -> {
                try {
                    this.semaphore.acquire();
                } catch (InterruptedException e) {
                }
                try {
                    log.info("--- thread:{} started ---", Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                    log.info("--- thread:{} end ---", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                } finally {
                    this.semaphore.release();
                }

            }, "t" + (i + 1)).start();
        }
    }
}
