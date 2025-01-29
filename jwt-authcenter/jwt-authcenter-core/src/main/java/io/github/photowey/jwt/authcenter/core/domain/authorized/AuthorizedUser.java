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
package io.github.photowey.jwt.authcenter.core.domain.authorized;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * {@code AuthorizedUser}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/01/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizedUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -7987845243230928922L;

    private Long authId;

    private Long userId;
    private String username;
    private String password;
    private String mobile;

    private Long principalId;
    private Integer principalType;

    // ----------------------------------------------------------------

    private String platform;
    private String client;

    // ----------------------------------------------------------------

    private Integer type;
    private Integer status;
    private Integer authorizeStatus;

    // ----------------------------------------------------------------

    public boolean determineIsPending() {
        return 1 == status;
    }

    public boolean determineIsActivated() {
        return 2 == status;
    }

    public boolean determineIsFrozen() {
        return 4 == status;
    }

    public boolean determineIsForbidden() {
        return 8 == status;
    }

    public boolean determineIsExpired() {
        return 16 == status;
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

    public String password() {
        return password;
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

    public String platform() {
        return platform;
    }

    public String client() {
        return client;
    }

    public Integer type() {
        return type;
    }

    public Integer status() {
        return status;
    }

    public Integer authorizeStatus() {
        return authorizeStatus;
    }
}

