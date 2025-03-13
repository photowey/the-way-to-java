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
package io.github.photowey.jwt.authcenter.security.service;

/**
 * {@code SecurityService}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/13
 */
public interface SecurityService {

    /**
     * 判断
     * 账号是否有权限
     *
     * @param account 账号
     * @return {@code boolean}
     */
    boolean hasAccount(String account);

    /**
     * 判断
     * 账号是否有权限，任一一个即可
     *
     * @param accounts 权限列表
     * @return {@code boolean}
     */
    boolean hasAccounts(String... accounts);

    /**
     * 判断是否有权限
     *
     * @param permission 权限
     * @return {@code boolean}
     */
    boolean hasPermission(String permission);

    /**
     * 判断是否有权限，任一一个即可
     *
     * @param permissions 权限
     * @return {@code boolean}
     */
    boolean hasAnyPermissions(String... permissions);

    /**
     * 判断是否有角色
     *
     * @param role 角色
     * @return {@code boolean}
     */
    boolean hasRole(String role);

    /**
     * 判断是否有角色，任一一个即可
     *
     * @param roles 角色数组
     * @return {@code boolean}
     */
    boolean hasAnyRoles(String... roles);
}

