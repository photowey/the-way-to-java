package com.photowey.netty.telnet.in.action.pool;

import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code ThreadPoolManager}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class ThreadPoolManager {

    private static ConcurrentHashMap<String, NamedThreadPool> threadPoolMap = null;

    public static synchronized void registerThreadPool(String threadPoolName, NamedThreadPool namedThreadPool) {
        if (threadPoolMap == null) {
            threadPoolMap = new ConcurrentHashMap<>(16);
        }
        threadPoolMap.putIfAbsent(threadPoolName, namedThreadPool);
    }

    public static synchronized void unRegisterUserThread(String threadPoolName) {
        if (threadPoolMap != null) {
            threadPoolMap.remove(threadPoolName);
        }
    }

    public static NamedThreadPool getThreadPool(String threadPoolName) {
        return threadPoolMap == null ? null : threadPoolMap.get(threadPoolName);
    }
}