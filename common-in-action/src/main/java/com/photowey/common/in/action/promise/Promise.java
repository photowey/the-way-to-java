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
package com.photowey.common.in.action.promise;

import java.util.function.Consumer;

/**
 * {@code Promise}
 *
 * @author photowey
 * @date 2024/02/10
 * @since 1.0.0
 */
public final class Promise<T, E extends Throwable> {

    private static final Promise<?, ? extends Throwable> EMPTY = new Promise<>();

    public static <T, E extends Throwable> Promise<T, E> valueOf(T value) {
        if (null == value) {
            return (Promise<T, E>) Promise.EMPTY;
        }

        return new Promise<>(value);
    }

    public static <T, E extends Throwable> Promise<T, E> throwableOf(E throwable) {
        if (null == throwable) {
            return (Promise<T, E>) Promise.EMPTY;
        }

        return new Promise<>(throwable);
    }

    private final T value;
    private final E throwable;

    public Promise() {
        this(null, null);
    }

    public Promise(T value) {
        this(value, null);
    }

    public Promise(E throwable) {
        this(null, throwable);
    }

    public Promise(T value, E throwable) {
        this.value = value;
        this.throwable = throwable;
    }

    public boolean determineIsPresent() {
        return !this.determineIsEmpty();
    }

    public boolean determineIsEmpty() {
        return this.value == null;
    }

    public boolean determineIsFailed() {
        return this.throwable != null;
    }

    public Promise<T, E> then(Consumer<T> resolve) {
        if (this.determineIsPresent()) {
            resolve.accept(this.value);
        }

        return this;
    }

    public Promise<T, E> then(Consumer<T> resolve, Consumer<T> reject) {
        if (this.determineIsPresent()) {
            resolve.accept(this.value);
            return this;
        }

        reject.accept(this.value);

        return this;
    }

    public Promise<T, E> then(Consumer<T> resolve, Consumer<T> reject, Consumer<E> throwable) {
        if (this.determineIsFailed()) {
            throwable.accept(this.throwable);
            return this;
        }

        if (this.determineIsPresent()) {
            resolve.accept(this.value);
            return this;
        }

        reject.accept(this.value);

        return this;
    }

    public Promise<T, E> throwable(Consumer<E> throwable) {
        if (this.determineIsFailed()) {
            throwable.accept(this.throwable);
        }

        return this;
    }

    public T unwrap() {
        return this.value;
    }
}
