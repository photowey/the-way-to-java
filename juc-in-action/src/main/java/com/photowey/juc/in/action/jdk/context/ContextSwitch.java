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
package com.photowey.juc.in.action.jdk.context;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code ContextSwitch}
 *
 * @author photowey
 * @date 2021/12/01
 * @since 1.0.0
 */
public class ContextSwitch {

    public static void main(String[] args) {
        Runnable contextTask = new ContextTask();
        for (int i = 0; i < 100; i++) {
            new Thread(contextTask, "t" + (i + 1)).start();
        }
    }

    @Slf4j
    public static class ContextTask implements Runnable {

        @Getter
        // private volatile int counter;
        private AtomicInteger counter = new AtomicInteger();

        @Override
        public void run() {
            for (int i = 0; i < 10_000; i++) {
                // the context-switch field:counter is:[999918]
                // log.info("the context-switch field:counter is:[{}]", counter++);

                log.info("the context-switch field:counter is:[{}]", counter.incrementAndGet());
                // the context-switch field:counter is:[1000000]
            }

        }
    }
}
