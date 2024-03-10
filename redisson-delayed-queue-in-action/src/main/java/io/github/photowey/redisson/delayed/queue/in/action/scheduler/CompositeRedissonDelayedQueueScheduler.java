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
package io.github.photowey.redisson.delayed.queue.in.action.scheduler;

import io.github.photowey.redisson.delayed.queue.in.action.core.pair.QueuePair;
import io.github.photowey.redisson.delayed.queue.in.action.core.task.RedissonDelayedTask;
import io.github.photowey.redisson.delayed.queue.in.action.executor.RedissonDelayedQueueExecutor;
import io.github.photowey.redisson.delayed.queue.in.action.manager.RedissonDelayedQueueManager;
import io.github.photowey.redisson.delayed.queue.in.action.property.RedissonClientProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * {@code CompositeRedissonDelayedQueueScheduler}
 *
 * @author photowey
 * @date 2024/03/09
 * @since 1.0.0
 */
@Slf4j
public class CompositeRedissonDelayedQueueScheduler implements RedissonDelayedQueueScheduler {

    private final RedissonDelayedQueueManager manager;
    private final RedissonDelayedQueueExecutor executor;
    private final ScheduledExecutorService watcher;

    public CompositeRedissonDelayedQueueScheduler(RedissonDelayedQueueManager manager, RedissonDelayedQueueExecutor executor) {
        this.manager = manager;
        this.executor = executor;

        this.watcher = Executors.newSingleThreadScheduledExecutor();
    }

    // ----------------------------------------------------------------

    @Override
    public RedissonClientProperties redissonProperties() {
        return this.manager.redissonProperties();
    }

    // ----------------------------------------------------------------

    @Override
    public String topic() {
        return this.redissonProperties().getDelayed().getTopic();
    }

    @Override
    public void schedule() {
        RedissonClientProperties.DelayedQueue delayedQueue = this.redissonProperties().getDelayed();
        this.watcher.scheduleAtFixedRate(this::watch, delayedQueue.getInitialDelay(), delayedQueue.getPeriod(), delayedQueue.getUnit());
    }

    @Override
    public void start() {
        this.schedule();
    }

    @Override
    public void stop() {
        this.watcher.shutdown();
    }

    // ----------------------------------------------------------------

    @Override
    public void destroy() throws Exception {
        this.stop();
        QueuePair pair = this.manager.tryAcquirePair(this.topic());
        pair.destroy();
    }

    // ----------------------------------------------------------------

    private void watch() {
        try {
            this.run();
        } catch (Exception e) {
            log.error("handle.redisson.delayed.queue.watch.failed", e);
        }
    }

    public void run() {
        QueuePair pair = this.manager.tryAcquirePair(this.topic());

        try {
            RedissonDelayedTask<?> delayedTask = pair.blockingQueue().take();
            this.executor.execute(delayedTask);
        } catch (Throwable e) {
            Thread.currentThread().interrupt();
        }
    }
}
