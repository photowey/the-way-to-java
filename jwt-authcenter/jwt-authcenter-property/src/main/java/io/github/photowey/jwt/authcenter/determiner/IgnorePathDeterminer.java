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
package io.github.photowey.jwt.authcenter.determiner;

import io.github.photowey.jwt.authcenter.core.constant.AuthorityConstants;
import io.github.photowey.jwt.authcenter.property.SecurityProperties;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@code IgnorePathDeterminer}.
 *
 * @author photowey
 * @version 2.0.0
 * @since 2025/03/02
 */
public class IgnorePathDeterminer {

    private static final String ANY_REQUEST_ANT = "/**";
    private static final String ANY_REQUEST_PATH = "/*";

    private static final String[] IGNORED_PATHS = AuthorityConstants.DEFAULT_IGNORED_PATHS;

    private static final String[] MUST_IGNORED_PATHS = new String[]{
        "/actuator/**",
        "/healthy",
        "/error",
    };

    private final SecurityProperties securityProperties;

    public IgnorePathDeterminer(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public List<String> determineIgnorePaths() {
        Set<String> webappPaths = new HashSet<>(this.securityProperties.getIgnores().paths());

        // 必须跳过的地址
        for (String mustIgnoredPath : MUST_IGNORED_PATHS) {
            if (!webappPaths.contains(mustIgnoredPath)) {
                webappPaths.add(mustIgnoredPath);
            }
        }

        for (String mustIgnoredPath : IGNORED_PATHS) {
            if (!webappPaths.contains(mustIgnoredPath)) {
                webappPaths.add(mustIgnoredPath);
            }
        }

        return new ArrayList<>(webappPaths);
    }

    public Set<String> determineIgnorePaths(String prefix) {
        return this.determineIgnorePaths(path -> path.startsWith(prefix)
            ? path
            : prefix + path);
    }

    public Set<String> determineIgnorePaths(Function<String, String> fx) {
        return this.determineIgnorePaths().stream().map(fx).collect(Collectors.toSet());
    }

    public List<String> determineSelectPaths() {
        Set<String> selectPaths = new HashSet<>(this.securityProperties.getSelects().paths());
        return new ArrayList<>(selectPaths);
    }

    public String[] determineIgnorePathArray() {
        return this.determineIgnorePaths().toArray(String[]::new);
    }

    public Set<String> determineIgnorePathsByAnyRequestAnt() {
        return this.determineIgnorePaths(ANY_REQUEST_ANT);
    }

    public Set<String> determineIgnorePathsByAnyRequestPath() {
        return this.determineIgnorePaths(ANY_REQUEST_PATH);
    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !CollectionUtils.isEmpty(collection);
    }
}
