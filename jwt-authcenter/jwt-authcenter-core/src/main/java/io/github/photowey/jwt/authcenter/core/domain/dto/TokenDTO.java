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
package io.github.photowey.jwt.authcenter.core.domain.dto;

import io.github.photowey.jwt.authcenter.core.constant.AuthorityConstants;
import io.github.photowey.jwt.authcenter.core.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * {@code TokenDTO}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8216344850436899034L;

    @Schema(
        description = "令牌",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "Bearer eyJhbGciOiJIxxx.eyJzd..."
    )
    private String token;

    @Schema(
        description = "选择器 0:不选择(默认) 1:选择",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "1"
    )
    private Integer selector;

    @Schema(
        description = "令牌类型",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "Bearer"
    )
    private String type;

    @Schema(
        description = "认证平台 saas | local",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "local"
    )
    private String platform;

    @Schema(
        description = "认证客户端 web|iot|mini|app",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "web"
    )
    private String client;

    public String toHeader() {
        return String.join(" ",
            Objects.defaultIfNull(this.getType(), AuthorityConstants.AUTHORIZATION_TOKEN_PREFIX_BEARER),
            this.getToken()
        );
    }
}

