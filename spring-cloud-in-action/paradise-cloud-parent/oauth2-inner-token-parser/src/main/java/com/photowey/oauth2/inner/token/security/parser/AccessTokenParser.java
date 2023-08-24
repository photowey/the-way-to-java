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
