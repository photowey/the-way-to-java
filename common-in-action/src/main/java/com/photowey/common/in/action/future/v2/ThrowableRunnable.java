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
package com.photowey.common.in.action.future.v2;

/**
 * {@code ThrowableRunnable}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/12/16
 */
@FunctionalInterface
public interface ThrowableRunnable {

    static ThrowableRunnable of(ThrowableRunnable ref) {
        return ref;
    }

    void run() throws Throwable;

    default Runnable unchecked() {
        return () -> {
            try {
                run();
            } catch (Throwable x) {
                ThrowableRunnableThrower.sneakyThrow(x);
            }
        };
    }
}

interface ThrowableRunnableThrower {

    /**
     * 抛出异常
     *
     * @param t   {@link Throwable}
     * @param <T> T 异常类型
     * @param <R> 响应类型
     * @return R 类型
     * @throws T 具体异常类型
     */
    @SuppressWarnings("unchecked")
    static <T extends Throwable, R> R sneakyThrow(Throwable t) throws T {
        throw (T) t;
    }

}
