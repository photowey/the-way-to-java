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

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@code AliveTimeRunnableTask}
 *
 * @author photowey
 * @date 2021/11/30
 * @since 1.0.0
 */
@Data
@Slf4j
public class AliveTimeRunnableTask implements Runnable {

    private int taskNo;
    ThreadPoolExecutor executor;

    public AliveTimeRunnableTask(int taskNo, ThreadPoolExecutor executor) {
        this.taskNo = taskNo;
        this.executor = executor;
    }

    @Override
    public void run() {
        log.info("execute the runnable-task,the task-no is:{}", taskNo);
        if (1 == taskNo) {
            try {
                // 保证 t2 执行 task3
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
        } else if (2 == taskNo) {
            try {
                // 这个时候 - task2 被 t1 执行 - keepAliveTime 秒后死亡
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
            }
        } else if (3 == taskNo) {
            try {
                // 这个时候 - task3 被 t2 执行 - keepAliveTime 秒后死亡
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
        } else {
            // task
            log.info("execute the runnable-task,the task-no is:{},the executor active-thread-count is:{}", taskNo, this.executor.getActiveCount());
        }
    }
}
