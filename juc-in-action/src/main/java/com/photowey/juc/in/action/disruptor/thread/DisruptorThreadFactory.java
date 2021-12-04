/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.juc.in.action.disruptor.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@code DisruptorThreadFactory}
 *
 * @author photowey
 * @date 2021/12/04
 * @since 1.0.0
 */
public final class DisruptorThreadFactory implements ThreadFactory {

    private static final AtomicLong THREAD_NUMBER = new AtomicLong(1);
    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("disruptor");

    private final boolean daemon;
    private final String prefix;
    private final int priority;

    private DisruptorThreadFactory(final String prefix, final boolean daemon, final int priority) {
        this.prefix = prefix;
        this.daemon = daemon;
        this.priority = priority;
    }

    public static ThreadFactory create(final String prefix, final boolean daemon) {
        return create(prefix, daemon, Thread.NORM_PRIORITY);
    }

    public static ThreadFactory create(final String prefix, final boolean daemon, final int priority) {
        return new DisruptorThreadFactory(prefix, daemon, priority);
    }

    @Override
    public Thread newThread(final Runnable runnable) {
        Thread thread = new Thread(THREAD_GROUP, runnable, THREAD_GROUP.getName() + "-" + prefix + "-" + THREAD_NUMBER.getAndIncrement());
        thread.setDaemon(daemon);
        thread.setPriority(priority);
        return thread;
    }
}
