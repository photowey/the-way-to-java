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
import java.util.function.Function;

/**
 * {@code Result}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/07/22
 */
@SuppressWarnings("all")
public class Result<T, E> {

    private final Optional<T> ok;
    private final Optional<E> err;

    private Result(Optional<T> ok, Optional<E> err) {
        this.ok = ok;
        this.err = err;
    }

    public static <T, E> Result<T, E> Ok(T value) {
        return new Result<>(Optional.of(value), Optional.empty());
    }

    public static <T, E> Result<T, E> Err(E error) {
        return new Result<>(Optional.empty(), Optional.of(error));
    }

    public static <T, E> Result<T, E> Err(String message, Function<String, E> fx) {
        return new Result<>(Optional.empty(), Optional.of(fx.apply(message)));
    }

    public boolean isOk() {
        return ok.isPresent();
    }

    public boolean isErr() {
        return err.isPresent();
    }

    public T unwrap() {
        return ok.orElse(null);
    }

    public E unwrapErr() {
        return err.orElse(null);
    }

    public T expect(String message) {
        return ok.orElseThrow(() -> new RuntimeException(message));
    }

    public T unwrapOr(T defaultValue) {
        return ok.orElse(defaultValue);
    }

    public T unwrapOrElse(Function<? super E, ? extends T> mapper) {
        return ok.orElseGet(() -> mapper.apply(err.orElse(null)));
    }
}