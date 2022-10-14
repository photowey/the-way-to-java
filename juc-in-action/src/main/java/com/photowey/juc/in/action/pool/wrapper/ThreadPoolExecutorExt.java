package com.photowey.juc.in.action.pool.wrapper;

import java.util.concurrent.*;

/**
 * {@code ThreadPoolExecutorExt}
 *
 * @author photowey
 * @date 2022/10/14
 * @since 1.0.0
 */
public class ThreadPoolExecutorExt extends ThreadPoolExecutor {

    public ThreadPoolExecutorExt(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

    }

    public ThreadPoolExecutorExt(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue workQueue,
            ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadPoolExecutorExt(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue workQueue,
            RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadPoolExecutorExt(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue workQueue,
            ThreadFactory threadFactory,
            RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable task, T value) {
        return new FutureTaskWrapper<>(new FutureTask<>(task, value));
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> task) {
        return new FutureTaskWrapper<>(new FutureTask<>(task));
    }

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadWrapper.wrap(task));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(ThreadWrapper.wrap(task), result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadWrapper.wrap(task));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadWrapper.wrap(task));
    }
}

