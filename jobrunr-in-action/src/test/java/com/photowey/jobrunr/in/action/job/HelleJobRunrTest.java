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
package com.photowey.jobrunr.in.action.job;

import com.photowey.jobrunr.in.action.service.MyServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.scheduling.JobScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * {@code HelleJobRunrTest}
 *
 * @author photowey
 * @date 2023/11/05
 * @since 1.0.0
 */
@Slf4j
@SpringBootTest
class HelleJobRunrTest {

    @Autowired
    private MyServiceInterface myService;

    @Autowired
    private JobScheduler jobScheduler;

    @Test
    void testScheduleRecurrently() throws InterruptedException {
        //jobScheduler.scheduleRecurrently(Cron.every15seconds(), () -> this.myService.doSimpleJob("Hello~"));
        jobScheduler.scheduleRecurrently("*/5 * * * * *", () -> this.myService.doSimpleJob("Hello~"));

        TimeUnit.SECONDS.sleep(30);
    }

}
