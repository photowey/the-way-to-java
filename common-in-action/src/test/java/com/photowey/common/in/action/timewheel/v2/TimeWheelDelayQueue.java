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
package com.photowey.common.in.action.timewheel.v2;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@code TimeWheelDelayQueue}
 *
 * @author photowey
 * @date 2023/11/03
 * @since 1.0.0
 */
public class TimeWheelDelayQueue {

    private final TimeWheel[] timeWheels;

    public TimeWheelDelayQueue() {
        this.timeWheels = new TimeWheel[3];
        this.timeWheels[0] = new TimeWheel(60, TimeUnit.SECONDS.toMillis(1), null);
        this.timeWheels[1] = new TimeWheel(60, TimeUnit.MINUTES.toMillis(1), this.timeWheels[0]);
        this.timeWheels[2] = new TimeWheel(24, TimeUnit.HOURS.toMillis(1), this.timeWheels[1]);
    }

    public void addTask(TimerTask timerTask) {
        this.timeWheels[0].addTask(timerTask);
    }

    private static class TimeWheel {

        private final int capacity;
        private final long tickMs;
        private final AtomicLong currentTime;

        private final DelayQueue<TimerTask> queue;
        private final TimeWheel overflowWheel;

        public TimeWheel(int capacity, long tickMs, TimeWheel overflowWheel) {
            this.capacity = capacity;
            this.tickMs = tickMs;
            this.queue = new DelayQueue<>();
            this.overflowWheel = overflowWheel;
            this.currentTime = new AtomicLong(System.currentTimeMillis());

            Thread workerThread = new Thread(this::advanceClock);
            workerThread.start();
        }

        private void advanceClock() {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(this.tickMs);
                    this.currentTime.addAndGet(this.tickMs);
                    if (this.overflowWheel != null) {
                        this.overflowWheel.addTaskFromQueue(queue);
                    } else {
                        this.consumeExpiredTasks();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        public void addTask(TimerTask timerTask) {
            long expiration = this.currentTime.get() + timerTask.getDelay(TimeUnit.MILLISECONDS);
            if (timerTask.getExpireMs() < this.currentTime.addAndGet(tickMs)) {
                if (this.overflowWheel != null) {
                    this.overflowWheel.addTask(timerTask);
                } else {
                    this.queue.add(timerTask);
                }
            } else {
                int virtualId = (int) ((expiration / tickMs) % capacity);
                if (virtualId < capacity) {
                    this.queue.add(timerTask);
                }
            }
        }

        private void consumeExpiredTasks() {
            long now = System.currentTimeMillis();
            while (!this.queue.isEmpty() && this.queue.peek().getExpireMs() <= now) {
                TimerTask task = this.queue.poll();
                if (task != null) {
                    if (task.getExpireMs() <= this.currentTime.get()) {
                        task.run();
                    } else {
                        this.addTask(task);
                    }
                }
            }
        }

        public void addTaskFromQueue(DelayQueue<TimerTask> sourceQueue) {
            while (!sourceQueue.isEmpty()) {
                TimerTask task = sourceQueue.poll();
                if (task != null) {
                    this.addTask(task);
                }
            }
        }
    }
}