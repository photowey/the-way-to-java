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
package io.github.photowey.jwt.authcenter.core.domain.username;

import io.github.photowey.jwt.authcenter.core.constant.AuthorityConstants;
import io.github.photowey.jwt.authcenter.core.formatter.StringFormatter;
import io.github.photowey.jwt.authcenter.core.util.Strings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code Username}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/01/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Username implements Serializable {

    // username + platform + client

    private static final Pattern PT = Pattern.compile("(.*):@:(.*):@:(.*)");

    private String username;
    private String platform;
    private String client;

    public String compress() {
        return StringFormatter.format(
            AuthorityConstants.USER_NAME_ACCOUNT_TEMPLATE,
            this.getUsername(),
            Strings.defaultIfEmpty(this.getPlatform(), AuthorityConstants.PLATFORM_SAAS),
            Strings.defaultIfEmpty(this.getClient(), AuthorityConstants.CLIENT_WEB)
        );
    }

    public static Username parse(String proxy) {
        Matcher matcher = PT.matcher(proxy);
        if (matcher.matches()) {
            return Username.builder()
                .username(matcher.group(1))
                .platform(matcher.group(2))
                .client(matcher.group(3))
                .build();
        }

        throw new RuntimeException("Unreachable here.");
    }

    public String username() {
        return username;
    }

    public String platform() {
        return platform;
    }

    public String client() {
        return client;
    }
}

