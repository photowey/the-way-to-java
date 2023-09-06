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
package com.photowey.spring.in.action.ctx.getter;

import com.photowey.commom.in.action.util.ObjectUtils;
import org.springframework.core.env.Environment;

/**
 * {@code EnvironmentGetter}
 *
 * @author photowey
 * @date 2023/09/06
 * @since 1.0.0
 */
public interface EnvironmentGetter {

    String SPRING_PROFILES_ACTIVE_KEY = "spring.profiles.active";

    String SPRING_PROFILES_ACTIVE_DEV = "dev";
    String SPRING_PROFILES_ACTIVE_TEST = "test";
    String SPRING_PROFILES_ACTIVE_PRO = "pro";
    String SPRING_PROFILES_ACTIVE_PROD = "prod";

    default String determineCurrentProfilesActive() {
        return this.environment().getProperty(SPRING_PROFILES_ACTIVE_KEY);
    }

    default boolean determineOffline(String profiles) {
        if (ObjectUtils.isNullOrEmpty(profiles)) {
            return false;
        }

        return profiles.contains(SPRING_PROFILES_ACTIVE_DEV) || profiles.contains(SPRING_PROFILES_ACTIVE_TEST);
    }

    Environment environment();
}
