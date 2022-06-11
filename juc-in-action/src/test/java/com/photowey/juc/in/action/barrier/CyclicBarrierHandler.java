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
package com.photowey.juc.in.action.barrier;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code CyclicBarrierHandler}
 *
 * @author photowey
 * @date 2021/11/29
 * @since 1.0.0
 */
@Slf4j
public class CyclicBarrierHandler {

    private final CyclicBarrier cyclicBarrier;

    public CyclicBarrierHandler(int parties) {
        this.cyclicBarrier = new CyclicBarrier(parties, () -> log.info("the count == 0,now"));
    }

    public void walk(int parties) {

        AtomicInteger counter = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(parties, (r) -> new Thread(r, "t" + counter.getAndIncrement()));

        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> {
                log.info("--- thread:{} started ---", Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                    this.cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                }

                log.info("--- thread:{} working now ---", Thread.currentThread().getName());
            });

            executorService.submit(() -> {
                log.info("--- thread:tt:{} started ---", Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(3);
                    this.cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                }

                log.info("--- thread:tt:{} working now ---", Thread.currentThread().getName());
            });

        }

        executorService.shutdown();
    }
}
