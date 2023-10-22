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
package com.photowey.db.scheduler.in.action.schedule;

import com.github.kagkarlsson.scheduler.task.schedule.CronSchedule;
import com.photowey.db.scheduler.in.action.core.domain.model.NamedConsumer;
import com.photowey.db.scheduler.in.action.core.domain.model.NamedSchedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * {@code DefaultScheduleTaskExecutor}
 *
 * @author photowey
 * @date 2023/10/22
 * @since 1.0.0
 */
@Slf4j
@Component
public class DefaultScheduleTaskExecutor implements ScheduleTaskExecutor, SmartInitializingSingleton, Ordered {

    @Override
    public void afterSingletonsInstantiated() {

    }

    @Override
    public boolean supports(String topic) {
        return "multi-instance-recurring-task".equals(topic);
    }

    @Override
    public void execute(NamedSchedule schedule) {
        CronSchedule corn = schedule.getSchedule();
        NamedConsumer customer = schedule.getCustomer();

        log.info("receive.scheduled.task.event:[{}:[{}:{}]]", corn.getPattern(), customer.getConsumerId(), customer.getTopic());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
