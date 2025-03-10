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
package io.github.photowey.jwt.authcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.photowey.jwt.authcenter.core.domain.authorized.AuthorizedUser;
import io.github.photowey.jwt.authcenter.core.domain.dto.AuthorizeDTO;
import io.github.photowey.jwt.authcenter.core.domain.dto.AuthorizeLogoutDTO;
import io.github.photowey.jwt.authcenter.core.domain.dto.AuthorizeMobileDTO;
import io.github.photowey.jwt.authcenter.core.domain.dto.AuthorizePasswordDTO;
import io.github.photowey.jwt.authcenter.core.domain.entity.AuthorizeUser;
import io.github.photowey.jwt.authcenter.core.domain.payload.*;
import io.github.photowey.jwt.authcenter.core.domain.username.Username;

/**
 * {@code AuthorizeUserService}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/10
 */
public interface AuthorizeUserService extends IService<AuthorizeUser>, BeanFactoryAuthorizeService {

    /**
     * 尝试新增用户
     * |- 账号唯一
     *
     * @param payload {@link AuthorizeAddPayload}
     * @return {@link AuthorizeDTO}
     */
    AuthorizeDTO tryAdd(AuthorizeAddPayload payload);

    /**
     * 更新手机号
     *
     * @param payload {@link AuthorizeMobileUpdatePayload}
     * @return {@link AuthorizePasswordDTO}
     */
    AuthorizeMobileDTO tryUpdateMobile(AuthorizeMobileUpdatePayload payload);

    /**
     * 更新密码
     *
     * @param payload {@link AuthorizeUpdatePasswordPayload}
     * @return {@link AuthorizePasswordDTO}
     */
    AuthorizePasswordDTO tryUpdatePassword(AuthorizeUpdatePasswordPayload payload);

    /**
     * 重置密码
     *
     * @param payload {@link AuthorizeResetPasswordPayload}
     * @return {@link AuthorizePasswordDTO}
     */
    AuthorizePasswordDTO tryResetPassword(AuthorizeResetPasswordPayload payload);

    // ----------------------------------------------------------------

    /**
     * 认证
     * |- 登出
     *
     * @param payload {@link AuthorizeLogoutPayload} 实例
     * @return {@link AuthorizeLogoutDTO} 实例
     */
    AuthorizeLogoutDTO logout(AuthorizeLogoutPayload payload);

    // ----------------------------------------------------------------

    /**
     * 尝试通过提交的用户代理信息查询认证用户
     *
     * @param proxy {@link Username}
     * @return {@link AuthorizedUser}
     */
    AuthorizedUser tryLoad(Username proxy);

    /**
     * 尝试通过用户标识加载用户
     *
     * @param username 用户名称
     * @return {@link AuthorizeUser}
     */
    AuthorizeUser tryLoad(String username);

    /**
     * 尝试通过用户标识加载用户
     *
     * @param userId 用户标识
     * @return {@link AuthorizeUser}
     */
    AuthorizeUser tryLoad(Long userId);
}

