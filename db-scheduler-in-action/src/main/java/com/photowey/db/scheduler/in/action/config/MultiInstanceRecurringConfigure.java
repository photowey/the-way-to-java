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
package com.photowey.db.scheduler.in.action.config;

import com.github.kagkarlsson.scheduler.task.ExecutionContext;
import com.github.kagkarlsson.scheduler.task.Task;
import com.github.kagkarlsson.scheduler.task.TaskInstance;
import com.github.kagkarlsson.scheduler.task.TaskWithDataDescriptor;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import com.photowey.db.scheduler.in.action.core.domain.model.NamedConsumer;
import com.photowey.db.scheduler.in.action.core.domain.model.NamedSchedule;
import com.photowey.db.scheduler.in.action.listener.ScheduleTaskStarter;
import com.photowey.db.scheduler.in.action.schedule.ScheduleTaskExecutor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@code MultiInstanceRecurringConfigure}
 *
 * @author photowey
 * @date 2023/10/22
 * @since 1.0.0
 */
@Configuration
public class MultiInstanceRecurringConfigure implements BeanFactoryAware {

    private static final TaskWithDataDescriptor<NamedSchedule> MULTI_INSTANCE_RECURRING_TASK =
            ScheduleTaskStarter.MULTI_INSTANCE_RECURRING_TASK;

    private ListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Bean
    public Task<NamedSchedule> multiInstanceRecurring() {
        return Tasks.recurringWithPersistentSchedule(MULTI_INSTANCE_RECURRING_TASK)
                .execute((TaskInstance<NamedSchedule> task, ExecutionContext executionContext) -> {
                    NamedSchedule schedule = task.getData();
                    NamedConsumer customer = schedule.getCustomer();

                    Map<String, ScheduleTaskExecutor> beans = this.beanFactory.getBeansOfType(ScheduleTaskExecutor.class);

                    List<ScheduleTaskExecutor> executors = new ArrayList<>(beans.values());
                    AnnotationAwareOrderComparator.sort(executors);

                    for (ScheduleTaskExecutor executor : executors) {
                        if (executor.supports(customer.getTopic())) {
                            executor.execute(schedule);
                        }
                    }
                });
    }
}
