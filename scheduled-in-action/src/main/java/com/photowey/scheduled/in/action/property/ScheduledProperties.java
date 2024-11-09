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
package com.photowey.scheduled.in.action.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;

/**
 * {@code ScheduledProperties}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
@Data
@ConfigurationProperties(prefix = ScheduledProperties.PREFIX)
public class ScheduledProperties implements Serializable {

    private static final long serialVersionUID = -5425815941596760543L;
    public static final String PREFIX = "io.github.photowey.platform.app.scheduled";

    private int corePoolSize = 1;
    private List<Task> tasks;

    @Data
    public static class Task implements Serializable {

        private static final long serialVersionUID = -7128756424156671787L;

        private String taskId;
        private String taskCron;
    }

    public static String getPrefix() {
        return PREFIX;
    }
}

