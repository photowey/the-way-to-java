/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.common.thread;

import com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.common.thread.space.ThreadPoolSpace;
import com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.common.utils.StringUtil;

import java.util.Map;
import java.util.concurrent.*;

/**
 * The governor to manager the {@link ThreadPoolExecutor}s
 *
 * @author <a href="mailto:guaner.zzx@alipay.com">Alaneuler</a>
 * Created on 2020/3/17
 */
public class ThreadPoolGovernor {
    private ThreadPoolGovernor() {
    }

    private static final ThreadPoolGovernor INSTANCE = new ThreadPoolGovernor();

    public static String CLASS_NAME = ThreadPoolGovernor.class.getCanonicalName();

    private final ScheduledExecutorService governorScheduler =
            Executors.newScheduledThreadPool(1, new NamedThreadFactory("SOFA-Thread-Pool-Governor"));

    private final ScheduledExecutorService monitorScheduler =
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() + 1, new NamedThreadFactory("SOFA-Thread-Pool-Monitor"));

    private final Object monitor = new Object();

    private final GovernorInfoDumper governorInfoDumper = new GovernorInfoDumper();

    private final ConcurrentHashMap<String, ThreadPoolMonitorWrapper> registry = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, ThreadPoolSpace> spaceNameMap = new ConcurrentHashMap<>();

    private volatile long governorPeriod = 1L;

    private volatile boolean governorLoggable = true;

    private volatile boolean globalMonitorLoggable = true;

    private ScheduledFuture<?> governorScheduledFuture;

    public static ThreadPoolGovernor getInstance() {
        return INSTANCE;
    }

    /**
     * Start the governor info dump task
     */
    public synchronized void startGovernorSchedule() {
        if (governorScheduledFuture == null) {
            governorScheduledFuture = governorScheduler.scheduleAtFixedRate(governorInfoDumper, governorPeriod, governorPeriod, TimeUnit.SECONDS);
        } else {
        }
    }

    /**
     * Stop the governor info dump task
     */
    public synchronized void stopGovernorSchedule() {
        if (governorScheduledFuture != null) {
            governorScheduledFuture.cancel(true);
            governorScheduledFuture = null;
        } else {
        }
    }

    /**
     * Restart the governor info dump task
     */
    private void restartGovernorSchedule() {
        synchronized (monitor) {
            if (governorScheduledFuture != null) {
                governorScheduledFuture.cancel(true);
                governorScheduledFuture = governorScheduler.scheduleAtFixedRate(governorInfoDumper, governorPeriod, governorPeriod, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * The task to dump all {@link ThreadPoolExecutor} registered in the governor
     */
    class GovernorInfoDumper implements Runnable {
        @Override
        public void run() {
            try {
                if (governorLoggable) {
                    for (Map.Entry<String, ThreadPoolMonitorWrapper> entry : registry.entrySet()) {
                        // logging
                    }
                }
            } catch (Throwable e) {
                // logging
            }
        }
    }

    /**
     * The period of the dump task
     *
     * @return the period
     */
    public long getGovernorPeriod() {
        return governorPeriod;
    }

    /**
     * update the period of the dump task, then restart the task
     *
     * @param governorPeriod the dump task period
     */
    public void setGovernorPeriod(long governorPeriod) {
        this.governorPeriod = governorPeriod;
        restartGovernorSchedule();
    }

    /**
     * The log switch of the dump task
     *
     * @return whether log the dump
     */
    public boolean isGovernorLoggable() {
        return governorLoggable;
    }

    /**
     * Update the log switch of the dump task
     *
     * @param governorLoggable whether log the dump
     */
    public void setGovernorLoggable(boolean governorLoggable) {
        this.governorLoggable = governorLoggable;
    }

    /**
     * The log switch of the all monitor task
     *
     * @return whether log monitor task
     */
    public boolean isGlobalMonitorLoggable() {
        return globalMonitorLoggable;
    }

    /**
     * Update the log switch of the all monitor task
     *
     * @param globalMonitorLoggable whether log monitor task
     */
    public void setGlobalMonitorLoggable(boolean globalMonitorLoggable) {
        this.globalMonitorLoggable = globalMonitorLoggable;
    }

    /**
     * Register the {@link ThreadPoolExecutor} with {@link ThreadPoolConfig}
     * and {@link ThreadPoolStatistics} to the governor
     *
     * @param threadPoolExecutor   the base thread pool
     * @param threadPoolConfig     the description of the thread pool
     * @param threadPoolStatistics the running statistics of the thread pool
     */
    public void registerThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor,
                                           ThreadPoolConfig threadPoolConfig,
                                           ThreadPoolStatistics threadPoolStatistics) {
        final String identity = threadPoolConfig.getIdentity();
        if (StringUtil.isEmpty(identity)) {
            return;
        }

        ThreadPoolMonitorWrapper threadPoolMonitorWrapper = new ThreadPoolMonitorWrapper(threadPoolExecutor, threadPoolConfig, threadPoolStatistics);
        if (registry.putIfAbsent(identity, threadPoolMonitorWrapper) != null) {

        } else {
            registry.get(identity).startMonitor();
            final String spaceName = threadPoolConfig.getSpaceName();
            if (StringUtil.isNotEmpty(spaceName)) {
                spaceNameMap.computeIfAbsent(spaceName, k -> new ThreadPoolSpace()).addThreadPool(identity);
            }
        }
    }

    /**
     * Unregister the {@link ThreadPoolExecutor} by it's {@link ThreadPoolConfig}
     *
     * @param threadPoolConfig the description of the thread pool, it's identity is unique
     */
    public void unregisterThreadPoolExecutor(ThreadPoolConfig threadPoolConfig) {
        final String identity = threadPoolConfig.getIdentity();
        if (StringUtil.isEmpty(identity)) {
            return;
        }
        ThreadPoolMonitorWrapper threadPoolMonitorWrapper = registry.remove(identity);
        if (threadPoolMonitorWrapper != null) {
            threadPoolMonitorWrapper.stopMonitor();
        }
        final String spaceName = threadPoolConfig.getSpaceName();
        if (StringUtil.isNotEmpty(spaceName) && spaceNameMap.get(spaceName) != null) {
            spaceNameMap.get(spaceName).removeThreadPool(identity);
        }
    }

    /**
     * Get the {@link ThreadPoolExecutor} by it's identity
     *
     * @param identity the unique identity
     * @return the {@link ThreadPoolExecutor}
     */
    public ThreadPoolExecutor getThreadPoolExecutor(String identity) {
        ThreadPoolMonitorWrapper wrapper = registry.get(identity);
        if (wrapper == null) {
            return null;
        }
        return wrapper.getThreadPoolExecutor();
    }

    /**
     * Get the {@link ThreadPoolMonitorWrapper} by it's identity
     *
     * @param identity the unique identity
     * @return the {@link ThreadPoolMonitorWrapper}
     */
    public ThreadPoolMonitorWrapper getThreadPoolMonitorWrapper(String identity) {
        ThreadPoolMonitorWrapper wrapper = registry.get(identity);
        if (wrapper == null) {
            return null;
        }
        return wrapper;
    }

    /**
     * Start the monitor the {@link ThreadPoolExecutor} registered in the governor
     *
     * @param identity the unique identity
     */
    public void startMonitorThreadPool(String identity) {
        ThreadPoolMonitorWrapper wrapper = registry.get(identity);
        if (wrapper == null) {
            return;
        }
        wrapper.startMonitor();
    }

    /**
     * Stop to monitor the {@link ThreadPoolExecutor} registered in the governor
     *
     * @param identity the unique identity
     */
    public void stopMonitorThreadPool(String identity) {
        ThreadPoolMonitorWrapper wrapper = registry.get(identity);
        if (wrapper == null) {
            return;
        }
        wrapper.stopMonitor();
    }

    /**
     * Restart the monitor the {@link ThreadPoolExecutor} registered in the governor
     *
     * @param identity the unique identity
     */
    public void restartMonitorThreadPool(String identity) {
        ThreadPoolMonitorWrapper wrapper = registry.get(identity);
        if (wrapper == null) {
            return;
        }
        wrapper.restartMonitor();
    }

    /**
     * The thread pool to executor the monitor tasks
     *
     * @return the {@link ScheduledExecutorService}
     */
    public ScheduledExecutorService getMonitorScheduler() {
        return monitorScheduler;
    }

    /**
     * return the spaceName thread pool number，it will increase after witch get
     * return 0 when the spaceName has not registered
     *
     * @param spaceName the spaceName
     * @return the spaceName thread pool number
     */
    public int getSpaceNameThreadPoolNumber(String spaceName) {
        ThreadPoolSpace threadPoolSpace = spaceNameMap.get(spaceName);
        if (threadPoolSpace == null) {
            return 0;
        } else {
            return threadPoolSpace.getThreadPoolNumber();
        }
    }

    /**
     * start monitor all thread pool in the spaceName
     *
     * @param spaceName the spaceName
     */
    public void startMonitorThreadPoolBySpaceName(String spaceName) {
        ThreadPoolSpace threadPoolSpace = spaceNameMap.get(spaceName);
        if (threadPoolSpace == null || threadPoolSpace.getThreadPoolIdentities().isEmpty()) {
            return;
        }
        threadPoolSpace.getThreadPoolIdentities().forEach(this::startMonitorThreadPool);
    }

    /**
     * stop monitor all thread pool in the spaceName
     *
     * @param spaceName the spaceName
     */
    public void stopMonitorThreadPoolBySpaceName(String spaceName) {
        ThreadPoolSpace threadPoolSpace = spaceNameMap.get(spaceName);
        if (threadPoolSpace == null || threadPoolSpace.getThreadPoolIdentities().isEmpty()) {
            return;
        }
        threadPoolSpace.getThreadPoolIdentities().forEach(this::stopMonitorThreadPool);
    }

    /**
     * update the monitor params and restart all thread pool in the spaceName
     *
     * @param spaceName the spaceName
     */
    public void setMonitorThreadPoolBySpaceName(String spaceName, long period) {
        ThreadPoolSpace threadPoolSpace = spaceNameMap.get(spaceName);
        if (threadPoolSpace == null || threadPoolSpace.getThreadPoolIdentities().isEmpty()) {
            return;
        }
        threadPoolSpace.getThreadPoolIdentities().forEach(identity -> {
            ThreadPoolMonitorWrapper wrapper = getThreadPoolMonitorWrapper(identity);
            if (wrapper != null) {
                wrapper.getThreadPoolConfig().setPeriod(period);
                restartMonitorThreadPool(identity);
            }
        });
    }
}
