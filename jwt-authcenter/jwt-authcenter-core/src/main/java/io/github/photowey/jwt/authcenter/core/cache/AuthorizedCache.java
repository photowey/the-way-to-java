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
package io.github.photowey.jwt.authcenter.core.cache;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * {@code AuthorizedCache}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizedCache implements Serializable {

    @Serial
    private static final long serialVersionUID = 3641242045109723553L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long authId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String username;
    private String mobile;

    /**
     * 如果用户退出后,该令牌立即失效
     */
    private String token;

    /**
     * 主体标识
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long principalId;
    /**
     * 用户所属主体类型
     */
    private Integer principalType;

    /**
     * 用户类型
     * |- 1: platform
     * |- 2: thirdparty
     * |- 4: temporal
     * |- ...
     */
    private Integer type;
    /**
     * 用户状态
     * |- 1: 未激活(默认) -> 需要修改初始密码等等...
     * |- 2: 已激活
     * |- 4: 已冻结
     * |- 8: 已禁止
     * |- 16: 已过期
     */
    private Integer status;
    /**
     * 认证状态
     * |- 0: 未认证
     * |- 1: 已认证
     */
    private Integer authorizeStatus;

    /**
     * 用户所属平台
     * |- SaaS - 云平台
     * |- local - 私有部署: 默认
     */
    private String platform;
    /**
     * 客户端
     * |- 1: web
     * |- 2: iot
     * |- 4: mini
     * |- 8: app
     * |- ...
     */
    private String client;

    private Set<String> authorities = new HashSet<>();
    private Set<String> scopes = new HashSet<>();
    private Set<String> roles = new HashSet<>();

    // ----------------------------------------------------------------

    public void authorized() {
        this.authorizeStatus = 1;
    }

    // ----------------------------------------------------------------

    public Set<String> authorities() {
        return authorities;
    }

    public Set<String> scopes() {
        return scopes;
    }

    public Set<String> roles() {
        return roles;
    }
}

