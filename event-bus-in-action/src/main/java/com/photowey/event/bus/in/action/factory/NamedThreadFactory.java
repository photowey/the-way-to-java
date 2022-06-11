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
package com.photowey.event.bus.in.action.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code NamedThreadFactory}
 *
 * @author photowey
 * @date 2021/11/14
 * @since 1.0.0
 */
public class NamedThreadFactory implements ThreadFactory {

    private final static Map<String, AtomicInteger> PREFIX_COUNTER = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private final String prefix;
    private final int totalSize;
    private final boolean daemon;

    public NamedThreadFactory(String prefix, int totalSize, boolean daemon) {
        PREFIX_COUNTER.putIfAbsent(prefix, new AtomicInteger(0));
        int prefixCounter = PREFIX_COUNTER.get(prefix).incrementAndGet();
        this.prefix = prefix + "_" + prefixCounter;
        this.daemon = daemon;
        this.totalSize = totalSize;
    }

    public NamedThreadFactory(String prefix, boolean daemon) {
        this(prefix, 0, daemon);
    }

    public NamedThreadFactory(String prefix, int totalSize) {
        this(prefix, totalSize, true);
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + "_" + counter.incrementAndGet();
        if (totalSize > 1) {
            name += "_" + totalSize;
        }
        Thread thread = new Thread(r, name);

        thread.setDaemon(daemon);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }

        return thread;
    }
}
