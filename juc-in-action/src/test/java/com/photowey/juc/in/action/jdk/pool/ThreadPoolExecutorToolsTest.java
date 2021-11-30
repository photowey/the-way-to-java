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
package com.photowey.juc.in.action.jdk.pool;

import org.junit.jupiter.api.Test;

/**
 * {@code ThreadPoolExecutorToolsTest}
 *
 * @author photowey
 * @date 2021/11/30
 * @since 1.0.0
 */
class ThreadPoolExecutorToolsTest {

    @Test
    void testFixedThreadPool() {
        ThreadPoolExecutorTools threadPoolExecutorTools = new ThreadPoolExecutorTools();
        threadPoolExecutorTools.newFixedThreadPool(1, 3);
    }

    @Test
    void testCachedThreadPool() {
        ThreadPoolExecutorTools threadPoolExecutorTools = new ThreadPoolExecutorTools();
        threadPoolExecutorTools.newCachedThreadPool(3);
    }

    @Test
    void testSingleThreadExecutor() {
        ThreadPoolExecutorTools threadPoolExecutorTools = new ThreadPoolExecutorTools();
        threadPoolExecutorTools.newSingleThreadExecutor();
    }

    @Test
    void testExecuteTask() {
        ThreadPoolExecutorTools threadPoolExecutorTools = new ThreadPoolExecutorTools();
        threadPoolExecutorTools.executeTask(1, 4);
    }

    @Test
    void testSubmitTask() {
        ThreadPoolExecutorTools threadPoolExecutorTools = new ThreadPoolExecutorTools();
        threadPoolExecutorTools.submitTask(1, 4);
    }

    @Test
    void testInvokeAll() {
        ThreadPoolExecutorTools threadPoolExecutorTools = new ThreadPoolExecutorTools();
        threadPoolExecutorTools.invokeAll(1, 4);
    }

    @Test
    void testInvokeAny() {
        ThreadPoolExecutorTools threadPoolExecutorTools = new ThreadPoolExecutorTools();
        threadPoolExecutorTools.invokeAny(1, 4);
    }

    @Test
    void testSchedule() {
        ThreadPoolExecutorTools threadPoolExecutorTools = new ThreadPoolExecutorTools();
        threadPoolExecutorTools.schedule(1);
    }

    @Test
    void testScheduleAtFixedRate() {
        ThreadPoolExecutorTools threadPoolExecutorTools = new ThreadPoolExecutorTools();
        threadPoolExecutorTools.scheduleAtFixedRate(1);
    }

    @Test
    void testScheduleWithFixedDelay() {
        ThreadPoolExecutorTools threadPoolExecutorTools = new ThreadPoolExecutorTools();
        threadPoolExecutorTools.scheduleWithFixedDelay(1);
    }
}