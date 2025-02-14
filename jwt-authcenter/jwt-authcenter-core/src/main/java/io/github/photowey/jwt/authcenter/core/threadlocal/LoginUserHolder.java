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
package io.github.photowey.jwt.authcenter.core.threadlocal;

import io.github.photowey.jwt.authcenter.core.domain.authorized.LoginUser;
import io.github.photowey.jwt.authcenter.core.enums.ExceptionStatus;
import io.github.photowey.jwt.authcenter.core.exception.AuthcenterSecurityException;
import io.github.photowey.jwt.authcenter.core.security.SecurityUtils;
import io.github.photowey.jwt.authcenter.core.thrower.AssertionErrorThrower;

import java.util.Objects;

/**
 * {@code LoginUserHolder}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
public final class LoginUserHolder {

    private static final ThreadLocal<LoginUser> LOGIN_USER_THREAD_LOCAL = new ThreadLocal<>();

    private LoginUserHolder() {
        AssertionErrorThrower.throwz(LoginUserHolder.class);
    }

    public static void set(LoginUser loginUser) {
        LOGIN_USER_THREAD_LOCAL.set(loginUser);
    }

    public static LoginUser get() {
        LoginUser loginUser = LOGIN_USER_THREAD_LOCAL.get();
        if (Objects.nonNull(loginUser)) {
            return loginUser;
        }

        return SecurityUtils.getLoginUser();
    }

    public static LoginUser mustGet() {
        LoginUser loginUser = get();
        if (loginUser == null) {
            throw new AuthcenterSecurityException(ExceptionStatus.UNAUTHORIZED);
        }

        return loginUser;
    }

    public static LoginUser mustAsyncGet() {
        LoginUser loginUser = LOGIN_USER_THREAD_LOCAL.get();
        if (loginUser == null) {
            throw new AuthcenterSecurityException(ExceptionStatus.UNAUTHORIZED, "未认证.async");
        }

        return loginUser;
    }

    public static void clean() {
        LOGIN_USER_THREAD_LOCAL.remove();
    }

    // ----------------------------------------------------------------

    public static void mock() {
        mock(0L);
    }

    public static void mock(Long userId) {
        LoginUser loginUser = populateDummyLoginUser(userId);
        LoginUserHolder.set(loginUser);
    }

    // ----------------------------------------------------------------

    private static LoginUser populateDummyLoginUser(Long userId) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(userId);

        return loginUser;
    }
}
