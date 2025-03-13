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
 * {@code SpringSecurityService}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/13
 */
public interface SpringSecurityService extends SecurityService {

    /**
     * 判断是否有授权域
     *
     * @param scope 授权域
     * @return {@code boolean}
     */
    boolean hasScope(String scope);

    /**
     * 判断是否有授权域
     *
     * @param scopes 授权域列表
     * @return {@code boolean}
     * @since 1.4.0
     */
    boolean hasScopes(String... scopes);

    /**
     * 判断-是否有任一授权域
     *
     * @param scopes 授权域列表
     * @return {@code boolean}
     */
    boolean hasAnyScopes(String... scopes);

    /**
     * 判断
     * 不能包含指定授权域
     *
     * @param scope 授权域
     * @return {@code boolean}
     */
    boolean hasNotScope(String scope);

    /**
     * 判断-不能包含指定的任何一个授权域
     *
     * @param scopes 授权域列表
     * @return {@code boolean}
     */
    boolean hasNotAnyScopes(String... scopes);

    /**
     * 判断-是否是指定的主体类型
     *
     * @param principalType 注定的主体类型
     * @return {@code boolean}
     */
    boolean hasPrincipalType(String principalType);

    /**
     * 判断-是否包含于指定的主体类型列表
     *
     * @param principalTypes 注定的主体类型列表
     * @return {@code boolean}
     */
    boolean hasAnyPrincipalType(String... principalTypes);

    /**
     * 判断-是否不包含于指定的主体类型列表
     *
     * @param principalTypes 注定的主体类型列表
     * @return {@code boolean}
     */
    boolean hasNotAnyPrincipalType(String... principalTypes);
}

