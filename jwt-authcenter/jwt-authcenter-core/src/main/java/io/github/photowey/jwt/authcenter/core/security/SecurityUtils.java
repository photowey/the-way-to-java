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
package io.github.photowey.jwt.authcenter.core.security;

import io.github.photowey.jwt.authcenter.core.constant.AuthorityConstants;
import io.github.photowey.jwt.authcenter.core.domain.authorized.LoginUser;
import io.github.photowey.jwt.authcenter.core.enums.ExceptionStatus;
import io.github.photowey.jwt.authcenter.core.exception.AuthcenterSecurityException;
import io.github.photowey.jwt.authcenter.core.threadlocal.LoginUserHolder;
import io.github.photowey.jwt.authcenter.core.thrower.AssertionErrorThrower;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * {@code SecurityUtils}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
public final class SecurityUtils {

    private static final String SPACE = " ";

    private SecurityUtils() {
        AssertionErrorThrower.throwz(SecurityUtils.class);
    }

    /**
     * 从默认请求头("Authorization")中解析-颁发的 {@code Token} 信息
     *
     * <pre>
     * Authorization=Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.xxx...
     * </pre>
     *
     * @return {@code Token} 信息
     */
    public static String parseAuthorizationHeader() {
        return parseAuthorizationHeader(
            AuthorityConstants.AUTHORIZATION_HEADER,
            AuthorityConstants.AUTHORIZATION_TOKEN_PREFIX_BEARER
        );
    }

    /**
     * 从指定的请求头(${header})中解析-颁发的 {@code Token} 信息
     *
     * @param header 指定的请求头
     * @return {@code Token} 信息
     */
    public static String parseAuthorizationHeader(String header, String prefix) {
        HttpServletRequest request = RequestUtils.getRequest();
        String authorization = Objects.requireNonNull(request).getHeader(header);
        if (!StringUtils.hasText(authorization)) {
            return null;
        }

        if (authorization.startsWith(prefix + SPACE)
            || authorization.startsWith(prefix.toLowerCase() + SPACE)) {
            return authorization.substring(prefix.length() + 1).trim();
        }

        return null;
    }

    /**
     * 从默认请求头("Authorization")中解析-网关颁发的 {@code Token} 信息
     *
     * <pre>
     * Authorization=Proxy eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.xxx...
     * </pre>
     *
     * @return {@code Token} 信息
     */
    public static String parseProxyHeader() {
        return parseProxyHeader(
            AuthorityConstants.AUTHORIZATION_HEADER,
            AuthorityConstants.AUTHORIZATION_TOKEN_PREFIX_PROXY
        );
    }

    /**
     * 从指定的请求头(${header})中解析-颁发的 {@code Token} 信息
     *
     * @param header 指定的请求头
     * @return {@code Token} 信息
     */
    public static String parseProxyHeader(String header, String prefix) {
        HttpServletRequest request = RequestUtils.getRequest();
        String authorization = Objects.requireNonNull(request).getHeader(header);
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        if (authorization.startsWith(prefix + SPACE)
            || authorization.startsWith(prefix.toLowerCase() + SPACE)) {
            return authorization.substring(prefix.length() + 1).trim();
        }

        return null;
    }

    public static String tryParseProxyHeader() {
        String header = parseProxyHeader();
        if (StringUtils.hasText(header)) {
            return header;
        }

        return parseAuthorizationHeader();
    }

    /**
     * 获取认证对象
     *
     * @return {@link Authentication}
     */
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }

        return context.getAuthentication();
    }

    @Nullable
    public static LoginUser getLoginUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }

        return getLoginUser(authentication);
    }

    public static LoginUser getLoginUser(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        return authentication.getPrincipal() instanceof LoginUser
            ? (LoginUser) authentication.getPrincipal()
            : null;
    }

    /**
     * 必须获取到用户
     * 如果过去不到 - 直接抛出-等同于 401 的异常
     *
     * @return {@code LoginUser}
     */
    public static LoginUser getMustLoginUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            // 如果没有,再次尝试从 ThreadLocal 中去拿取一次
            LoginUser loginUser = LoginUserHolder.get();
            if (loginUser == null) {
                throw new AuthcenterSecurityException(ExceptionStatus.UNAUTHORIZED);
            }

            return loginUser;
        }

        return getLoginUser(authentication);
    }

    /**
     * 获取登录用户标识
     *
     * @return 用户标识
     */
    public static Long getUserId() {
        LoginUser loginUser = getLoginUser();
        return loginUser != null ? loginUser.getUserId() : null;
    }

    /**
     * 设置登录用户信息
     *
     * @param loginUser {@link LoginUser}
     */
    public static void authenticated(LoginUser loginUser) {
        Authentication authentication = buildAuthentication(loginUser);
        authenticated(authentication);
    }

    public static void authenticated(LoginUser loginUser, Object credentials) {
        Authentication authentication = buildAuthentication(loginUser, credentials);
        authenticated(authentication);
    }

    public static void authenticated(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static Authentication buildAuthentication(LoginUser loginUser) {
        return buildAuthentication(loginUser, loginUser.getToken());
    }

    private static Authentication buildAuthentication(LoginUser loginUser, Object credentials) {
        HttpServletRequest request = RequestUtils.getRequest();
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginUser, credentials,
                loginUser.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authenticationToken;
    }
}

