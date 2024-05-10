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
package io.github.photowey.redisson.delayed.queue.in.action.manager;

import io.github.photowey.redisson.delayed.queue.in.action.queue.DelayedQueue;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * {@code DelayedQueueManager}
 *
 * @author photowey
 * @date 2024/03/02
 * @since 1.0.0
 */
public interface DelayedQueueManager extends BeanFactoryPostProcessor {

    DelayedQueue register(DelayedQueue delayedQueue);

    DelayedQueue tryAcquire(String topic);

    // ----------------------------------------------------------------

    boolean topicContains(String topic);

    boolean taskContains(String taskId);

    // ----------------------------------------------------------------

    boolean removeTask(String taskId);
}