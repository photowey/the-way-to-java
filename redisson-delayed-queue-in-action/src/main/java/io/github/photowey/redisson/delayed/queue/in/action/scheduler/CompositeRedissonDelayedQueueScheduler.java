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
import io.github.photowey.redisson.delayed.queue.in.action.property.RedissonProperties;
import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Set;
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

    private static final int THRESHOLD = 1 << 4;
    private static final String SCHEDULER_NAME_TEMPLATE = "redisson-delayqueue-scheduler-%d";

    private final RedissonDelayedQueueManager manager;
    private final RedissonDelayedQueueExecutor executor;
    private final ScheduledExecutorService ticker;
    private final ScheduledExecutorService scheduler;

    public CompositeRedissonDelayedQueueScheduler(RedissonDelayedQueueManager manager, RedissonDelayedQueueExecutor executor) {
        this.manager = manager;
        this.executor = executor;
        this.ticker = Executors.newSingleThreadScheduledExecutor();

        this.scheduler = Executors.newScheduledThreadPool(
                Math.min(this.topics().size(), THRESHOLD),
                ThreadFactoryBuilder.create()
                        .setNameFormat(SCHEDULER_NAME_TEMPLATE)
                        .get()
        );
    }

    // ----------------------------------------------------------------

    @Override
    public RedissonProperties redissonProperties() {
        return this.manager.redissonProperties();
    }

    // ----------------------------------------------------------------

    @Override
    public Set<String> topics() {
        return this.redissonProperties().delayed().topics();
    }

    @Override
    public void start() {
        this.schedule();
    }

    @Override
    public void schedule() {
        RedissonProperties.Ticker tickerConf = this.redissonProperties().delayed().ticker();
        this.ticker.scheduleAtFixedRate(this::tick, tickerConf.initialDelay(), tickerConf.period(), tickerConf.unit());
    }

    @Override
    public void stop() {
        this.ticker.shutdown();
        this.scheduler.shutdown();
    }

    // ----------------------------------------------------------------

    @Override
    public void destroy() throws Exception {
        this.stop();
        this.manager.tryAcquirePairs().forEach(QueuePair::destroy);
    }

    // ----------------------------------------------------------------

    private void tick() {
        this.advance();
    }

    public void advance() {
        this.topics().forEach(topic -> {
            this.scheduler.execute(() -> {
                QueuePair pair = this.manager.tryAcquirePair(topic);
                if (!ObjectUtils.isEmpty(pair)) {
                    this.scheduleTopic(pair);
                }
            });
        });
    }

    private void scheduleTopic(QueuePair pair) {
        RedissonProperties.Poll poll = this.redissonProperties().delayed().poll();
        try {
            RedissonDelayedTask<?> delayedTask = null;
            while (null != (delayedTask = pair.blockingQueue().poll(poll.timeout(), poll.unit()))) {
                this.executor.execute(delayedTask);
            }
        } catch (Throwable ignored) {}
    }
}
