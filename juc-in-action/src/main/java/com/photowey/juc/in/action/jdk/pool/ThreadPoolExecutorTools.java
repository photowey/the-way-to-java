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
package com.photowey.juc.in.action.jdk.pool;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * {@code ThreadPoolExecutorTools}
 *
 * @author photowey
 * @date 2021/11/30
 * @since 1.0.0
 */
@Slf4j
public class ThreadPoolExecutorTools {

    /**
     * 1.如果当前运行的线程数少于 {@code corePoolSize},则创建新线程来执行任务
     * 2.在线程池完成预热之后(当前运行的线程数等于 {@code corePoolSize} ),将任务加入 {@link LinkedBlockingQueue}
     * 3.线程执行完1中的任务后,会在循环中反复从 {@link LinkedBlockingQueue} 获取任务来执行
     * <p>
     * FixedThreadPool 使用无界队列 {@link LinkedBlockingQueue} 作为线程池的工作队列(队列的容量为 Integer.MAX_VALUE).
     * 使用无界队列作为工作队列会对线程池带来如下影响:
     * 1.当线程池中的线程数达到 {@code corePoolSize} 后,新任务将在无界队列中等待,因此线程池中的线程数不会超过 {@code corePoolSize}
     * 2.由于1,使用无界队列时 {@code maximumPoolSize} 将是一个无效参数
     * 3.由于1和2,使用无界队列时 {@code keepAliveTime} 将是一个无效参数
     * 4.由于使用无界队列,运行中的 FixedThreadPool (未执行方法 {@link ExecutorService#shutdown()} 或 {@link ExecutorService#shutdownNow()})
     * 不会拒绝任务(不会调用 {@link RejectedExecutionHandler#rejectedExecution(java.lang.Runnable, java.util.concurrent.ThreadPoolExecutor)}方法)
     */

    /**
     * 1.核心线程 == 最大线程(没有空闲线程-被创建,因此也无需超时时间)
     * 2.阻塞队列是无界队列
     * 3.适用于-任务量一直-相对耗时的任务
     *
     * @param nThreads 线程数量
     * @param taskSize 指定任务数量
     */
    public void newFixedThreadPool(int nThreads, int taskSize) {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        for (int i = 0; i < taskSize; i++) {
            executor.execute(new RunnableTask((i + 1)));
        }

        try {
            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }

        /**
         * 线程池状态变为SHUTDOWN
         * 不会接收新任务
         * 但已提交任务会执行完(包含了在队列当中)
         * 不会阻塞调用线程的执行
         */
        // executor.shutdown();

        /**
         * 线程池状态变为 STOP
         * 不会接收新任务
         * 会将队列中的任务返回
         * 并用 interrupt 的方式中断正在执行的任务
         */
        // executor.shutdownNow();
    }

