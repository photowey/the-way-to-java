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
package com.photowey.quartz.in.action.job;

import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * {@code SimpleJobTest}
 *
 * @author photowey
 * @date 2022/10/23
 * @since 1.0.0
 */
@SpringBootTest
class SimpleJobTest {

    @Test
    public void testSimple() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job1", "job-group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "trigger-group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever()
                ).build();

        scheduler.scheduleJob(job, trigger);

        scheduler.start();
        Keeper.keep(20_000);
        scheduler.shutdown();
    }

    @Test
    public void testCron() throws SchedulerException, InterruptedException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job1", "job-group1")
                .build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "trigger-group1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build();

        scheduler.scheduleJob(job, cronTrigger);

        scheduler.start();
        Keeper.keep(20_000);
        scheduler.shutdown();
    }

    @Test
    void testMultiJob() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job1", "job-group1")
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "trigger-group1")
                .startNow()
                .forJob(job)
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();

        Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "trigger-group1")
                .startNow()
                .forJob(job)
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(3)
                        .repeatForever())
                .build();

        scheduler.addJob(job, false);

        scheduler.scheduleJob(trigger);
        scheduler.scheduleJob(trigger2);

        scheduler.start();
        Keeper.keep(20_000);
        scheduler.shutdown();
    }

    public static class Keeper {

        public static void keep(long millis) {
            try {
                TimeUnit.MILLISECONDS.sleep(millis);
            } catch (InterruptedException ignored) {
                Thread.interrupted();
            }
        }
    }

}