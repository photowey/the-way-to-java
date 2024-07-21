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
package com.photowey.spring.project.infras.in.action.rust;

import java.util.function.Function;

/**
 * {@code Result}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/07/22
 */
public abstract class Result<T, E> {

    private Result() {}

    // ----------------------------------------------------------------

    @SuppressWarnings("all")
    public static <T, E> Result<T, E> Ok(T value) {
        return new Ok<>(value);
    }

    @SuppressWarnings("all")
    public static <T, E> Result<T, E> Err(E error) {
        return new Err<>(error);
    }

    // ----------------------------------------------------------------

    public abstract boolean isOk();

    public abstract boolean isErr();

    public abstract T unwrap();

    public abstract E unwrapErr();

    public abstract T unwrapOr(T defaultValue);

    public abstract <U> U match(Function<? super T, ? extends U> okFunc, Function<? super E, ? extends U> errFunc);

    // ----------------------------------------------------------------

    private static class Ok<T, E> extends Result<T, E> {

        private final T value;

        private Ok(T value) {
            this.value = value;
        }

        @Override
        public boolean isOk() {
            return true;
        }

        @Override
        public boolean isErr() {
            return false;
        }

        @Override
        public T unwrap() {
            return value;
        }

        @Override
        public E unwrapErr() {
            throw new RuntimeException("Called unwrapErr on an Ok value");
        }

        @Override
        public T unwrapOr(T defaultValue) {
            return value;
        }

        @Override
        public <U> U match(Function<? super T, ? extends U> okFunc, Function<? super E, ? extends U> errFunc) {
            return okFunc.apply(value);
        }
    }

    // ----------------------------------------------------------------

    private static class Err<T, E> extends Result<T, E> {

        private final E error;

        private Err(E error) {
            this.error = error;
        }

        @Override
        public boolean isOk() {
            return false;
        }

        @Override
        public boolean isErr() {
            return true;
        }

        @Override
        public T unwrap() {
            throw new RuntimeException("Called unwrap on an Err value");
        }

        @Override
        public E unwrapErr() {
            return error;
        }

        @Override
        public T unwrapOr(T defaultValue) {
            return defaultValue;
        }

        @Override
        public <U> U match(Function<? super T, ? extends U> okFunc, Function<? super E, ? extends U> errFunc) {
            return errFunc.apply(error);
        }
    }
}
