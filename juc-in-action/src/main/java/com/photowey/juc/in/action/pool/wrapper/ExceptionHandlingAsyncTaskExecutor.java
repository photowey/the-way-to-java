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

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

/**
 * 线程异步处理
 *
 * @author wangxin
 * @version v1.0.0
 * @date: 2022-10-13 2:00 PM
 */
public class ExceptionHandlingAsyncTaskExecutor implements AsyncListenableTaskExecutor, InitializingBean, DisposableBean {

    private final AsyncTaskExecutor asyncTaskExecutor;

    public ExceptionHandlingAsyncTaskExecutor(AsyncTaskExecutor asyncTaskExecutor) {
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    @Override
    public void execute(Runnable task) {
        this.asyncTaskExecutor.execute(ThreadWrapper.wrap(task));
    }

    @Override
    @Deprecated
    public void execute(Runnable task, long startTimeout) {
        this.asyncTaskExecutor.execute(ThreadWrapper.wrap(task), startTimeout);
    }

    @Override

    public Future<?> submit(Runnable task) {
        return this.asyncTaskExecutor.submit(ThreadWrapper.wrap(task));
    }

    @Override

    public <T> Future<T> submit(Callable<T> task) {
        return this.asyncTaskExecutor.submit(ThreadWrapper.wrap(task));
    }

    @Override
    public void destroy() throws Exception {
        if (this.asyncTaskExecutor instanceof DisposableBean) {
            DisposableBean bean = (DisposableBean) this.asyncTaskExecutor;
            bean.destroy();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.asyncTaskExecutor instanceof InitializingBean) {
            InitializingBean bean = (InitializingBean) this.asyncTaskExecutor;
            bean.afterPropertiesSet();
        }
    }

    @Override

    public ListenableFuture<?> submitListenable(Runnable task) {
        try {
            ListenableFutureTask<Object> future = new ListenableFutureTask<>(task, null);
            this.asyncTaskExecutor.execute(future);
            return future;
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + this.asyncTaskExecutor + "] did not accept task: " + task, ex);
        }
    }

    @Override

    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        try {
            ListenableFutureTask<T> future = new ListenableFutureTask<>(task);
            this.asyncTaskExecutor.execute(future);
            return future;
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + this.asyncTaskExecutor + "] did not accept task: " + task, ex);
        }
    }
}
