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
package com.photowey.juc.in.action.jdk.visibility;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * {@code MemoryVisibility}
 *
 * @author photowey
 * @date 2021/12/01
 * @since 1.0.0
 */
@Slf4j
public class MemoryVisibility {

    public static void main(String[] args) {

        // v1();
        // the visibility-task field:visible is:[true] -- and not print-log

        // v2();
        // the visibility-task field:visible is:[true]
        // ---- main-thread run in while-if ----

        // v3();
        // the visibility-task field:visible is:[true] -- and not print-log

        // v4();
        // I am main thread
        // ---- main-thread run in while-if ----

        // v5();
        // I am main thread
        // ---- main-thread run in while-if ----

        // v6();
        // ---- main-thread run in while-if ----
    }

    private static void v1() {
        VisibilityTask task = new VisibilityTask();
        task.start();

        while (true) {
            if (task.isVisible()) {
                log.info("---- main-thread run in while-if ----");
            }
        }
    }

    private static void v2() {
        VisibilityTask task = new VisibilityTask();
        task.start();

        while (true) {
            for (int i = 0; i < 1; i++) {
                new Object();
            }
            if (task.isVisible()) {
                log.info("---- main-thread run in while-if ----");
            }
        }
    }

    private static void v3() {
        VisibilityTask task = new VisibilityTask();
        task.start();

        while (true) {
            new Object();
            if (task.isVisible()) {
                log.info("---- main-thread run in while-if ----");
            }
        }
    }

    private static void v4() {
        VisibilityTask task = new VisibilityTask();
        task.start();

        while (true) {
            System.out.println("I am main thread");
            if (task.isVisible()) {
                log.info("---- main-thread run in while-if ----");
            }
        }
    }

    private static void v5() {
        VisibilityTask task = new VisibilityTask();
        task.start();

        while (true) {
            log.info("I am main thread");
            if (task.isVisible()) {
                log.info("---- main-thread run in while-if ----");
            }
        }
    }

    private static void v6() {
        VisibilityTask task = new VisibilityTask();
        task.start();

        int counter = 0;
        while (true) {
            do {
                counter++;
            } while (counter < 10_000);
            if (task.isVisible()) {
                log.info("---- main-thread run in while-if ----");
            }
        }
    }

    @Slf4j
    public static class VisibilityTask extends Thread {

        @Getter
        private boolean visible;

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }

            this.visible = true;
            log.info("the visibility-task field:visible is:[{}]", this.isVisible());
        }
    }

}
