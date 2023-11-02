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
package com.photowey.commom.in.action.timewheel;

/**
 * {@code TimeWheel}
 *
 * @author photowey
 * @date 2023/11/03
 * @since 1.0.0
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TimeWheel {

    private final Object lock = new Object();
    private final ExecutorService executorService;

    private final int size;
    private final DelayQueue<TimerTask> queue;
    private final List<TimerTaskList> buckets;
    private final long tickMs;
    private volatile long currentTime;

    public TimeWheel(int size, long tickMs) {
        this.size = size;
        this.tickMs = tickMs;
        this.buckets = new ArrayList<>(this.size);
        this.queue = new DelayQueue<>();
        this.currentTime = System.currentTimeMillis();
        this.executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < size; i++) {
            buckets.add(new TimerTaskList());
        }

        Thread workerThread = new Thread(this::advanceClock);
        workerThread.start();

        Thread consumerThread = new Thread(this::consumeTasks);
        consumerThread.start();
    }

    private void advanceClock() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(tickMs);
                currentTime += tickMs;
                expireTimers();
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void consumeTasks() {
        while (true) {
            try {
                TimerTask task = queue.take();
                if (task != null && !task.isCancelled()) {
                    executorService.execute(task::run);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void expireTimers() {
        long now = System.currentTimeMillis();
        int index = (int) ((now / tickMs) % size);
        synchronized (lock) {
            TimerTaskList bucket = buckets.get(index);
            LinkedList<TimerTask> tasks;
            synchronized (bucket) {
                tasks = new LinkedList<>(bucket.getTasks());
                bucket.clear();
            }
            LinkedList<TimerTask> reAddTasks = new LinkedList<>();
            for (TimerTask task : tasks) {
                if (!task.isCancelled()) {
                    if (task.getExpireMs() > now) {
                        reAddTasks.add(task);
                    } else {
                        task.run();
                    }
                }
            }
            for (TimerTask task : reAddTasks) {
                addTask(task);
            }
            currentTime = now;
        }
    }

    public void addTask(TimerTask timerTask) {
        long expiration = currentTime + timerTask.getDelay(TimeUnit.MILLISECONDS);
        synchronized (lock) {
            if (timerTask.isCancelled()) {
                return;
            }
            if (timerTask.getExpireMs() < currentTime + tickMs) {
                queue.add(timerTask);
            } else {
                int virtualId = (int) (expiration / tickMs % size);
                TimerTaskList bucket = buckets.get(virtualId);
                synchronized (bucket) {
                    bucket.addTask(timerTask);
                }
            }
        }
    }

    private class TimerTaskList {

        private final LinkedList<TimerTask> timerTasks;

        public TimerTaskList() {
            timerTasks = new LinkedList<>();
        }

        public TimerTaskList(TimerTask timerTask) {
            timerTasks = new LinkedList<>();
            timerTasks.add(timerTask);
        }

        public synchronized void addTask(TimerTask timerTask) {
            timerTasks.add(timerTask);
        }

        public synchronized LinkedList<TimerTask> getTasks() {
            return timerTasks;
        }

        public synchronized void clear() {
            timerTasks.clear();
        }
    }
}




