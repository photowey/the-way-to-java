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

/**
 * {@code Option}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/07/22
 */
public abstract class Option<T> {

    private Option() {}

    // ----------------------------------------------------------------

    @SuppressWarnings("all")
    public static <T> Option<T> Some(T value) {
        return new Some<>(value);
    }

    @SuppressWarnings("all")
    public static <T> Option<T> None() {
        return new None<>();
    }

    // ----------------------------------------------------------------

    public abstract boolean isSome();

    public abstract boolean isNone();

    public abstract T unwrap();

    public abstract T unwrapOr(T defaultValue);

    // ----------------------------------------------------------------

    private static class Some<T> extends Option<T> {

        private final T value;

        private Some(T value) {
            this.value = value;
        }

        @Override
        public boolean isSome() {
            return true;
        }

        @Override
        public boolean isNone() {
            return false;
        }

        @Override
        public T unwrap() {
            return value;
        }

        @Override
        public T unwrapOr(T defaultValue) {
            return value;
        }
    }

    // ----------------------------------------------------------------

    private static class None<T> extends Option<T> {

        private None() {}

        @Override
        public boolean isSome() {
            return false;
        }

        @Override
        public boolean isNone() {
            return true;
        }

        @Override
        public T unwrap() {
            throw new RuntimeException("Called unwrap on a None value");
        }

        @Override
        public T unwrapOr(T defaultValue) {
            return defaultValue;
        }
    }
}