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
package com.photowey.juc.in.action.stop;

import java.util.concurrent.TimeUnit;

/**
 * {@code StopThread}
 *
 * @author photowey
 * @date 2021/11/18
 * @since 1.0.0
 */
public class StopThread {

    private static boolean stopRequested = false;
    private static volatile boolean stopRequestedVolatile = false;

    public static void main(String[] args) throws InterruptedException {
        // stopV1();
        // stopV2();

        // stopV3();
        // stopV4();
    }

    private static void requestStop() {
        stopRequested = true;
    }

    private static boolean stopRequested() {
        return stopRequested;
    }

    private static synchronized void synchronizedRequestStop() {
        stopRequested = true;
    }

    private static synchronized boolean synchronizedStopRequested() {
        return stopRequested;
    }

    private static void stopV4() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int i = 0;
            while (!stopRequestedVolatile) {
                i++;
            }

        }, "t1");

        t1.start();

        TimeUnit.SECONDS.sleep(1);

        stopRequestedVolatile = true;
    }

    private static void stopV3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int i = 0;
            while (!synchronizedStopRequested()) {
                i++;
            }

        }, "t1");

        t1.start();

        TimeUnit.SECONDS.sleep(1);

        synchronizedRequestStop();
    }

    private static void stopV2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int i = 0;
            while (!stopRequested()) {
                i++;
            }

        }, "t1");

        t1.start();

        TimeUnit.SECONDS.sleep(1);

        requestStop();
    }

    private static void stopV1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int i = 0;
            while (!stopRequested) {
                i++;
            }

            // 被优化
//            if(!stopRequested) {
//                while (true) {
//                    i++;
//                }
//            }


        }, "t1");

        t1.start();

        TimeUnit.SECONDS.sleep(1);

        stopRequested = true;
    }
}
