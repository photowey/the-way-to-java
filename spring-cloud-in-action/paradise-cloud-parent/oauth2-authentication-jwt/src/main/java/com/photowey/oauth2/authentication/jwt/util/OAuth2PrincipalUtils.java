/*
 * Copyright © 2021 the original author or authors.
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
