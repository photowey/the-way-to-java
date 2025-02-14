/*
 * Copyright © 2025 the original author or authors.
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
package io.github.photowey.jwt.authcenter.core.exception;

import io.github.photowey.jwt.authcenter.core.thrower.AssertionErrorThrower;

import java.util.Objects;

/**
 * {@code Exceptions}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
public final class Exceptions {

    private Exceptions() {
        AssertionErrorThrower.throwz(Exceptions.class);
    }

    public static <T> T checkNPE(T object) {
        if (Objects.isNull(object)) {
            throw new NullPointerException();
        }

        return object;
    }

    public static <T> T checkNPE(T object, String message, Object... args) {
        if (Objects.isNull(object)) {
            throw new NullPointerException(String.format(message, args));
        }

        return object;
    }

    // ----------------------------------------------------------------

    public static <T> T throwUnchecked(final Throwable ex, final Class<T> returnType) {
        throwsUnchecked(ex);
        throw new AssertionError("unchecked: this code should be unreachable  here!");
    }

    public static <T> T throwUnchecked(final Throwable ex) {
        return throwUnchecked(ex, null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwsUnchecked(Throwable throwable) throws T {
        throw (T) throwable;
    }
}
