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
package io.github.photowey.jwt.authcenter.security.filter;

import io.github.photowey.jwt.authcenter.core.constant.AuthorityConstants;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.Filter;

/**
 * {@code RootFilter}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/02
 */
public interface RootFilter extends Filter {

    default boolean determineIsBearerToken(String token) {
        return StringUtils.isNotBlank(token)
            && token.startsWith(AuthorityConstants.AUTHORIZATION_TOKEN_PREFIX_BEARER);
    }

    default boolean determineIsNotProxyToken(String token) {
        return !this.determineIsProxyToken(token);
    }

    default boolean determineIsProxyToken(String token) {
        return StringUtils.isNotBlank(token)
            && token.startsWith(AuthorityConstants.AUTHORIZATION_TOKEN_PREFIX_BEARER);
    }
}

