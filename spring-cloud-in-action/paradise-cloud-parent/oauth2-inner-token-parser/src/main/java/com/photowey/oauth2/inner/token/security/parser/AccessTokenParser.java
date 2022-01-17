package com.photowey.oauth2.inner.token.security.parser;

import com.alibaba.fastjson.JSON;
import com.photowey.oauth2.authentication.jwt.model.AuthUser;
import com.photowey.oauth2.authentication.jwt.model.token.InnerToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * {@code AccessTokenParser}
 *
 * @author photowey
 * @date 2022/01/17
 * @since 1.0.0
 */
public class AccessTokenParser {

    private static final Logger log = LoggerFactory.getLogger(AccessTokenParser.class);

    public AuthUser parseAccessToken(String accessToken) {
        // TODO 先不考虑加解密问题-只考虑 编解码
        try {
            String passport = new String(Base64Utils.decodeFromString(accessToken), StandardCharsets.UTF_8);
            InnerToken innerToken = JSON.parseObject(passport, InnerToken.class);

            return this.populateAuthUser(innerToken);
        } catch (Exception e) {
            log.error("Parse the gateway issue inner-token exception", e);
            throw new IllegalStateException("Parse the gateway issue inner-token error", e);
        }
    }

    private AuthUser populateAuthUser(InnerToken passport) {
        AuthUser authUser = new AuthUser();
        authUser.setUserId(passport.getUi());
        authUser.setUserName(passport.getUn());
        authUser.setAuthorities(Arrays.asList(StringUtils.delimitedListToStringArray(passport.getAu(), ",")));
        authUser.setJti(passport.getJti());
        authUser.setExpiredIn(passport.getEi());

        return authUser;
    }

}
