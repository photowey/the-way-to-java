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

import io.github.photowey.redisson.delayed.queue.in.action.core.pair.QueuePair;
import io.github.photowey.redisson.delayed.queue.in.action.listener.CompositeRedissonDelayedQueueEventListener;
import io.github.photowey.redisson.delayed.queue.in.action.property.RedissonProperties;
import io.github.photowey.redisson.delayed.queue.in.action.scheduler.RedissonDelayedQueueScheduler;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.List;

/**
 * {@code RedissonDelayedQueueManager}
 *
 * @author photowey
 * @date 2024/03/02
 * @since 1.0.0
 */
public interface RedissonDelayedQueueManager extends DelayedQueueManager, SmartInitializingSingleton {

    RedissonProperties redissonProperties();

    RedissonClient redisson();

    QueuePair registerPair(QueuePair pair);

    QueuePair tryAcquirePair(String topic);

    List<QueuePair> tryAcquirePairs();

    RedissonDelayedQueueScheduler redissonScheduler();

    CompositeRedissonDelayedQueueEventListener redissonEventListener();
}
