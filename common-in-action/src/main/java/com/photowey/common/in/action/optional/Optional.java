/*
 * Copyright (C) 2021-2023 Thomas Akehurst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.common.in.action.optional;

import java.util.function.Supplier;

/**
 * {@code Optional}
 *
 * @author photowey
 * @date 2024/02/10
 * @since 1.0.0
 */
public final class Optional<T> {

    private static final Optional<?> EMPTY = new Optional<>();

    public static <T> Optional<T> valueOf(T value) {
        if (null == value) {
            return (Optional<T>) Optional.EMPTY;
        }

        return new Optional<>(value);
    }

    private final T value;

    public Optional() {
        this(null);
    }

    public Optional(T value) {
        this.value = value;
    }

    public boolean determineIsPresent() {
        return !this.determineIsEmpty();
    }

    public boolean determineIsEmpty() {
        return this.value == null;
    }

    public T match(Supplier<String> fx) {
        if (this.determineIsPresent()) {
            return this.value;
        }

        throw new RuntimeException(fx.get());
    }
}
