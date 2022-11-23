/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.commom.in.action.util;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * {@code StringFormatUtils}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public final class StringFormatUtils {

    private StringFormatUtils() {
        // utility class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    /**
     * 格式化字符串
     *
     * @param text 字符串格式
     * @param args 参数
     * @see FormattingTuple
     * @see MessageFormatter#arrayFormat(String, Object[])
     * @see FormattingTuple#getMessage()
     */
    public static String format(String text, Object... args) {
        assert null != text;
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(text, args);
        return formattingTuple.getMessage();
    }
}