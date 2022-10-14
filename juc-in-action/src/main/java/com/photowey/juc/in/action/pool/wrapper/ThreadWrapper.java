package com.photowey.juc.in.action.pool.wrapper;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * {@code ThreadWrapper}
 *
 * @author photowey
 * @date 2022/10/14
 * @since 1.0.0
 */
@Slf4j
public class ThreadWrapper {

    public static <T> Callable<T> wrap(final Callable<T> callable) {
        return () -> {
            try {
                return callable.call();
            } catch (Throwable e) {
                handleThrowable(e);
                throw e;
            } finally {
                // do something
            }
        };
    }

    public static Runnable wrap(final Runnable runnable) {
        return () -> {
            try {
                runnable.run();
            } finally {
                // do something
            }
        };
    }

    protected static void handleThrowable(Throwable e) {
        log.error("execute async task exception", e);
    }
}
