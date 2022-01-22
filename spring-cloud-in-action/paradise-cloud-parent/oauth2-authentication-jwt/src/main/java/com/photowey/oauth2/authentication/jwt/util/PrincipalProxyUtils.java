package com.photowey.oauth2.authentication.jwt.util;

import com.photowey.oauth2.authentication.crypto.util.AESUtils;
import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;

/**
 * {@code PrincipalProxyUtils}
 *
 * @author photowey
 * @date 2022/01/22
 * @since 1.0.0
 */
public final class PrincipalProxyUtils {

    private PrincipalProxyUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static String populateNormalPrincipal(String principal) {
        return populatePrincipal(TokenConstants.USER_NAME_NORMAL_PREFIX, principal);
    }

    public static String populateProxyPrincipal(String principal) {
        return populatePrincipal(TokenConstants.USER_NAME_PROXY_PREFIX, principal);
    }

    public static String parseNormalPrincipal(String principal) {
        return parsePrincipal(TokenConstants.USER_NAME_NORMAL_PREFIX, principal);
    }

    public static String parseProxyPrincipal(String principal) {
        return parsePrincipal(TokenConstants.USER_NAME_PROXY_PREFIX, principal);
    }

    public static String determineOriginalPrincipal(String principal) {
        if (principal.startsWith(TokenConstants.USER_NAME_NORMAL_PREFIX)
                || principal.startsWith(TokenConstants.USER_NAME_PROXY_PREFIX)) {
            String parsePrincipal = parsePrincipal(TokenConstants.USER_NAME_PROXY_PREFIX, parsePrincipal(TokenConstants.USER_NAME_NORMAL_PREFIX, principal));
            return AESUtils.decrypt(TokenConstants.USER_NAME_AES_KEY, parsePrincipal);
        }

        return principal;
    }

    private static String parsePrincipal(String prefix, String principal) {
        return principal.replaceAll(prefix, "");
    }

    private static String populatePrincipal(String prefix, String principal) {
        return prefix + principal;
    }

}
