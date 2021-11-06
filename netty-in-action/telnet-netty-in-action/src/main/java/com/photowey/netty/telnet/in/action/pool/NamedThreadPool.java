package com.photowey.netty.telnet.in.action.pool;

import com.photowey.netty.telnet.in.action.pool.factory.BlockingQueueFactory;
import com.photowey.netty.telnet.in.action.pool.factory.NamedThreadFactory;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@code NamedThreadPool}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class NamedThreadPool {

    private transient volatile ThreadPoolExecutor namedExecutor;

    private int corePoolSize = 10;

    private int maximumPoolSize = 100;

    private int keepAliveTime = 300000;

    private int queueSize = 0;

    private String threadPoolName = "named-pool";

    private boolean daemon = false;

    private boolean allowCoreThreadTimeOut = false;

    private boolean preStartAllCoreThreads = false;

    private void init() {
        namedExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                BlockingQueueFactory.buildQueue(queueSize),
                new NamedThreadFactory(threadPoolName, daemon)
        );
        if (allowCoreThreadTimeOut) {
            namedExecutor.allowCoreThreadTimeOut(true);
        }
        if (preStartAllCoreThreads) {
            namedExecutor.prestartAllCoreThreads();
        }
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public NamedThreadPool setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public NamedThreadPool setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
        return this;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public NamedThreadPool setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public NamedThreadPool setQueueSize(int queueSize) {
        this.queueSize = queueSize;
        return this;
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public NamedThreadPool setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
        return this;
    }

    public boolean isDaemon() {
        return daemon;
    }

    public NamedThreadPool setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public boolean isAllowCoreThreadTimeOut() {
        return allowCoreThreadTimeOut;
    }

    public NamedThreadPool setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
        return this;
    }

    public boolean isPreStartAllCoreThreads() {
        return preStartAllCoreThreads;
    }

    public NamedThreadPool setPreStartAllCoreThreads(boolean preStartAllCoreThreads) {
        this.preStartAllCoreThreads = preStartAllCoreThreads;
        return this;
    }

    public ThreadPoolExecutor getExecutor() {
        if (namedExecutor == null) {
            synchronized (this) {
                if (namedExecutor == null) {
                    init();
                }
            }
        }
        return namedExecutor;
    }
}