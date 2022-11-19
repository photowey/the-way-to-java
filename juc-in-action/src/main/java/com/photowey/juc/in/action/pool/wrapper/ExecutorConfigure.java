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

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.*;

/**
 * {@code ExecutorConfigure}
 *
 * @author photowey
 * @date 2022/07/27
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class ExecutorConfigure implements AsyncConfigurer, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Executor getAsyncExecutor() {
        return this.beanFactory.getBean("commonAsyncExecutor", ThreadPoolTaskExecutorExt.class);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            String methodName = method.getName();
            log.error("--- method:[{}] async uncaught exception handler ---", methodName, ex);
        };
    }

    @Bean("commonAsyncExecutor")
    public ThreadPoolTaskExecutorExt commonAsyncExecutor() {
        ThreadPoolTaskExecutorExt taskExecutor = new ThreadPoolTaskExecutorExt();
        taskExecutor.setCorePoolSize(1 << 2);
        taskExecutor.setMaxPoolSize(1 << 3);
        taskExecutor.setQueueCapacity(1 << 10);
        taskExecutor.setKeepAliveSeconds(1 << 6);
        // MdcTaskDecorator
        taskExecutor.setTaskDecorator((r) -> r);
        taskExecutor.setThreadGroupName("async");
        taskExecutor.setThreadNamePrefix("tester" + "-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 核心线程在规定时间内会被回收
        taskExecutor.setAllowCoreThreadTimeOut(true);
        // 等待所有任务结束后再关闭线程池
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.initialize();

        return taskExecutor;
    }

    /**
     * 专门:
     * 用于时间短的小任务
     *
     * @return
     */
    @Bean("cachePoolExecutor")
    public ExecutorService cachePoolExecutor() {
        // Executors.newCachedThreadPool()
        return new ThreadPoolExecutor(
                0,
                Integer.MAX_VALUE,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

}
