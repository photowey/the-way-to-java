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
package io.github.photowey.jwt.authcenter.jwt.loader;

import org.springframework.core.Ordered;

/**
 * {@code NoOpsAuthorizedCacheLoader}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/07
 */
public interface NoOpsAuthorizedCacheLoader extends AuthorizedCacheLoader {

    String NOOPS_LOADER_NAME = "noops";

    @Override
    default int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    default boolean supports() {
        return true;
    }
}

