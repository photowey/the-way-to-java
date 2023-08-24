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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.RejectedExecutionException;

/**
 * {@code ThreadPoolExecutorsTest}
 *
 * @author photowey
 * @date 2021/11/30
 * @since 1.0.0
 */
class ThreadPoolExecutorsTest {

    /**
     * 正常执行
     * 测试空闲线程和核心线程
     * 当空闲线程被启用:
     * - 空闲线程和核心线程都会从队工作队列中-获取任务(随机)
     */
    @Test
    void testThreadPoolExecutorNormal() {
        ThreadPoolExecutors threadPoolExecutors = new ThreadPoolExecutors();
        threadPoolExecutors.createThreadPoolExecutorNormal(
                1, 2, 3L, 1, 3);
        /**
         * [t1] INFO  - execute the runnable-task,the task-no is:1
         * [t2] INFO  - execute the runnable-task,the task-no is:3
         * [t1] INFO  - execute the runnable-task,the task-no is:2
         * -- t2 会参与执行任务
         */
    }

    /**
     * 正常执行
     * 测试空闲线程和核心线程
     * -- 测试-不启用空闲线程
     */
    @Test
    void testThreadPoolExecutorNormalNotUseMax() {
        ThreadPoolExecutors threadPoolExecutors = new ThreadPoolExecutors();
        threadPoolExecutors.createThreadPoolExecutorNormal(
                1, 2, 3L, 1, 2);
        /**
         * [t1] INFO  - execute the runnable-task,the task-no is:1
         * [t1] INFO  - execute the runnable-task,the task-no is:2
         * -- t2 不会参与执行任务
         */
    }

    /**
     * 执行拒绝策略
     */
    @Test
    void testThreadPoolExecutorAbort() {
        // 执行拒绝策略抛异常
        Assertions.assertThrows(RejectedExecutionException.class, () -> {
            ThreadPoolExecutors threadPoolExecutors = new ThreadPoolExecutors();
            threadPoolExecutors.createThreadPoolExecutorNormal(
                    1, 2, 3L, 1, 4);
        });

        /**
         * [t2] INFO  - execute the runnable-task,the task-no is:3
         * [t2] INFO  - execute the runnable-task,the task-no is:2
         * [t1] INFO  - execute the runnable-task,the task-no is:1
         */
    }

    /**
     * 空闲线程-存活时间
     */
    @Test
    void testThreadPoolExecutorAliveTime() {
        ThreadPoolExecutors threadPoolExecutors = new ThreadPoolExecutors();
        threadPoolExecutors.createThreadPoolExecutorNormalAliveTime(
                1, 2, 3L, 1, 4);

        /**
         * [t1] INFO  - execute the runnable-task,the task-no is:1
         * [t2] INFO  - execute the runnable-task,the task-no is:3
         * [t1] INFO  - execute the runnable-task,the task-no is:2
         * [t1] INFO  - execute the runnable-task,the task-no is:4
         * [t1] INFO  - execute the runnable-task,the task-no is:4,the executor active-thread-count is:1  ## idle:t2 在 3L 后 消亡 - 由 t1 执行-task4
         */
    }
}