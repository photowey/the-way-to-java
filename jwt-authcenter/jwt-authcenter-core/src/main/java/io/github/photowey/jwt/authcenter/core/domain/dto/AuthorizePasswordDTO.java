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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * {@code AuthorizePasswordDTO}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizePasswordDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -286899192214545377L;

    @Schema(
        description = "认证标识",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "9527"
    )
    private Long id;

    @Schema(
        description = "用户标识",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "1024"
    )
    private Long userId;

    @Schema(
        description = "用户名",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "admin"
    )
    private String username;

    @Schema(
        description = "手机号",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "13993993939"
    )
    private String mobile;

    @Schema(
        description = "用户状态 1: 未激活(默认) 2: 已激活 4: 已冻结 8: 已禁止 16: 已过期",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "2"
    )
    private Integer userStatus;

    @Schema(
        description = "授权状态 0: 未认证 1: 已认证",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "2"
    )
    private Integer authorizeStatus;
}

