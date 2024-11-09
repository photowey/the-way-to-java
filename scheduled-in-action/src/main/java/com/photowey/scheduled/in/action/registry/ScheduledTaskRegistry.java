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
package com.photowey.scheduled.in.action.registry;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.photowey.common.in.action.util.HardwareUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * {@code ScheduledTaskRegistry}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
public class ScheduledTaskRegistry implements SchedulingConfigurer, SmartInitializingSingleton {

    private final Set<ScheduledFuture<?>> scheduledFutures;
    private final Map<String, ScheduledFuture<?>> taskFutures;

    private ScheduledTaskRegistrar taskRegistrar;

    public ScheduledTaskRegistry() {
        this.scheduledFutures = new LinkedHashSet<>();
        this.taskFutures = new ConcurrentHashMap<>(1 << 3);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(this.scheduleTaskExecutors());
        this.taskRegistrar = taskRegistrar;
    }

    @Override
    public void afterSingletonsInstantiated() {

    }

    // ----------------------------------------------------------------

    private void removeTask(String taskId) {
        ScheduledFuture<?> future = this.taskFutures.remove(taskId);
        this.scheduledFutures().remove(future);
        future.cancel(true);
    }

    // ----------------------------------------------------------------

    public void replaceCronTask(String taskId, TriggerTask triggerTask) {
        this.removeTask(taskId);
        this.addTask(taskId, triggerTask);
    }

    public void replaceCronTask(String taskId, String corn, Runnable task) {
        this.removeTask(taskId);
        this.addTask(
            taskId,
            new TriggerTask(task, (ctx) -> {
                CronTrigger trigger = new CronTrigger(corn);
                return trigger.nextExecutionTime(ctx);
            })
        );
    }

    // ----------------------------------------------------------------

    public void addTask(String taskId, TriggerTask triggerTask) {
        TaskScheduler scheduler = this.taskRegistrar.getScheduler();
        assert scheduler != null;
        ScheduledFuture<?> future = scheduler.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());
        this.scheduledFutures().add(future);
        this.taskFutures.put(taskId, future);
    }

    public void addCronTask(String taskId, String corn, Runnable task) {
        this.addTask(
            taskId,
            new TriggerTask(task, (ctx) -> {
                CronTrigger trigger = new CronTrigger(corn);
                return trigger.nextExecutionTime(ctx);
            })
        );
    }

    // ----------------------------------------------------------------

    private Executor scheduleTaskExecutors() {
        int corePoolSize = HardwareUtils.doubleNcpu();
        return new ScheduledThreadPoolExecutor(
            corePoolSize,
            new ThreadFactoryBuilder().setNameFormat("scheduler-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());
    }

    // ----------------------------------------------------------------

    private Set<ScheduledFuture<?>> scheduledFutures() {
        return this.scheduledFutures;
    }
}

