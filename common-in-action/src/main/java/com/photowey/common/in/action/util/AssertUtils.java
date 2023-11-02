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
package com.photowey.common.in.action.util;

import com.photowey.common.in.action.thrower.AssertionErrorThrower;

/**
 * {@code AssertUtils}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
public final class AssertUtils {

    private AssertUtils() {
        // utility class; can't create
        AssertionErrorThrower.throwz(AssertUtils.class);
    }

    public static void notNull(final Object target) {
        notNull(target, "Argument:target invalid, check please~");
    }

    public static void notNull(final Object target, String message) {
        if (target == null) {
            throw new NullPointerException(message);
        }
    }
}
