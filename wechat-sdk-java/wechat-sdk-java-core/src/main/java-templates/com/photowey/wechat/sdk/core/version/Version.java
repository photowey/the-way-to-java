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
package com.photowey.wechat.sdk.core.version;

/**
 * {@code Version}
 *
 * @author weichangjun
 * @date 2024/02/20
 * @since 1.0.0
 */
public abstract class Version {

    private static final String API_VERSION = "${project.version}";

    private static final String JAVA_VERSION = "${java.version}";

    private Version() {
    }

    public static String api() {
        return API_VERSION.replaceAll("-SNAPSHOT", "");
    }

    public static String java() {
        return JAVA_VERSION.split("_")[0];
    }
}
