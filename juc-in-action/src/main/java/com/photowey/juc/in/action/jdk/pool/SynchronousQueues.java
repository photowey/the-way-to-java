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
package com.photowey.juc.in.action.jdk.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * {@code SynchronousQueues}
 *
 * @author photowey
 * @date 2021/11/30
 * @since 1.0.0
 */
@Slf4j
public class SynchronousQueues {

    public void walk() {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        new Thread(() -> {
            log.info("t1 put task start");
            try {
                synchronousQueue.put("task1");
            } catch (InterruptedException e) {
            }
            log.info("t1 put task end");
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }

        new Thread(() -> {
            log.info("t2 take task start");
            try {
                String task = synchronousQueue.take();
                log.info("t2 take task end,value is:{}", task);
            } catch (InterruptedException e) {
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }

        new Thread(() -> {
            log.info("t3 put task start");
            try {
                synchronousQueue.put("task1");
            } catch (InterruptedException e) {
            }
            log.info("t3 put task end");
        }, "t3").start();

        // blocking

    }

}
