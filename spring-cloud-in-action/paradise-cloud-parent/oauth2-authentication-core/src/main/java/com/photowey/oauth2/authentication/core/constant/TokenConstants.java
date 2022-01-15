package com.photowey.oauth2.authentication.core.constant;

/**
 * {@code TokenConstants}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
public interface TokenConstants {

    String ROLE_PREFIX = "ROLE_";

    // user id
    String TOKEN_USER_ID = "ui";
    // user name
    String TOKEN_USER_NAME = "un";
    String TOKEN_USER_NAME_UNDERLINE = "user_name";
    // jti
    String TOKEN_JTI = "jti";
    // expired in
    String TOKEN_EXPIRED = "ei";

    String GATEWAY_TOKEN_NAME = "x-inner-oauth-token";
    // principal
    String TOKEN_PRINCIPAL = "pp";
    // authorities
    String TOKEN_AUTHORITIES = "au";
    String TOKEN_AUTHORITIES_FULL = "authorities";

    String AUTHORIZATION_SERVER_PUBLIC_KEY_FILENAME = "authorization-server.pub";
    String AUTHORIZATION_SERVER_TOKEN_KEY_ENDPOINT_URL = "/oauth/token_key";

    String JTI_KEY_PREFIX = "oauth2.black.list:";
    String AUTH_USER_KEY = "com.photowey.oauth2.authentication.core.model.AuthUser";

    String OAUTH_PERMISSION_URLS = "oauth2.permission.urls";
}
