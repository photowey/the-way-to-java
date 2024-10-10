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
package com.photowey.common.in.action.future;

import com.photowey.common.in.action.thrower.AssertionErrorThrower;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * {@code Futures}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/10
 */
public final class Futures {

    private Futures() {
        // utility class; can't create
        AssertionErrorThrower.throwz(Futures.class);
    }

    /**
     * 该方法会阻塞,直到获取到结果
     * |- 若出现异常将尝试转换为 {@link RuntimeException}
     *
     * @param future {@code JDK} {@link Future}
     * @param <T>    T 返回类型
     * @return T 类型
     */
    public static <T> T join(Future<T> future) {
        return join(future, Futures::sneakyThrowRuntime);
    }

    /**
     * 该方法会阻塞,直到获取到结果
     * |- WARNING: 如果出现异常会吞并异常并返回 {@code null},请谨慎使用
     *
     * @param future {@code JDK} {@link Future}
     * @param <T>    T 返回类型
     * @return T 类型
     */
    public static <T> T joinOr(Future<T> future) {
        return join(future, (e) -> {});
    }

    /**
     * 该方法会阻塞,直到获取到结果
     * |- 若出现异常,需要开发者采用回调函数自行处理异常
     *
     * @param future {@code JDK} {@link Future}
     * @param fx     异常处理的回调函数
     * @param <T>    T 返回类型
     * @return T 类型
     */
    public static <T> T join(Future<T> future, Consumer<Exception> fx) {
        return joinOr(future, fx, null);
    }

    /**
     * 该方法会阻塞,直到获取到结果
     * |- 若出现异常,需要开发者采用回调函数自行处理异常
     *
     * @param future       {@code JDK} {@link Future}
     * @param fx           异常处理的回调函数
     * @param defaultValue 默认值
     * @param <T>          T 返回类型
     * @return T 类型
     */
    public static <T> T joinOr(Future<T> future, Consumer<Exception> fx, T defaultValue) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fx.accept(e);
        } catch (ExecutionException e) {
            fx.accept(e);
        }

        return defaultValue;
    }

    public static <T extends Throwable> void sneakyThrowRuntime(T t) {
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }

        throw new RuntimeException(t);
    }
}

