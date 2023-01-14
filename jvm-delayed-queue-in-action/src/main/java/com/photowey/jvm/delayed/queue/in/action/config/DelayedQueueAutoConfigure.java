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
package com.photowey.jvm.delayed.queue.in.action.config;

import com.photowey.jvm.delayed.queue.in.action.event.DelayedEvent;
import com.photowey.jvm.delayed.queue.in.action.handler.DefaultDelayedQueueHandler;
import com.photowey.jvm.delayed.queue.in.action.handler.DelayedQueueHandler;
import com.photowey.jvm.delayed.queue.in.action.listener.DelayedQueueListener;
import com.photowey.jvm.delayed.queue.in.action.publisher.DefaultDelayedQueueEventPublisher;
import com.photowey.jvm.delayed.queue.in.action.publisher.DelayedQueueEventPublisher;
import com.photowey.jvm.delayed.queue.in.action.registry.DelayedQueueListenerRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@code DelayedQueueAutoConfigure}
 *
 * @author weichangjun
 * @date 2023/01/14
 * @since 1.0.0
 */
@AutoConfiguration(after = {DelayedQueueListenerRegistryConfigure.class})
public class DelayedQueueAutoConfigure implements BeanFactoryAware, SmartInitializingSingleton {

    private ListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        DelayedQueueListenerRegistry listenerRegistry = this.beanFactory.getBean(DelayedQueueListenerRegistry.class);
        Map<String, DelayedQueueListener> beans = this.beanFactory.getBeansOfType(DelayedQueueListener.class);
        beans.forEach((k, listener) -> listenerRegistry.registerListener(listener));
    }

    /**
     * 延迟队列
     *
     * @return {@link DelayQueue<DelayedEvent>}
     */
    @Bean
    @ConditionalOnMissingBean
    public DelayQueue<DelayedEvent> delayQueue() {
        return new DelayQueue<>();
    }

    /**
     * 申明延迟队列处理器
     *
     * @param delayQueue          {@link DelayQueue<DelayedEvent>} 延迟队列
     * @param delayedTaskExecutor {@link ThreadPoolTaskExecutor} 任务执行器
     * @return {@link DelayedQueueHandler}
     */
    @Bean
    @ConditionalOnMissingBean
    public DelayedQueueHandler delayedQueueHandler(
            DelayQueue<DelayedEvent> delayQueue,
            @Qualifier("delayedTaskExecutor") ThreadPoolTaskExecutor delayedTaskExecutor) {
        DelayedQueueListenerRegistry listenerRegistry = this.beanFactory.getBean(DelayedQueueListenerRegistry.class);
        return new DefaultDelayedQueueHandler(delayQueue, delayedTaskExecutor, listenerRegistry);
    }

    /**
     * 申明事件发布器-如果需要
     *
     * @param delayQueue {@link DelayQueue<DelayedEvent>} 延迟队列
     * @return {@link DelayedQueueEventPublisher}
     */
    @Bean
    @ConditionalOnMissingBean
    public DelayedQueueEventPublisher delayedQueueEventPublisher(DelayQueue<DelayedEvent> delayQueue) {
        return new DefaultDelayedQueueEventPublisher(delayQueue);
    }

    @Bean("delayedTaskExecutor")
    @ConditionalOnMissingBean(name = "delayedTaskExecutor")
    public ThreadPoolTaskExecutor delayedTaskExecutor() {
        ThreadPoolTaskExecutor delayedTaskExecutor = new ThreadPoolTaskExecutor();
        delayedTaskExecutor.setCorePoolSize(1 << 2);
        delayedTaskExecutor.setMaxPoolSize(1 << 3);
        delayedTaskExecutor.setKeepAliveSeconds(60_0);
        delayedTaskExecutor.setQueueCapacity(1 << 10);
        delayedTaskExecutor.setThreadNamePrefix("delayed" + "-");
        delayedTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        delayedTaskExecutor.setAllowCoreThreadTimeOut(true);
        delayedTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        delayedTaskExecutor.initialize();

        return delayedTaskExecutor;
    }
}
