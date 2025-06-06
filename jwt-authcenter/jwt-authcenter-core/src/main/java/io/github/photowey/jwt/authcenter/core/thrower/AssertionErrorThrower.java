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
package io.github.photowey.jwt.authcenter.core.thrower;

/**
 * {@code AssertionErrorThrower}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/01/28
 */
public final class AssertionErrorThrower {

    private AssertionErrorThrower() {
        // utility class; can't create
        throwz(AssertionErrorThrower.class);
    }

    public static <T> void throwz(Class<T> clazz) {
        throw new AssertionError("No " + clazz.getName() + " instances for you!");
    }
}
