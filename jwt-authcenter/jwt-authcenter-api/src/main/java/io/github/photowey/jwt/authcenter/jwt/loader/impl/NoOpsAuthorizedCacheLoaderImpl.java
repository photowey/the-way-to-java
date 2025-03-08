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
package io.github.photowey.jwt.authcenter.jwt.loader.impl;

import io.github.photowey.jwt.authcenter.core.cache.AuthorizedCache;
import io.github.photowey.jwt.authcenter.core.util.Collections;
import io.github.photowey.jwt.authcenter.jwt.loader.AbstractAuthorizedCacheLoader;
import io.github.photowey.jwt.authcenter.jwt.loader.NoOpsAuthorizedCacheLoader;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * {@code NoOpsAuthorizedCacheLoaderImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/08
 */
@Component
public class NoOpsAuthorizedCacheLoaderImpl
    extends AbstractAuthorizedCacheLoader implements NoOpsAuthorizedCacheLoader {

    @Override
    public AuthorizedCache load(Long userId) {
        Set<String> emptySet = Collections.emptySet();
        return AuthorizedCache.builder()
            .authId(userId)
            .userId(userId)
            .authorities(emptySet)
            .roles(emptySet)
            .scopes(emptySet)
            .build();
    }
}

