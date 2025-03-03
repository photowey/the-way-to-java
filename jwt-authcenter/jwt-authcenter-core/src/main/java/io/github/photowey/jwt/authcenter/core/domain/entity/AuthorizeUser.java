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
package io.github.photowey.jwt.authcenter.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.photowey.jwt.authcenter.core.domain.dto.AuthorizeMobileDTO;
import io.github.photowey.jwt.authcenter.core.domain.dto.AuthorizePasswordDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@code AuthorizeUser}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("auth_authorize_user")
public class AuthorizeUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 3806382209389820816L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long createBy;
    private Long updateBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long userId;
    private String username;
    private String mobile;
    private String password;

    /**
     * 用户主体标识
     */
    private Long principalId;
    /**
     * 用户主体类型 1:平台 2.xxx
     */
    private Integer principalType;

    /**
     * 用户所属平台 saas|local私有部署(默认)
     */
    private String platform;
    /**
     * 客户端 web,iot,mini,app
     */
    private String client;
    /**
     * 用户类型 1: web(系统分配|前端注册) 2: iot 4: mini 8: app
     */
    private Integer userType;
    /**
     * 用户状态 1: 未激活(默认) 2: 已激活 4: 已冻结 8: 已禁止 16: 已过期
     */
    private Integer userStatus;
    /**
     * 授权状态
     * |- 0: 未认证
     * |- 1: 已认证
     */
    private Integer authorizeStatus;

    // ----------------------------------------------------------------

    public void resetUnauthorized() {
        this.authorizeStatus = 0;
    }

    // ----------------------------------------------------------------

    public AuthorizeUser toUpdatePasswordImage() {
        LocalDateTime now = LocalDateTime.now();

        AuthorizeUser authorized = AuthorizeUser.builder()
            .id(this.getId())
            .password(this.getPassword())
            .build();

        return authorized;
    }

    public AuthorizeUser toResetPasswordImage() {
        LocalDateTime now = LocalDateTime.now();

        AuthorizeUser authorized = AuthorizeUser.builder()
            .id(this.getId())
            .password(this.getPassword())
            .build();

        authorized.setUpdatedAt(now);

        return authorized;
    }

    public AuthorizeUser toMobilePasswordImage() {
        LocalDateTime now = LocalDateTime.now();

        AuthorizeUser authorized = AuthorizeUser.builder()
            .id(this.getId())
            .mobile(this.getMobile())
            .build();

        authorized.setUpdatedAt(now);

        return authorized;
    }

    // ----------------------------------------------------------------

    public AuthorizeUser toUnauthorizedImage() {
        LocalDateTime now = LocalDateTime.now();

        AuthorizeUser authorized = AuthorizeUser.builder()
            .id(this.getId())
            .authorizeStatus(this.getAuthorizeStatus())
            .build();

        authorized.setUpdatedAt(now);

        return authorized;
    }

    // ----------------------------------------------------------------

    public AuthorizeMobileDTO toMobile() {
        return AuthorizeMobileDTO.builder()
            .id(this.getId())
            .userId(this.getUserId())
            .username(this.getUsername())
            .mobile(this.getMobile())
            .userStatus(this.getUserStatus())
            .authorizeStatus(this.getAuthorizeStatus())
            .build();
    }

    public AuthorizePasswordDTO toPassword() {
        return AuthorizePasswordDTO.builder()
            .id(this.getId())
            .userId(this.getUserId())
            .username(this.getUsername())
            .mobile(this.getMobile())
            .userStatus(this.getUserStatus())
            .authorizeStatus(this.getAuthorizeStatus())
            .build();
    }
}

