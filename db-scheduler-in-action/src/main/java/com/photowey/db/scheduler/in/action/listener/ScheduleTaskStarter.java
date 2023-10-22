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
package com.photowey.db.scheduler.in.action.listener;

import com.github.kagkarlsson.scheduler.SchedulerClient;
import com.github.kagkarlsson.scheduler.task.TaskInstance;
import com.github.kagkarlsson.scheduler.task.TaskWithDataDescriptor;
import com.github.kagkarlsson.scheduler.task.schedule.CronSchedule;
import com.photowey.crypto.in.action.crypto.CryptoJava;
import com.photowey.db.scheduler.in.action.core.domain.model.NamedConsumer;
import com.photowey.db.scheduler.in.action.core.domain.model.NamedSchedule;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.Instant;

/**
 * {@code ScheduleTaskStarter}
 *
 * @author photowey
 * @date 2023/10/22
 * @since 1.0.0
 */
@Component
public class ScheduleTaskStarter implements ApplicationListener<ContextRefreshedEvent> {

    public static final TaskWithDataDescriptor<NamedSchedule> MULTI_INSTANCE_RECURRING_TASK =
            new TaskWithDataDescriptor<>("multi-instance-recurring-task", NamedSchedule.class);

    private final SchedulerClient scheduler;

    public ScheduleTaskStarter(SchedulerClient scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.sync();
    }

    private void sync() {
        this.execute();
    }

    public void execute() {
        String localhost = this.localhost();
        String md5 = CryptoJava.HASH.MD5.md5(localhost);

        CronSchedule cron = new CronSchedule("*/5 * * * * ?");
        NamedConsumer customer = new NamedConsumer("multi-instance-recurring-task", md5);
        NamedSchedule scheduled = new NamedSchedule(cron, customer);

        TaskInstance<NamedSchedule> task = MULTI_INSTANCE_RECURRING_TASK.instance(customer.getConsumerId(), scheduled);
        this.scheduler.schedule(task, cron.getInitialExecutionTime(Instant.now()));
    }

    private String localhost() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (Exception e) {
            throw new RuntimeException("Get localhost failed.");
        }
    }
}
