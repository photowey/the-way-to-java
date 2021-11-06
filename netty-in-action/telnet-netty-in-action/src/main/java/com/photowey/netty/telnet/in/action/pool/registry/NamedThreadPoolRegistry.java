package com.photowey.netty.telnet.in.action.pool.registry;

import com.photowey.netty.telnet.in.action.pool.NamedThreadPool;

import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code NamedThreadPoolRegistry}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class NamedThreadPoolRegistry {

    private static ConcurrentHashMap<String, NamedThreadPool> threadPoolMap = null;

    public static void registerThreadPool(String threadPoolName, NamedThreadPool namedThreadPool) {
        if (threadPoolMap == null) {
            synchronized (NamedThreadPoolRegistry.class) {
                if (threadPoolMap == null) {
                    threadPoolMap = new ConcurrentHashMap<>(4);
                }
            }
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
