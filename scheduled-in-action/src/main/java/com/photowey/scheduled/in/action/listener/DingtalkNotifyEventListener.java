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

import com.photowey.scheduled.in.action.core.domain.payload.DingtalkNotifyPayload;
import com.photowey.scheduled.in.action.core.event.DingtalkNotifyEvent;
import com.photowey.scheduled.in.action.handler.ScheduledTaskHandler;
import com.photowey.scheduled.in.action.registry.ScheduledTaskRegistry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * {@code DingtalkNotifyEventListener}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
@Slf4j
public class DingtalkNotifyEventListener extends AbstractEventListener<DingtalkNotifyEvent> implements ScheduledTaskHandler {

    @Override
    public void register(ScheduledTaskRegistry register) {}

    @Override
    public Logger logger() {
        return log;
    }

    @Override
    public void onApplicationEvent(DingtalkNotifyEvent event) {
        DingtalkNotifyPayload payload = event.getDingtalkNotifyPayload();
        this.sync(payload);
    }

    private void sync(DingtalkNotifyPayload payload) {
        this.dingtalk(payload.getTopicId(), payload.getTopic());
    }
}

