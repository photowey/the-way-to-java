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
package com.photowey.common.in.action.timewheel;

import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * {@code TimerTask}
 *
 * @author photowey
 * @date 2023/11/03
 * @since 1.0.0
 */
@Data
public class TimerTask implements Delayed {

    private final Runnable task;
    private final long delayMs;
    private final long expireMs;
    private boolean cancelled;

    public TimerTask(Runnable task, long delayMs) {
        this.task = task;
        this.delayMs = delayMs;
        this.expireMs = System.currentTimeMillis() + delayMs;
        this.cancelled = false;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = expireMs - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.expireMs < ((TimerTask) o).expireMs) {
            return -1;
        }
        if (this.expireMs > ((TimerTask) o).expireMs) {
            return 1;
        }
        return 0;
    }

    public void run() {
        if (!isCancelled()) {
            task.run();
        }
    }

    public synchronized boolean isCancelled() {
        return cancelled;
    }

    public synchronized void cancel() {
        cancelled = true;
    }
}