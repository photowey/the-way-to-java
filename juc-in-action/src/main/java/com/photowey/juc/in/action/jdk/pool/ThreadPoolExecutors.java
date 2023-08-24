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

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code ThreadPoolExecutors}
 *
 * @author photowey
 * @date 2021/11/30
 * @see {@link java.util.concurrent.ThreadPoolExecutor}
 * @since 1.0.0
 */
public class ThreadPoolExecutors {

    /**
     * 任务的性质:CPU密集型任务,IO密集型任务和混合型任务;
     * 任务的优先级:高,中和低;
     * 任务的执行时间:长,中和短;
     * 任务的依赖性:是否依赖其他系统资源，如数据库连接。
     */

    /**
     * 根据指定的参数-创建一个线程池
     *
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   idle线程-活动保持时间, 如果超时-空闲线程会被-{@code kill} 掉
     * @param unit            idle线程-活动保持时间-单位
     * @param workQueue       工作队列
     * @param threadFactory   线程工厂
     * @param handler         拒绝策略
     *                        {@link ThreadPoolExecutor.CallerRunsPolicy} # 只用调用者所在线程来运行任务
     *                        {@link ThreadPoolExecutor.AbortPolicy}  # 直接抛出异常
     *                        {@link ThreadPoolExecutor.DiscardPolicy} # 不处理,丢弃掉
     *                        {@link ThreadPoolExecutor.DiscardOldestPolicy} # 丢弃队列里最近的一个任务，并执行当前任务
     */
    private void createThreadPoolExecutor(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory,
            RejectedExecutionHandler handler) {

    }

    private final AtomicInteger counter = new AtomicInteger(1);

    public void createThreadPoolExecutorNormal(int corePoolSize,
                                               int maximumPoolSize, long keepAliveTime, int capacity, int taskSize) {
        this.createThreadPoolExecutorNormal(corePoolSize, maximumPoolSize, keepAliveTime, capacity, taskSize, new ThreadPoolExecutor.AbortPolicy());
    }

    public void createThreadPoolExecutorNormal(int corePoolSize,
                                               int maximumPoolSize, long keepAliveTime, int capacity, int taskSize, RejectedExecutionHandler handler) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(capacity),
                (r) -> new Thread(r, "t" + counter.getAndIncrement()),
                handler
        );

        for (int i = 0; i < taskSize; i++) {
            executor.execute(new RunnableTask((i + 1)));
        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
        }

        executor.shutdown();
    }

    public void createThreadPoolExecutorNormalAliveTime(int corePoolSize,
                                                        int maximumPoolSize, long keepAliveTime, int capacity, int taskSize) {
        this.createThreadPoolExecutorNormalAliveTime(corePoolSize, maximumPoolSize, keepAliveTime, capacity, taskSize, new ThreadPoolExecutor.AbortPolicy());
    }

    public void createThreadPoolExecutorNormalAliveTime(int corePoolSize,
                                                        int maximumPoolSize, long keepAliveTime, int capacity, int taskSize, RejectedExecutionHandler handler) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(capacity),
                (r) -> new Thread(r, "t" + counter.getAndIncrement()),
                handler
        );

        for (int i = 0; i < taskSize; i++) {
            if (i == taskSize - 1) {
                try {
                    // 睡 keepAliveTime + 1 -> 过了空闲线程的存活时间
                    // 理论上:
                    // t1 - 执行 task:1,2,4
                    // t2 - 执行 task:3
                    TimeUnit.SECONDS.sleep(keepAliveTime + 4);
                } catch (InterruptedException e) {
                }
            }

            executor.execute(new AliveTimeRunnableTask((i + 1), executor));
        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
        }

        executor.shutdown();
    }
}
