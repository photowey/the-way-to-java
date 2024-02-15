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
package com.photowey.common.in.action.option;

import java.util.function.Supplier;

/**
 * {@code Option<T>}
 *
 * @param <T> T
 * @author photowey
 * @date 2024/02/10
 * @since 1.0.0
 */
public final class Option<T> {

    private static final Option<?> EMPTY = new Option<>();

    public static <T> Option<T> valueOf(T value) {
        if (null == value) {
            return (Option<T>) Option.EMPTY;
        }

        return new Option<>(value);
    }

    private final T value;

    public Option() {
        this(null);
    }

    public Option(T value) {
        this.value = value;
    }

    public boolean isPresent() {
        return !this.isEmpty();
    }

    public boolean isEmpty() {
        return this.value == null;
    }

    public T match(Supplier<String> fx) {
        return this.matches(() -> {
            throw new RuntimeException(fx.get());
        });
    }

    public T matches(OptionHandler<T> fx) {
        if (this.isPresent()) {
            return this.value;
        }

        return fx.handle();
    }

    public T unwrap() {
        return this.value;
    }
}