package com.photowey.juc.in.action.pool.wrapper;

import java.util.concurrent.*;

/**
 * {@code FutureTaskWrapper}
 *
 * @author photowey
 * @date 2022/10/14
 * @since 1.0.0
 */
public class FutureTaskWrapper<V> implements RunnableFuture<V> {

    private FutureTask<V> delegate;

    public FutureTaskWrapper(FutureTask<V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void run() {
        this.delegate.run();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.delegate.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return this.delegate.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.delegate.isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return this.delegate.get();
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate.get(timeout, unit);
    }
}
