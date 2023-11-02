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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

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
        this.timeWheels[0] = new TimeWheel(60, 1000, null);
        this.timeWheels[1] = new TimeWheel(60, 60000, this.timeWheels[0]);
        this.timeWheels[2] = new TimeWheel(24, 3600000, this.timeWheels[1]);
    }

    public void addTask(TimerTask timerTask) {
        this.timeWheels[0].addTask(timerTask);
    }

    private static class TimeWheel {

        private static final int TASK_QUEUE_CAPACITY_SIZE = 100;
        private static final int TASK_QUEUE_CONSUME_BATCH_SIZE = 10;

        private final int capacity;
        private final long tickMs;
        private final DelayQueue<TimerTask> queue;
        private final TimeWheel overflowWheel;
        private volatile long currentTime;
        private final BlockingQueue<TimerTask> taskQueue = new ArrayBlockingQueue<>(TASK_QUEUE_CAPACITY_SIZE);

        public TimeWheel(int capacity, long tickMs, TimeWheel overflowWheel) {
            this.capacity = capacity;
            this.tickMs = tickMs;
            this.queue = new DelayQueue<>();
            this.overflowWheel = overflowWheel;
            this.currentTime = System.currentTimeMillis();

            Thread workerThread = new Thread(this::advanceClock);
            workerThread.start();
        }

        private void advanceClock() {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(tickMs);
                    currentTime += tickMs;
                    if (overflowWheel != null) {
                        overflowWheel.addTaskFromQueue(queue);
                    } else {
                        consumeExpiredTasks();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        public void addTask(TimerTask timerTask) {
            long expiration = currentTime + timerTask.getDelay(TimeUnit.MILLISECONDS);
            if (timerTask.getExpireMs() < currentTime + tickMs) {
                if (overflowWheel != null) {
                    overflowWheel.addTask(timerTask);
                } else {
                    taskQueue.offer(timerTask);
                }
            } else {
                int virtualId = (int) ((expiration / tickMs) % capacity);
                if (virtualId < capacity) {
                    taskQueue.offer(timerTask);
                }
            }
        }

        private void consumeExpiredTasks() {
            long now = System.currentTimeMillis();
            List<TimerTask> tasksToRun = new ArrayList<>();

            for (int i = 0; i < TASK_QUEUE_CAPACITY_SIZE / TASK_QUEUE_CONSUME_BATCH_SIZE; i++) {
                if (!taskQueue.isEmpty()) {
                    tasksToRun.clear();
                    taskQueue.drainTo(tasksToRun, TASK_QUEUE_CONSUME_BATCH_SIZE);

                    for (TimerTask task : tasksToRun) {
                        if (task.getExpireMs() <= now) {
                            task.run();
                        } else {
                            addTask(task);
                        }
                    }
                }
            }
        }

        public void addTaskFromQueue(DelayQueue<TimerTask> sourceQueue) {
            while (!sourceQueue.isEmpty()) {
                TimerTask task = sourceQueue.poll();
                if (task != null) {
                    addTask(task);
                }
            }
        }
    }
}