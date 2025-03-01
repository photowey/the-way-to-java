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
package io.github.photowey.jwt.authcenter.core.constant;

import java.util.regex.Pattern;

/**
 * {@code AuthorityConstants}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/01/28
 */
public interface AuthorityConstants {

    // ---------------------------------------------------------- protocol

    String PROTOCOL_TEMPLATE = "{}{}";
    String ACCOUNT_PROTOCOL = "account://";
    String MOBILE_PROTOCOL = "mobile://";
    String REMOTE_PROTOCOL = "remote://";
    String THIRD_PARTY_PROTOCOL = "thirdparty://";

    // ---------------------------------------------------------- Prefix

    String PASSPORT_CLIENT_TENANT_MINIGRAM_PREFIX = "mini";
    String PASSPORT_CLIENT_TENANT_WEB_PREFIX = "web";
    String PASSPORT_CLIENT_TENANT_IOT_PREFIX = "iot";

    // ---------------------------------------------------------- Principal

    String AUTHORIZATION_PRINCIPAL_ID = "pi";
    String AUTHORIZATION_PRINCIPAL_TYPE = "pt";

    // ---------------------------------------------------------- Security

    String SPRING_SECURITY_AUTHORITY_PREFIX = "ROLE_";
    String SPRING_OAUTH2_SCOPE_PREFIX = "SCOPE_";

    String AUTHORITY_SCOPE_KEY = "scopes";
    String AUTHORITY_ROLE_KEY = "roles";

    String WEB_AUTH_SCOPE = "web";
    String IOT_AUTH_SCOPE = "iot";

    String MINI_AUTH_SCOPE = "mini";
    String MINI_INIT_SCOPE = "init";

    String PASSWORD_MODE_AUTH_SCOPE = "pwd";

    String NORMAL_AUTH_ROLE = "nm";
    String DEVICE_AUTH_ROLE = "io";
    String MINI_AUTH_ROLE = "mn";
    String APP_AUTH_ROLE = "ap";

    // ---------------------------------------------------------- Authorization

    String AUTHORIZATION_HEADER = "Authorization";

    String AUTHORIZATION_TOKEN_PREFIX_BEARER = "Bearer";
    String AUTHORIZATION_TOKEN_PREFIX_PROXY = "Proxy";
    String AUTHORIZATION_TOKEN_PREFIX_FIXED = "Fixed";
    String AUTHORIZATION_TOKEN_PREFIX_IOT = "Iot";

    // ---------------------------------------------------------- Authority

    String AUTHORITY_ISSUER_KEY = "iss";
    String AUTHORITY_ISSUE_AT_KEY = "iat";
    String AUTHORITY_JWT_ID_KEY = "jti";
    String AUTHORITY_AUDIT_KEY = "aud";
    String AUTHORITY_CLIENT_KEY = "xoc";
    String AUTHORITY_AUTH_KEY = "ath";

    // ---------------------------------------------------------- Header

    String TENANT_HEADER = "Tenant";
    String USER_AGENT_HEADER = "User-Agent";

    // ----------------------------------------------------------

    /**
     * SaaS deploy
     */
    String PLATFORM_SAAS = "saas";
    /**
     * Local deploy
     */
    String PLATFORM_LOCAL = "local";

    // ----------------------------------------------------------

    String CLIENT_DEVICE = "iot";
    String CLIENT_WEB = "web";
    String CLIENT_MINI = "mini";
    String CLIENT_APP = "app";

    String USER_NAME_ACCOUNT_TEMPLATE = "{}:@:{}:@:{}";
    String USER_NAME_ACCOUNT_SEPARATOR = ":@:";

    /**
     * authId + userId + username + mobile + principalId + principalType
     */
    String PASSPORT_NAME_ACCOUNT_TEMPLATE = "{}:@:{}:@:{}:@:{}:@:{}:@:{}:@:{}:@:{}:@:{}";

    // ---------------------------------------------------------- Password

    String DEFAULT_PASSWORD = "$2a$10$u5kk6emmJX7yjW8jgkess.r5/h.SLIL0e0IWDRR4lfQiodGn/lirm";

    // ---------------------------------------------------------- Token

    String TOKEN_ALGO_HS512 = "HmacSHA512";

    // ---------------------------------------------------------- Ordered

    int TENANT_INJECT_ORDERED = 3;
    int AUTHENTICATED_ORDERED = 5;

    long DUMMY_LOGIN_USER_ID = -1L;

    // ----------------------------------------------------------------

    Pattern BCRYPT_HASH_PATTERN = Pattern.compile("^\\$2[aby]\\$");

    // ---------------------------------------------------------- Ignore Paths

    String[] DEFAULT_IGNORED_PATHS = new String[]{
        "/favicon.ico",
        "/webjars/**",
        "/v2/api-docs",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/doc.html",
        "/swagger-ui.html",
        "/login/**",
        "/authorize/**",
        "/actuator/**",
        "/healthy",
        "/error"
    };
}

