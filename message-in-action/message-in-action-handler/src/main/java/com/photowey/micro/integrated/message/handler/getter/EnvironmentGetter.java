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
package com.photowey.micro.integrated.message.handler.getter;

import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;

/**
 * {@code EnvironmentGetter}
 *
 * @author photowey
 * @date 2023/09/20
 * @since 1.0.0
 */
public interface EnvironmentGetter {

    String SPRING_PROFILES_ACTIVE_KEY = "spring.profiles.active";

    String APPLICATION_SERVER_PORT_KEY = "server.port";
    String APPLICATION_SERVER_SERVLET_CONTEXT_PATH_KEY = "server.servlet.context-path";
    String APPLICATION_SERVER_SERVLET_CONTEXT_PATH_ROOT = "/";

    String SPRING_PROFILES_ACTIVE_DEV = "dev";
    String SPRING_PROFILES_ACTIVE_TEST = "test";

    String SPRING_PROFILES_ACTIVE_PRO = "pro";
    String SPRING_PROFILES_ACTIVE_PROD = "prod";

    /**
     * GET {@link Environment}
     *
     * @return {@link Environment}
     */
    default Environment environment() {
        return null;
    }

    default String determineProfilesActive() {
        return this.environment().getProperty(SPRING_PROFILES_ACTIVE_KEY);
    }

    default String determineServerServletContextPath() {
        return this.environment().getProperty(APPLICATION_SERVER_SERVLET_CONTEXT_PATH_KEY, APPLICATION_SERVER_SERVLET_CONTEXT_PATH_ROOT);
    }

    default boolean determineDeveloping(String profiles) {
        if (ObjectUtils.isEmpty(profiles)) {
            return false;
        }

        return profiles.contains(SPRING_PROFILES_ACTIVE_DEV) || profiles.contains(SPRING_PROFILES_ACTIVE_TEST);
    }

    default int determineServerPort() {
        return Integer.parseInt(this.environment().getProperty(APPLICATION_SERVER_PORT_KEY));
    }
}
