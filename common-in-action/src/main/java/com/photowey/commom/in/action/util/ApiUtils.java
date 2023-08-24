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
package com.photowey.commom.in.action.util;

import com.photowey.commom.in.action.thrower.AssertionErrorThrower;

/**
 * {@code ApiUtils}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public final class ApiUtils {

    private ApiUtils() {
        // utility class; can't create
        AssertionErrorThrower.throwz(ApiUtils.class);
    }

    public static String populateApi(String host, String api) {
        // https://www.github.com/
        // /userinfo
        // -> https://www.github.com/userinfo
        return StringFormatUtils.format("{}{}",
                host.endsWith("/") ? host.substring(0, host.length() - 1) : host,
                api.startsWith("/") ? api : "/" + api
        );
    }

    public static String populateArgs(String url, Object... args) {
        return StringFormatUtils.format(url, args);
    }

}