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
package com.photowey.commom.in.action.timewheel;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@code TimeWheelTest}
 *
 * @author photowey
 * @date 2023/11/03
 * @since 1.0.0
 */
public class TimeWheelTest {

    @Test
    public void testTimeWheel() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        TimeWheel timeWheel = new TimeWheel(2, 1000);

        TimerTask task = new TimerTask(() -> {
            System.out.println("Task executed!");
            latch.countDown();
        }, 5000);
        timeWheel.addTask(task);

        TimeUnit.SECONDS.sleep(6);
        assertTrue(latch.getCount() == 0);
    }
}
