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
package io.github.photowey.redisson.delayed.queue.in.action.listener;

import io.github.photowey.redisson.delayed.queue.in.action.core.task.TaskContext;
import io.github.photowey.redisson.delayed.queue.in.action.event.RedissonDelayedTaskEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code CompositeRedissonDelayedQueueEventListener}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/11
 */
@Slf4j
public class CompositeRedissonDelayedQueueEventListener implements RedissonDelayedQueueEventListener {

    public final ConcurrentHashMap<String, DelayedQueueEventListener> ctx = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public ApplicationContext applicationContext() {
        return this.applicationContext;
    }

    @Override
    public void register(DelayedQueueEventListener listener) {
        this.ctx.computeIfAbsent(listener.getClass().getName(), (x) -> listener);
    }

    @Override
    public void remove(Class<DelayedQueueEventListener> clazz) {
        this.ctx.remove(clazz.getName());
    }

    @Override
    public void onEvent(RedissonDelayedTaskEvent event) {
        TaskContext<?> ctx = event.toTaskContext();

        List<DelayedQueueEventListener> eventListeners = new ArrayList<>(this.ctx.values());
        AnnotationAwareOrderComparator.sort(eventListeners);

        for (DelayedQueueEventListener eventListener : eventListeners) {
            if (eventListener.supports(ctx)) {
                this.handleEvent(eventListener, ctx);
            }
        }

        // 删除 task 注册
        this.tryRemoveTask(ctx.taskId());
    }

    private void handleEvent(DelayedQueueEventListener eventListener, TaskContext<?> ctx) {
        try {
            eventListener.handle(ctx);
        } catch (Exception e) {
            log.error("redisson.delayed.queue: handle.delayed.task.failed,info:[listener:{},topic:{},taskId:{}]",
                    eventListener.getClass().getName(), ctx.taskId(), ctx.taskId());
        }
    }
}