    /**
     * 1.核心线程 == 0
     * 2.最大线程 == Integer.MAX_VALUE, 全部都是-空闲线程,60s 后被回收.
     * 3.SynchronousQueue
     * -- 没有容量,是无缓冲等待队列,是一个不存储元素的阻塞队列,
     * 会直接将任务交给消费者,必须等队列中的添加元素被消费后才能继续添加新的元素.
     * -- 这意味着,如果主线程提交任务的速度高于 {@code cachedThreadPool} 中线程处理任务的速度时, {@code cachedThreadPool} 会不断创建新线程
     * 4.适用于-执行时间短的任务
     * <p>
     * 1.一个可根据需要,创建新线程的线程池,
     * 如果没有可用的现有线程,则创建一个新线程并添加到池中,
     * 如果有被使用完但是还没销毁的线程,就复用该线程.
     * 2.终止并从缓存中移除那些已有 60 秒钟未被使用的线程.
     * 因此,长时间保持空闲的线程池不会使用任何资源
     * <p>
     * 这种线程池比较灵活,对于执行很多短期异步任务的程序而言,这些线程池通常可提高程序性能.
     *
     * @param taskSize 指定任务数量
     */
    public void newCachedThreadPool(int taskSize) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < taskSize; i++) {
            executor.execute(new RunnableTask((i + 1)));
        }

        try {
            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 1.核心线程 == 最大线程 == 1
     * 2.阻塞队列是无界队列
     * <p>
     * 希望多个任务排队执行,线程数固定为 1,任务数多于 1 时,会放入无界队列排队,任务执行完毕,这唯一的线程,也不会被释放.
     * 区别于自己创建一个单线程串行执行任务,如果任务执行失败而终止那么没有任何补救措施,
     * 而线程池还会新建一个线程,保证池的正常工作.
     * <p>
     * 注意区别于:
     * {@code Executors.newFixedThreadPool(1)}
     * newFixedThreadPool -> ThreadPoolExecutor 可类型强制 -> 再次设置相应的参数
     */
    public void newSingleThreadExecutor() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            log.info("execution task1");
        });
        executor.execute(() -> {
            log.info("execution task2 and occur exception now...");
            int exception = 1 / 0;
        });
        executor.execute(() -> {
            log.info("execution task2");
        });
        executor.execute(() -> {
            log.info("execution task4");
        });

        try {
            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 提交无返回结果的任务
     *
     * @param nThreads 线程数量
     * @param taskSize 指定任务数量
     */
    public void executeTask(int nThreads, int taskSize) {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        for (int i = 0; i < taskSize; i++) {
            executor.execute(new RunnableTask((i + 1)));
        }

        try {
            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 提交由返回结果的任务
     *
     * @param nThreads 线程数量
     * @param taskSize 指定任务数量
     */
    public void submitTask(int nThreads, int taskSize) {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        List<Future<Integer>> futures = new ArrayList<>(taskSize);
        for (int i = 0; i < taskSize; i++) {
            Future<Integer> future = executor.submit(new SubmitTask((i + 1), 100L));
            futures.add(future);
        }

        futures.forEach(future -> {
            try {
                Integer result = future.get();
                // main-thread
                log.info("---------- get result:{}", result);
            } catch (InterruptedException | ExecutionException e) {
            }
        });

        try {
            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 批量调用
     *
     * @param nThreads 线程数量
     * @param taskSize 指定任务数量
     */
    public void invokeAll(int nThreads, int taskSize) {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        List<SubmitTask> tasks = new ArrayList<>(taskSize);
        for (int i = 0; i < taskSize; i++) {
            tasks.add(new SubmitTask((i + 1), 100L));
        }
        try {
            List<Future<Integer>> futures = executor.invokeAll(tasks);
            futures.forEach(future -> {
                try {
                    Integer result = future.get();
                    // main-thread
                    log.info("---------- get future result:{}", result);
                } catch (InterruptedException | ExecutionException e) {
                }
            });
        } catch (InterruptedException e) {
        }

        try {
            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 返回-最快的一个任务的结果
     *
     * @param nThreads 线程数量
     * @param taskSize 指定任务数量
     */
    public void invokeAny(int nThreads, int taskSize) {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        List<SubmitTask> tasks = new ArrayList<>(taskSize);
        Random random = new SecureRandom();

        for (int i = 0; i < taskSize; i++) {
            tasks.add(new SubmitTask((i + 1), (1_000 + random.nextInt(4_000))));
        }

        try {
            Integer taskNo = executor.invokeAny(tasks);
            log.info("---------- invokeAny:{}", taskNo);
        } catch (InterruptedException | ExecutionException e) {
        }

        try {
            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }

    public void schedule(int corePoolSize) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(corePoolSize);
        log.info("---------- main put task ----------");
        executor.schedule(() -> {
            log.info("---------- scheduled task ----------");
        }, 1L, TimeUnit.MILLISECONDS);

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
        }

        try {
            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 每间隔一段时间执行,分为两种情况:
     * 1.当前任务执行时间小于间隔时间,每次到点即执行
     * 2.当前任务执行时间大于等于间隔时间,任务执行后立即执行下一次任务,相当于连续执行了
     *
     * @param corePoolSize 核心线程数
     */
    public void scheduleAtFixedRate(int corePoolSize) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(corePoolSize);
        log.info("---------- main put task ----------");
        executor.scheduleAtFixedRate(() -> {
            log.info("---------- scheduled task ----------");
        }, 1L, 2L, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(12);
        } catch (InterruptedException e) {
        }

        try {
            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 每当上次任务执行完毕后,间隔一段时间执行.
     * 不管当前任务执行时间大于|等于|小于间隔时间,执行效果都是一样的
     *
     * @param corePoolSize 核心线程数
     */
    public void scheduleWithFixedDelay(int corePoolSize) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(corePoolSize);
        log.info("---------- main put task ----------");
        executor.scheduleWithFixedDelay(() -> {
            log.info("---------- scheduled task ----------");
        }, 1L, 2L, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(12);
        } catch (InterruptedException e) {
        }

        try {
            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }
}
