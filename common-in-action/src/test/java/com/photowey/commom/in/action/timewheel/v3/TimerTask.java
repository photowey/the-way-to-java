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
package com.photowey.commom.in.action.timewheel.v3;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * {@code TimerTask}
 *
 * @author photowey
 * @date 2023/11/03
 * @since 1.0.0
 */
public class TimerTask implements Delayed, Runnable {
    private final long expireTime;
    private final Runnable task;

    public TimerTask(long delayMs, Runnable task) {
        this.expireTime = System.currentTimeMillis() + delayMs;
        this.task = task;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        if (other instanceof TimerTask) {
            long diff = this.expireTime - ((TimerTask) other).expireTime;
            return Long.compare(diff, 0);
        }
        return 0;
    }

    public long getExpireMs() {
        return expireTime;
    }

    @Override
    public void run() {
        task.run();
    }
}