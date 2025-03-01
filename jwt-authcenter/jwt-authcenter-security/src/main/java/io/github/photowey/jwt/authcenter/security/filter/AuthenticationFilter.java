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
package io.github.photowey.jwt.authcenter.security.filter;

import io.github.photowey.jwt.authcenter.core.constant.AuthorityConstants;
import io.github.photowey.jwt.authcenter.core.constant.MessageConstants;
import io.github.photowey.jwt.authcenter.core.enums.ExceptionStatus;
import io.github.photowey.jwt.authcenter.core.exception.AuthcenterException;
import io.github.photowey.jwt.authcenter.core.util.security.ResponseUtils;
import io.github.photowey.jwt.authcenter.determiner.IgnorePathDeterminer;
import io.github.photowey.jwt.authcenter.security.matcher.AntPathMatcherCreator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * {@code AuthenticationFilter}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/02
 */
@Slf4j
public class AuthenticationFilter /*extends GenericFilterBean*/
    implements WebsocketFilter, AntPathMatcherCreator, BeanFactoryAware {

    private final UrlPathHelper helper = new UrlPathHelper();
    private final AntPathMatcher requestMatcher = this.createMatcher(true);

    private final IgnorePathDeterminer ignorePathDeterminer;
    private ConfigurableListableBeanFactory beanFactory;

    public AuthenticationFilter(IgnorePathDeterminer ignorePathDeterminer) {
        this.ignorePathDeterminer = ignorePathDeterminer;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (this.determineIsWebSocketRequest(httpRequest)) {
            filterChain.doFilter(request, response);

            return;
        }

        if (this.determineUnauthorizedRequest(httpRequest)) {
            // 抛错: 不接受不授信请求
            // 只接收来自于网关的 Proxy 请求
            // 所有直接提供认证中心对外颁发的 Bearer 令牌均认为不授信
            ResponseUtils.toJSONBody(
                ExceptionStatus.UNAUTHORIZED,
                MessageConstants.AUTHORIZE_REQUEST_INVALID
            );
            return;
        }

        this.tryAuthorizeRequest(request, response, filterChain, httpRequest);
    }

    private boolean determineUnauthorizedRequest(HttpServletRequest request) {
        String authorization =
            Objects.requireNonNull(request).getHeader(AuthorityConstants.AUTHORIZATION_HEADER);
        // 认证中心直接颁发的 Bearer 令牌 默认不守信
        // 只接收来自于网关重新颁发的令牌
        return this.determineIsBearerToken(authorization);
    }

    private void tryAuthorizeRequest(
        ServletRequest request, ServletResponse response,
        FilterChain filterChain, HttpServletRequest httpRequest)
        throws IOException, ServletException {

        String requestPath = this.helper.getLookupPathForRequest(httpRequest);
        String method = httpRequest.getMethod().toUpperCase();

        try {
            // 处理忽略: path
            if (this.determineIsAuthorizeRequest(httpRequest)) {
                this.handleAuth(requestPath);
            } else {
                this.tryParseUserIfNecessary(requestPath);
            }

            this.checkScopes(requestPath);

            filterChain.doFilter(request, response);
        } catch (AuthcenterException e) {
            log.error("authcenter: try.jwt.filter.catch.authenticate.exception: request:[{} {}]",
                method,
                requestPath, e
            );

            ResponseUtils.toJSONBody(ExceptionStatus.UNAUTHORIZED, e.getMessage());
        } catch (Throwable e) {
            log.error("authcenter: try.jwt.filter.catch.unknown.exception: request:[{} {}]",
                method,
                requestPath,
                e
            );
            throw e;
        }
    }

    private void checkScopes(String requestPath) {
        this.checkInitScope(requestPath);
        this.checkSelectScope(requestPath);
    }

    private void checkInitScope(String requestPath) {

    }

    private void checkSelectScope(String requestPath) {
        if (this.determineIsIgnoredRequest(requestPath)) {
            return;
        }

        this.checkContainsSelectScope(requestPath);
        this.checkNotContainsSelectScope(requestPath);
    }

    private void checkContainsSelectScope(String requestPath) {
        List<String> selectPaths = this.determineSelectPaths();
        if (ObjectUtils.isEmpty(selectPaths)) {
            return;
        }

        // NOTE: 暂不处理
    }

    private void checkNotContainsSelectScope(String requestPath) {
        List<String> selectPaths = this.determineSelectPaths();
        if (ObjectUtils.isEmpty(selectPaths)) {
            return;
        }

        // NOTE: 暂不处理
    }

    private void handleAuth(String requestPath) {
        throw new UnsupportedOperationException("Unsupported now");
    }

    private void tryParseUserIfNecessary(String requestPath) {
        throw new UnsupportedOperationException("Unsupported now");
    }

    private boolean determineIsAuthorizeRequest(HttpServletRequest request) {
        return !this.determineIsIgnoredRequest(request);
    }

    private boolean determineIsIgnoredRequest(HttpServletRequest request) {
        String path = this.helper.getLookupPathForRequest(request);
        return this.determineIsIgnoredRequest(path);
    }

    private boolean determineIsIgnoredRequest(String path) {
        List<String> paths = this.determineIgnorePaths();

        for (String ignorePath : paths) {
            boolean matches = this.requestMatcher.match(ignorePath, path);
            if (matches) {
                return true;
            }
        }

        return false;
    }

    private List<String> determineIgnorePaths() {
        throw new UnsupportedOperationException("Unsupported now");
    }

    private List<String> determineSelectPaths() {
        throw new UnsupportedOperationException("Unsupported now");
    }
}

