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
package io.github.photowey.redisson.delayed.queue.in.action.queue;

import io.github.photowey.redisson.delayed.queue.in.action.core.task.RedissonDelayedTask;
import io.github.photowey.redisson.delayed.queue.in.action.manager.RedissonDelayedQueueManager;
import io.github.photowey.redisson.delayed.queue.in.action.property.RedissonProperties;
import org.redisson.api.RedissonClient;

import java.io.Serializable;

/**
 * {@code RedissonDelayedQueue}
 *
 * @author photowey
 * @date 2024/03/02
 * @since 1.0.0
 */
public interface RedissonDelayedQueue extends DelayedQueue {

    RedissonDelayedQueueManager manager();

    default RedissonClient redisson() {
        return this.manager().redisson();
    }

    RedissonProperties redissonProperties();

    void registerTask(String taskId);

    <P extends Serializable> void offer(RedissonDelayedTask<P> task);
}
