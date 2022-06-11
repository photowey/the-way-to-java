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
package com.photowey.juc.in.action.latch;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * {@code CountDownLatcher}
 *
 * @author photowey
 * @date 2021/11/29
 * @since 1.0.0
 */
@Slf4j
public class CountDownLatcher {

    private final CountDownLatch latch;

    public CountDownLatcher(int count) {
        this.latch = new CountDownLatch(count);
    }

    public void walk(int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            final int index = i;
            new Thread(() -> {
                log.info("--- the thread:t{} started ---", (index + 1));
                try {
                    TimeUnit.SECONDS.sleep((index + 1));
                } catch (InterruptedException e) {
                }
                this.latch.countDown();
                log.info("--- the thread:t{} end,the count is:{} ---", +(index + 1), this.latch.getCount());
            }, "t" + (index + 1)).start();
        }

        log.info("--- the thread:main start wait ---");
        this.latch.await();
        log.info("--- the thread:main end wait ---");
    }
}
