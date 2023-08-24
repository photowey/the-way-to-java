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
package com.photowey.wechat.sdk.core.checker;

import com.photowey.wechat.sdk.core.exception.WechatRequestException;
import com.photowey.wechat.sdk.core.formatter.StringFormatter;

import java.util.Collection;

/**
 * {@code WechatExceptionChecker}
 *
 * @author photowey
 * @date 2023/05/13
 * @since 1.0.0
 */
public abstract class WechatExceptionChecker {

    public static <T> void checkNotNull(T object, String message, Object... args) {
        if (object == null) {
            throwException(message, args);
        }
    }

    public static <T> void checkNotNull(Collection<T> objects, String message, Object... args) {
        if (objects == null || objects.isEmpty()) {
            throwException(message, args);
        }
    }

    public static <T> T throwException(String message, Object... args) {
        throw new WechatRequestException(StringFormatter.format(message, args));
    }
}
