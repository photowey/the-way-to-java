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

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * {@code SynchronousQueuesTest}
 *
 * @author photowey
 * @date 2021/11/30
 * @since 1.0.0
 */
class SynchronousQueuesTest {

    @Test
    void testSynchronousQueue() {
        SynchronousQueues synchronousQueues = new SynchronousQueues();
        synchronousQueues.walk();

        /**
         * [t1] INFO  - t1 put task start
         * [t2] INFO  - t2 take task start
         * [t1] INFO  - t1 put task end
         * [t2] INFO  - t2 take task end,value is:task1
         * [t3] INFO  - t3 put task start
         * -- ---------------------------------------------
         * # t3 blocking
         */
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
        }
    }

}