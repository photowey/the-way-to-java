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
package com.photowey.jobrunr.in.action.service;

import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.jobrunr.spring.annotations.Recurring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * {@code MyService}
 *
 * @author photowey
 * @date 2023/11/05
 * @since 1.0.0
 */
@Component
public class MyService implements MyServiceInterface {

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(MyService.class));

    @Recurring(id = "my-recurring-job", cron = "*/5 * * * * *")
    @Job(name = "My recurring job")
    public void doRecurringJob() {
        LOGGER.info("Doing some work without arguments");
    }

    @Override
    public void doSimpleJob(String anArgument) {
        LOGGER.info("Doing some work: " + anArgument);
    }

    public void doLongRunningJob(String anArgument) {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(String.format("Doing work item %d: %s", i, anArgument));
                Thread.sleep(20000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void doLongRunningJobWithJobContext(String anArgument, JobContext jobContext) {
        try {
            LOGGER.warn("Starting long running job...");
            final JobDashboardProgressBar progressBar = jobContext.progressBar(10);
            for (int i = 0; i < 10; i++) {
                LOGGER.info(String.format("Processing item %d: %s", i, anArgument));
                System.out.println(String.format("Doing work item %d: %s", i, anArgument));
                Thread.sleep(15000);
                progressBar.increaseByOne();
            }
            LOGGER.warn("Finished long running job...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}