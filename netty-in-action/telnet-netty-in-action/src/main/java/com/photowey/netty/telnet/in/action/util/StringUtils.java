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
package com.photowey.netty.telnet.in.action.util;

import com.photowey.netty.telnet.in.action.constant.NamedConstants;

/**
 * {@code StringUtils}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public final class StringUtils {

    private StringUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static boolean isEmpty(String target) {
        return null == target || target.trim().length() == 0;
    }

    public static boolean isNotEmpty(String target) {
        return !isEmpty(target);
    }

    public static String stripPrefixIfNecessary(String target) {
        if (StringUtils.isEmpty(target)) {
            return NamedConstants.EMPTY_STRING;
        }

        return target.replaceAll("^-*", "");
    }
}