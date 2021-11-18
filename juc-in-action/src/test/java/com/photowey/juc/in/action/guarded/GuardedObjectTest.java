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
package com.photowey.juc.in.action.guarded;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * {@code GuardedObjectTest}
 *
 * @author photowey
 * @date 2021/11/18
 * @since 1.0.0
 */
@Slf4j
class GuardedObjectTest {

    @Test
    void testGuardedObject() {
        GuardedObject guardedObject = new GuardedObject();

        new Thread(() -> {
            String action = Operate.doAction();
            guardedObject.setResponse(action);
            log.info("the notifier set response successfully...");
        }, "notifier").start();

        log.info("the main wait the notifier:set response");

        String response = guardedObject.getResponse();
        log.info("the response is:{}", response);
        // the response is:null

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
        }
    }

    @Test
    void testGuardedObjectTimeout() {
        GuardedObjectTimeout guardedObject = new GuardedObjectTimeout();

        Thread notifier = new Thread(() -> {
            String action = Operate.doAction();
            guardedObject.setResponse(action);
            log.info("the notifier set response successfully...");
        }, "notifier");
        notifier.start();

        log.info("the main wait the notifier:set response");

        String response = guardedObject.getResponse(2_000);
        log.info("the response is:{}", response);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
        }

    }

}