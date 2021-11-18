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
package com.photowey.juc.in.action.sync;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * {@code SleepTest}
 *
 * @author photowey
 * @date 2021/11/18
 * @since 1.0.0
 */
@Slf4j
class SleepTest {

    public static Object lock = new Object();
    public static boolean condition = false;

    @Test
    void testSleep() {
        new Thread(() -> {
            synchronized (lock) {
                if (condition) {
                    log.info("--- Jack --- the condition:true");
                } else {
                    try {
                        log.info("--- Jack --- the condition:false, do sleep(5_000)");
                        Thread.sleep(5_000);
                    } catch (InterruptedException e) {
                    }
                }

                if (condition) {
                    log.info("--- Jack --- after sleep, the condition:true");
                } else {
                    log.info("--- Jack --- after sleep, the condition:false");
                }
            }

        }, "Jack").start();

        for (int i = 0; i < 10; i++) {
            final int index = (i + 1);
            new Thread(() -> {

                synchronized (lock) {
                    log.info("--- the Other --- do work:{}", index);
                }

            }, "Other").start();
        }

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
        }
        new Thread(() -> {
            synchronized (lock) {
                condition = true;
                log.info("--- Boss --- the condition:true");
            }
        }, "Boss").start();

//        new Thread(() -> {
//            condition = true;
//            log.info("--- Boss --- the condition:true");
//        }, "Boss").start();

        try {
            Thread.sleep(6_000);
        } catch (InterruptedException e) {
        }
    }

}
