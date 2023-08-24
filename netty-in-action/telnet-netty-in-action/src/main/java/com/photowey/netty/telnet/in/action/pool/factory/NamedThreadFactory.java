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
package com.photowey.netty.telnet.in.action.pool.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code NamedThreadFactory}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger POOL_COUNT = new AtomicInteger();

    private final static String FIRST_PREFIX = "photowey-";

    private final AtomicInteger threadCount = new AtomicInteger(1);

    private final ThreadGroup group;

    private final String prefix;

    private final boolean isDaemon;

    public NamedThreadFactory(String secondPrefix) {
        this(secondPrefix, false);
    }

    public NamedThreadFactory(String secondPrefix, boolean daemon) {
        SecurityManager sm = System.getSecurityManager();
        group = (sm != null) ? sm.getThreadGroup() : Thread.currentThread().getThreadGroup();
        prefix = FIRST_PREFIX + secondPrefix + "-" + POOL_COUNT.getAndIncrement() + "-T";
        isDaemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, prefix + threadCount.getAndIncrement(), 0);
        t.setDaemon(isDaemon);
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}