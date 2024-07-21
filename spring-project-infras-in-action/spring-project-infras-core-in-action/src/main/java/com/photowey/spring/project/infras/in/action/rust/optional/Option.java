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
package com.photowey.spring.project.infras.in.action.rust.optional;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * {@code Option}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/07/22
 */
public class Option<T> {

    private final Optional<T> value;

    private Option(Optional<T> value) {
        this.value = value;
    }

    @SuppressWarnings("all")
    public static <T> Option<T> Some(T value) {
        return new Option<>(Optional.of(value));
    }

    @SuppressWarnings("all")
    public static <T> Option<T> None() {
        return new Option<>(Optional.empty());
    }

    public boolean isSome() {
        return value.isPresent();
    }

    public boolean isNone() {
        return value.isEmpty();
    }

    public T unwrap() {
        return value.get();
    }

    public T expect(String message) {
        return value.orElseThrow(() -> new RuntimeException(message));
    }

    public T unwrapOr(T defaultValue) {
        return value.orElse(defaultValue);
    }

    public T unwrapOrElse(Supplier<? extends T> supplier) {
        return value.orElseGet(supplier);
    }
}