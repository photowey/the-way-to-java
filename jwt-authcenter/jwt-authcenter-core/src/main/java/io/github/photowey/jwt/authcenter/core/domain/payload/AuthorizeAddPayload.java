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
package io.github.photowey.jwt.authcenter.core.domain.payload;

import io.github.photowey.jwt.authcenter.core.domain.entity.AuthorizeUser;
import io.github.photowey.jwt.authcenter.core.domain.model.EmptyModel;
import lombok.*;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 * {@code AuthorizeAddPayload}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AuthorizeAddPayload extends AbstractPayload<EmptyModel> {

    @Serial
    private static final long serialVersionUID = -8369606748000338668L;

    private Long userId;
    private String username;
    private String mobile;
    private String password;

    private Long principalId;
    private Integer principalType;

    private String platform;
    private String client;
    private Integer userType;

    // ----------------------------------------------------------------

    private String createBy;

    // ----------------------------------------------------------------

    public AuthorizeUser toAuthorizeUser() {
        LocalDateTime now = LocalDateTime.now();

        AuthorizeUser tt = AuthorizeUser.builder()
            .userId(this.userId)
            .username(this.username)
            .mobile(this.mobile)
            .password(this.password)
            .principalId(this.principalId)
            .principalType(this.principalType)
            .platform(this.platform)
            .client(this.client)
            .userType(this.userType)
            .userStatus(2)
            .authorizeStatus(0)
            .build();

        // TODO

        return tt;
    }

    public AuthorizeUser toAuthorizeUser(Consumer<AuthorizeUser> fx) {
        AuthorizeUser tt = this.toAuthorizeUser();
        fx.accept(tt);

        return tt;
    }

    // ----------------------------------------------------------------

    public Long userId() {
        return userId;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String platform() {
        return platform;
    }

    public String client() {
        return client;
    }
}

