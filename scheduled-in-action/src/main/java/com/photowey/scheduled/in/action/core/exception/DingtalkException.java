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
package com.photowey.scheduled.in.action.core.exception;

import com.photowey.common.in.action.formatter.StringFormatter;

/**
 * {@code DingtalkException}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
public class DingtalkException extends RuntimeException {

    public DingtalkException() {
        super();
    }

    public DingtalkException(String message, Object... args) {
        super(StringFormatter.format(message, args));
    }

    public DingtalkException(Throwable cause, String message, Object... args) {
        super(StringFormatter.format(message, args), cause);
    }

    public DingtalkException(Throwable cause) {
        super(cause);
    }

    public DingtalkException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

