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
package io.github.photowey.redisson.delayed.queue.in.action.pattern;

import org.springframework.util.AntPathMatcher;

/**
 * {@code DefaultDelayedAntPathMatcher}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/11
 */
public class DefaultDelayedAntPathMatcher implements DelayedAntPathMatcher {

    private final AntPathMatcher matcher;

    // ----------------------------------------------------------------

    public static AntPathMatcher createMatcher(boolean caseSensitive) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setTrimTokens(false);
        matcher.setCaseSensitive(caseSensitive);

        return matcher;
    }

    // ----------------------------------------------------------------

    public DefaultDelayedAntPathMatcher(boolean caseSensitive) {
        this.matcher = createMatcher(caseSensitive);
    }

    public DefaultDelayedAntPathMatcher(AntPathMatcher matcher) {
        this.matcher = matcher;
    }

    // ----------------------------------------------------------------

    @Override
    public boolean matches(String pattern, String path) {
        String convertedPattern = this.convertToAntPattern(pattern);
        String convertedPath = this.convertToAntPath(path);

        return this.matcher.match(convertedPattern, convertedPath);
    }

    @Override
    public String toAntPattern(String expression) {
        return PATH_SEPARATOR + expression
                .replaceAll("\\" + MULTI_WORLD, MULTI_PATH)
                .replaceAll(SINGLE_WORLD, SINGLE_PATH)
                .replaceAll("[.:]", PATH_SEPARATOR)
                .replaceAll("/*$", "");
    }

    @Override
    public String toAntPath(String expression) {
        return PATH_SEPARATOR + expression
                .replaceAll("[.:]", PATH_SEPARATOR)
                .replaceAll("/*$", "");
    }

    private String convertToAntPattern(String pattern) {
        return this.toAntPattern(pattern);
    }

    private String convertToAntPath(String path) {
        return this.toAntPath(path);
    }
}