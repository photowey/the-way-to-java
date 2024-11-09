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
package com.photowey.scheduled.in.action.schedule;

import com.photowey.common.in.action.formatter.StringFormatter;
import com.photowey.common.in.action.util.LambdaUtils;
import com.photowey.common.in.action.util.ObjectUtils;
import com.photowey.scheduled.in.action.context.DingtalkNotifyContext;
import com.photowey.scheduled.in.action.context.NotifyContext;
import com.photowey.scheduled.in.action.core.domain.payload.NotifyPayload;
import com.photowey.scheduled.in.action.core.exception.DingtalkException;
import com.photowey.scheduled.in.action.notify.DingtalkNotifier;
import com.photowey.scheduled.in.action.property.MessageProperties;
import com.photowey.scheduled.in.action.property.ScheduledProperties;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * {@code ScheduledDingtalkNotifier}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
public interface ScheduledDingtalkNotifier extends DingtalkNotifier {

    @Override
    default void dingtalk(String taskId, String taskName) {
        boolean notEnabled = this.messageProperties().dingtalk().notEnabled();
        if (notEnabled) {
            return;
        }

        ScheduledProperties scheduledProperties = this.scheduledProperties();
        ScheduledProperties.Task task = this.determingTask(scheduledProperties, taskId);

        String app = this.app();
        String profiles = StringUtils.arrayToCommaDelimitedString(this.environment().getActiveProfiles());
        String domain = this.domain();

        String body = StringFormatter.format(this.template(),
            taskId,
            taskName,
            task.getTaskCron(),
            app,
            DEPLOY_TENANT_CLOUD,
            profiles,
            domain
        );

        NotifyPayload payload = NotifyPayload.dingtalkMarkdown(
            body,
            "App: 定时任务执行通知",
            taskId,
            taskName
        );

        this.toDingtalk(payload);
    }

    @Override
    default void dingtalk(String template, String title, String actonId, String actionName) {
        MessageProperties messageProperties = this.messageProperties();
        boolean notEnabled = messageProperties.getDingtalk().notEnabled();
        if (notEnabled) {
            return;
        }

        String app = this.app();
        String profiles = StringUtils.arrayToCommaDelimitedString(this.environment().getActiveProfiles());
        String domain = this.domain();

        String body = StringFormatter.format(template,
            actonId,
            actionName,
            app,
            DEPLOY_TENANT_CLOUD,
            profiles,
            domain
        );

        NotifyPayload payload = NotifyPayload.dingtalkMarkdown(
            body,
            title,
            actonId,
            actionName
        );

        this.toDingtalk(payload);
    }

    @Override
    default void toDingtalk(NotifyPayload payload) {
        // @formatter:off
        NotifyContext ctx = DingtalkNotifyContext.builder()
            .type(payload.getType())
            .title(payload.getTitle())
            .messageType(payload.getMessageType())
            .body(payload.getContent())
            .build();
        // @formatter:on

        if (ObjectUtils.isNotNullOrEmpty(payload.getTarget())) {
            ctx.setTargetz(StringUtils.split(payload.getTarget(), ","));
        }

        try {
            this.preLog(ctx);
            this.dingtalkMessageNotifier().publish(ctx);
            this.posLog(ctx);
        } catch (Throwable e) {
            this.errorLog(ctx, e);
            throw new DingtalkException(e);
        }
    }

    // ----------------------------------------------------------------

    default void preLog(NotifyContext ctx) {

    }

    default void posLog(NotifyContext ctx) {

    }

    default void errorLog(NotifyContext ctx, Throwable throwable) {

    }

    // ----------------------------------------------------------------

    default ScheduledProperties.Task determingTask(ScheduledProperties scheduledProperties, String taskId) {
        List<ScheduledProperties.Task> tasks = scheduledProperties.getTasks();
        ScheduledProperties.Task taskz = LambdaUtils.findAny(
            tasks, task -> task.getTaskId().equals(taskId));
        if (ObjectUtils.isNullOrEmpty(taskz)) {
            taskz = new ScheduledProperties.Task();
            taskz.setTaskCron("Unknown");
        }

        return taskz;
    }
}

