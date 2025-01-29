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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code PassportUsername}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/01/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassportUsername implements Serializable {

    // authId + userId + username + mobile + principalId + principalType

    private static final Pattern PT =
        Pattern.compile("(.*):@:(.*):@:(.*):@:(.*):@:(.*):@:(.*):@:(.*):@:(.*):@:(.*)");

    private Long authId;
    private Long userId;
    private String username;
    private String mobile;
    private Long principalId;
    private Integer principalType;
    private Integer type;
    private String platform;
    private String client;

    public String compress() {
        return StringFormatter.format(
            AuthorityConstants.PASSPORT_NAME_ACCOUNT_TEMPLATE,
            this.authId(),
            this.userId(),
            this.username(),
            this.mobile(),
            this.principalId(),
            this.principalType(),
            this.type(),
            this.platform(),
            this.client()
        );
    }

    public static PassportUsername parse(String proxy) {
        Matcher matcher = PT.matcher(proxy);
        if (matcher.matches()) {
            return PassportUsername.builder()
                .authId(Long.parseLong(matcher.group(1)))
                .userId(Long.parseLong(matcher.group(2)))
                .username(matcher.group(3))
                .mobile(matcher.group(4))
                .principalId(Long.parseLong(matcher.group(5)))
                .principalType(Integer.parseInt(matcher.group(6)))
                .type(Integer.parseInt(matcher.group(7)))
                .platform(matcher.group(8))
                .client(matcher.group(9))
                .build();
        }

        throw new RuntimeException("Unreachable here.");
    }

    // ----------------------------------------------------------------

    public Long authId() {
        return authId;
    }

    public Long userId() {
        return userId;
    }

    public String username() {
        return username;
    }

    public String mobile() {
        return mobile;
    }

    public Long principalId() {
        return principalId;
    }

    public Integer principalType() {
        return principalType;
    }

    public Integer type() {
        return type;
    }

    public String platform() {
        return platform;
    }

    public String client() {
        return client;
    }
}

