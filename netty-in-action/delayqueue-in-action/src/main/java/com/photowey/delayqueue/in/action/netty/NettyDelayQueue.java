/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.delayqueue.in.action.netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * {@code NettyDelayQueue}
 *
 * @author photowey
 * @date 2023/03/25
 * @since 1.0.0
 */
@Slf4j
public class NettyDelayQueue implements Serializable {

    private static final long serialVersionUID = -840454744524153684L;
    private static final long MILLISECONDS = 1000L;
    private static final String POOL_PREFIX = "netty";

    public final Timer timer;

    public NettyDelayQueue() {
        this(buildPoolName("delay-queue"));
    }

    public NettyDelayQueue(String poolName) {
        this(new DefaultThreadFactory(buildPoolName(poolName)));
    }

    public NettyDelayQueue(ThreadFactory factory) {
        this(factory, 1000L);
    }

    public NettyDelayQueue(ThreadFactory factory, long interval) {
        this(factory, 60, interval);
    }

    public NettyDelayQueue(ThreadFactory factory, int ticks, long interval) {
        this(factory, ticks, interval, TimeUnit.MILLISECONDS);
    }

    public NettyDelayQueue(ThreadFactory factory, int ticks, long interval, TimeUnit timeUnit) {
        this.timer = new HashedWheelTimer(
                factory,
                interval,
                timeUnit,
                ticks,
                true
        );
    }

    public <T> void delayMillis(AbstractNettyDelayedQueueListener<T> timerTask, long delay) {
        this.delayAt(timerTask, delay, TimeUnit.MILLISECONDS);
    }

    public <T> void delaySeconds(AbstractNettyDelayedQueueListener<T> timerTask, long delay) {
        this.delayAt(timerTask, delay, TimeUnit.SECONDS);
    }

    public <T> void delayMinutes(AbstractNettyDelayedQueueListener<T> timerTask, long delay) {
        this.delayAt(timerTask, delay, TimeUnit.MINUTES);
    }

    public <T> void delayHours(AbstractNettyDelayedQueueListener<T> timerTask, long delay) {
        this.delayAt(timerTask, delay, TimeUnit.HOURS);
    }

    public <T> void delayDays(AbstractNettyDelayedQueueListener<T> timerTask, long delay) {
        this.delayAt(timerTask, delay, TimeUnit.DAYS);
    }

    public <T> boolean delayAt(AbstractNettyDelayedQueueListener<T> timerTask, long delay, TimeUnit timeUnit) {
        if (timerTask == null) {
            log.error("timerTask is required");
            return false;
        }
        if (delay < 0) {
            log.error("delay must be > 0");
            return false;
        }
        if (timeUnit == null) {
            log.error("timeUnit is required");
            return false;
        }

        this.offer(timerTask, delay, timeUnit);

        return true;
    }

    public <T> boolean futureAt(AbstractNettyDelayedQueueListener<T> timerTask, LocalDateTime futureAt) {
        if (timerTask == null) {
            log.error("timerTask is required");
            return false;
        }
        if (futureAt == null || futureAt.compareTo(LocalDateTime.now()) < 0) {
            log.error("futureAt is required");
            return false;
        }

        this.offer(timerTask, futureAt);

        return true;
    }

    private <T> void offer(AbstractNettyDelayedQueueListener<T> timerTask, LocalDateTime futureAt) {
        Task<T> task = timerTask.getTask();
        task.setFutureAt(futureAt);
        long now = System.currentTimeMillis();
        long delay = toTimestamp(futureAt) - now;
        this.offerz(timerTask, delay, TimeUnit.MILLISECONDS);
    }

    private <T> void offer(AbstractNettyDelayedQueueListener<T> timerTask, long delay, TimeUnit timeUnit) {
        Task<T> task = timerTask.getTask();
        task.setDelay(delay);
        task.setTimeUnit(timeUnit);

        this.offerz(timerTask, delay, timeUnit);
    }

    private <T> void offerz(AbstractNettyDelayedQueueListener<T> timerTask, long delay, TimeUnit timeUnit) {
        this.timer.newTimeout(timerTask, delay, timeUnit);
    }

    private static Long toTimestamp(LocalDateTime target) {
        return target.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / MILLISECONDS * MILLISECONDS;
    }

    private static String buildPoolName(String name) {
        return name.startsWith(POOL_PREFIX) ? name : POOL_PREFIX + "-" + name;
    }
}
