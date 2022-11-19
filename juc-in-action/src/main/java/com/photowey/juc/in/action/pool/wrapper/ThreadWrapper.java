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
