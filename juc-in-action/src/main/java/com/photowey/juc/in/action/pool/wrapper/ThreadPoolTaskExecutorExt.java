/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.juc.in.action.pool.wrapper;

import org.springframework.core.task.TaskDecorator;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Map;
import java.util.concurrent.*;

/**
 * {@code ThreadPoolTaskExecutorExt}
 *
 * @author photowey
 * @date 2022/10/14
 * @since 1.0.0
 */
public class ThreadPoolTaskExecutorExt extends ThreadPoolTaskExecutor {

    private int queueCapacity = Integer.MAX_VALUE;
    private boolean allowCoreThreadTimeOutx = false;

    @Nullable
    private TaskDecorator taskDecorator;
    @Nullable
    private ThreadPoolExecutorExt threadPoolExecutor;

    private final Map<Runnable, Object> decoratedTaskMap;

    public ThreadPoolTaskExecutorExt() {
        this.decoratedTaskMap = new ConcurrentReferenceHashMap<>(16, ConcurrentReferenceHashMap.ReferenceType.WEAK);
    }

    @Override
    protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        BlockingQueue<Runnable> queue = this.createQueue(this.queueCapacity);
        ThreadPoolExecutorExt executor;
        if (this.taskDecorator != null) {
            executor = new ThreadPoolExecutorExt(
                    this.getCorePoolSize(),
                    this.getMaxPoolSize(),
                    this.getKeepAliveSeconds(),
                    TimeUnit.SECONDS,
                    queue,
                    threadFactory,
                    rejectedExecutionHandler
            ) {
                @Override
                public void execute(Runnable command) {
                    Runnable decorated = ThreadPoolTaskExecutorExt.this.taskDecorator.decorate(command);
                    if (decorated != command) {
                        ThreadPoolTaskExecutorExt.this.decoratedTaskMap.put(decorated, command);
                    }

                    super.execute(decorated);
                }
            };
        } else {
            executor = new ThreadPoolExecutorExt(
                    this.getCorePoolSize(),
                    this.getMaxPoolSize(),
                    this.getKeepAliveSeconds(),
                    TimeUnit.SECONDS,
                    queue,
                    threadFactory,
                    rejectedExecutionHandler
            );
        }

        if (this.allowCoreThreadTimeOutx) {
            executor.allowCoreThreadTimeOut(true);
        }

        this.threadPoolExecutor = executor;
        return executor;
    }

    @Override
    public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOutx = allowCoreThreadTimeOut;
        super.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
    }

    @Override
    public void setTaskDecorator(@Nullable TaskDecorator taskDecorator) {
        this.taskDecorator = taskDecorator;
        super.setTaskDecorator(taskDecorator);
    }

    @Override
    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
        super.setQueueCapacity(queueCapacity);
    }

    @Override
    public ThreadPoolExecutor getThreadPoolExecutor() throws IllegalStateException {
        Assert.state(this.threadPoolExecutor != null, "ThreadPoolTaskExecutor not initialized");
        return this.threadPoolExecutor;
    }

    @Override
    protected void cancelRemainingTask(Runnable task) {
        super.cancelRemainingTask(task);
        // Cancel associated user-level Future handle as well
        Object original = this.decoratedTaskMap.get(task);
        if (original instanceof Future) {
            ((Future<?>) original).cancel(true);
        }
    }
}
