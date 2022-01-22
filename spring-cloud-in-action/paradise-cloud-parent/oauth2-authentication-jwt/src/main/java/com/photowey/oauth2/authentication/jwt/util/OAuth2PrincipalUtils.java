package com.photowey.oauth2.authentication.jwt.util;

import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import com.photowey.oauth2.authentication.jwt.model.principal.PrincipalModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code OAuth2PrincipalUtils}
 *
 * @author photowey
 * @date 2022/01/22
 * @since 1.0.0
 */
public class OAuth2PrincipalUtils {

    private OAuth2PrincipalUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static <T> String principal(T principal) {
        return principal.toString();
    }

    public static <T> PrincipalModel principalModel(T principal) {
        return parsePrincipal(principal(principal));
    }

    public static <T> PrincipalModel obtainPrincipalModel(T principal) {
        return parsePrincipal(principal(principal));
    }

    public static PrincipalModel parsePrincipal(String principal) {
        String template = "(.*)%s(.*)%s(.*)";
        String regex = String.format(template, TokenConstants.PRINCIPAL_DELIMITER, TokenConstants.PRINCIPAL_DELIMITER);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(principal);
        while (matcher.find()) {
            return new PrincipalModel(Long.parseLong(matcher.group(1)), matcher.group(2), matcher.group(3));
        }

        throw new IllegalArgumentException("the principal pattern error,check Please.");
    }

}
