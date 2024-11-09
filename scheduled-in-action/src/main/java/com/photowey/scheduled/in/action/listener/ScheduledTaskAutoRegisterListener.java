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
package com.photowey.scheduled.in.action.listener;

import com.photowey.scheduled.in.action.handler.ScheduledTaskHandler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@code ScheduledTaskAutoRegisterListener}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
@Component
public class ScheduledTaskAutoRegisterListener extends AbstractEventListener<ApplicationReadyEvent> implements Ordered {

    private final AtomicBoolean started = new AtomicBoolean();

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (this.started.compareAndSet(false, true)) {
            Map<String, ScheduledTaskHandler> beans =
                this.listableBeanFactory().getBeansOfType(ScheduledTaskHandler.class);

            List<ScheduledTaskHandler> handlers = new ArrayList<>(beans.values());
            for (ScheduledTaskHandler handler : handlers) {
                handler.register(this.scheduledTaskRegistry());
            }
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
