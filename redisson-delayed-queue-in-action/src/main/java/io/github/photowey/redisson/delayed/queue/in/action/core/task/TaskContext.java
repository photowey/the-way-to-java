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
package io.github.photowey.redisson.delayed.queue.in.action.core.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code TaskContext}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskContext<P extends Serializable> implements Serializable {

    private static final long serialVersionUID = -8411945629477188222L;

    private String topic;
    private String taskId;
    private P payload;

    public String topic() {
        return topic;
    }

    public String taskId() {
        return taskId;
    }

    public P payload() {
        return payload;
    }
}
