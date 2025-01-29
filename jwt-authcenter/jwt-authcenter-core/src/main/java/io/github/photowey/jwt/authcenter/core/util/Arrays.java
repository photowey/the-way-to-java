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
package io.github.photowey.jwt.authcenter.core.util;

import io.github.photowey.jwt.authcenter.core.thrower.AssertionErrorThrower;

/**
 * {@code Arrays}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/01/29
 */
public final class Arrays {

    private Arrays() {
        // utility class; can't create
        AssertionErrorThrower.throwz(Arrays.class);
    }

    // ----------------------------------------------------------------

    public static <T> boolean isEmpty(T[] elements) {
        return null == elements || elements.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] elements) {
        return !isEmpty(elements);
    }
}
