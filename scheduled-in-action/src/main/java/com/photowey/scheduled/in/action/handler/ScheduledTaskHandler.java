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
package com.photowey.scheduled.in.action.handler;

import com.photowey.scheduled.in.action.registry.ScheduledTaskRegistry;
import com.photowey.scheduled.in.action.schedule.ScheduledDingtalkNotifier;

/**
 * {@code ScheduledTaskHandler}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
public interface ScheduledTaskHandler extends ScheduledDingtalkNotifier {

    /**
     * 任务模板
     */
    String DINGTALK_SCHEDULE_TASK_NOTIFY_BODY_TEMPLATE = "# APP: 定时任务执行通知\n" +
        "- 动作: 执行定时任务\n" +
        "- 任务标识: {}\n" +
        "- 任务名称: {}\n" +
        "- 任务表达式: {}\n" +
        "- 应用名称: {}\n" +
        "- 部署点: {}\n" +
        "- 部署环境: {}\n" +
        "- 结果: 成功\n" +
        "- 访问地址: [Swagger]({}/doc.html)";

    default String template() {
        return DINGTALK_SCHEDULE_TASK_NOTIFY_BODY_TEMPLATE;
    }

    /**
     * 注册调度任务
     *
     * @param registry {@link ScheduledTaskRegistry}
     */
    void register(ScheduledTaskRegistry registry);
}

