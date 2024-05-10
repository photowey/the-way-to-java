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
package io.github.photowey.redisson.delayed.queue.in.action.event;

import io.github.photowey.redisson.delayed.queue.in.action.core.task.RedissonDelayedTask;
import io.github.photowey.redisson.delayed.queue.in.action.core.task.TaskContext;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * {@code RedissonDelayedTaskEvent}
 *
 * @author photowey
 * @date 2024/03/09
 * @since 1.0.0
 */
public class RedissonDelayedTaskEvent extends ApplicationEvent {

    public RedissonDelayedTaskEvent(RedissonDelayedTask<? extends Serializable> source) {
        super(source);
    }

    public <T extends Serializable> RedissonDelayedTask<T> getTask() {
        return (RedissonDelayedTask<T>) this.getSource();
    }

    public TaskContext<?> toTaskContext() {
        RedissonDelayedTask<Serializable> task = this.getTask();
        return TaskContext.builder()
                .topic(task.topic())
                .taskId(task.taskId())
                .payload(task.payload())
                .build();
    }
}
