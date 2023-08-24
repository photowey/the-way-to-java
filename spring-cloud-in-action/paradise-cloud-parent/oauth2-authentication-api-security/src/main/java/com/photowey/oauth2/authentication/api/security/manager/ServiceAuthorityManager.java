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
package com.photowey.oauth2.authentication.api.security.manager;

import com.alibaba.fastjson.JSON;
import com.photowey.oauth2.authentication.api.security.head.TokenHead;
import com.photowey.oauth2.authentication.api.security.head.TokenHeadWrapper;
import com.photowey.oauth2.authentication.crypto.util.AESUtils;
import com.photowey.oauth2.authentication.crypto.util.AlgorithmCombine;
import com.photowey.oauth2.authentication.jwt.constant.AuthConstant;
import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * {@code ServiceAuthorityManager}
 *
 * @author photowey
 * @date 2022/01/29
 * @since 1.0.0
 */
public class ServiceAuthorityManager implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String applicationName() {
        return this.environment.getProperty(AuthConstant.APPLICATION_NAME);
    }

    public String sign() {
        return this.sign(this.applicationName());
    }

    public String sign(String serviceName) {
        TokenHead head = this.populateTokenHead(serviceName, AlgorithmCombine.ALGORITHM_SHA256);
        String signHeader = this.handleHeader(head);

        return signHeader;
    }

    private String sign(String signStr, String signType) {
        switch (signType) {
            case AlgorithmCombine.ALGORITHM_MD5:
                return AlgorithmCombine.md5(signStr);
            case AlgorithmCombine.ALGORITHM_SHA256:
                return AlgorithmCombine.sha256(signStr);
            default:
                return AlgorithmCombine.sha256(signStr);
        }
    }

    private TokenHead populateTokenHead(String serviceName, String signType) {
        Assert.notNull(serviceName, "the token userName can't be null");
        signType = StringUtils.hasText(signType) ? signType : AlgorithmCombine.ALGORITHM_SHA256;
        TokenHead head = TokenHead.builder()
                .nonceStr(this.getNonceStr())
                .timeStamp(System.currentTimeMillis() / 1000L)
                .serviceName(serviceName)
                .signType(signType)
                .build();

        return head;
    }

    public TokenHeadWrapper verify(String signHeader) {
        String securityHeader = this.parseSecurityHeader(signHeader);
        Assert.notNull(securityHeader, "the sign header can't be null!");
        String signStr = AESUtils.decrypt(TokenConstants.INNER_TOKEN_AES_KEY, securityHeader);
        TokenHead head = JSON.parseObject(signStr, TokenHead.class);
        String signRequest = head.getSign();
        head.setSign(null);
        String signVerify = this.sign(JSON.toJSONString(head), head.getSignType());
        // TODO 还可以校验时效

        return new TokenHeadWrapper(head, signRequest.equals(signVerify));
    }

    public String getNonceStr() {
        Random random = new SecureRandom();
        return AlgorithmCombine.hash(AlgorithmCombine.ALGORITHM_MD5, String.valueOf(random.nextInt(10000)));
    }

    private String handleHeader(TokenHead head) {
        String headStr = JSON.toJSONString(head);
        String sign = this.sign(headStr, head.getSignType());
        head.setSign(sign);
        String signHeader = JSON.toJSONString(head);

        String securityHeader = this.populateSecurityHeader(signHeader);

        return securityHeader;
    }

    private String populateSecurityHeader(String signHeader) {
        signHeader = AESUtils.encrypt(TokenConstants.INNER_TOKEN_AES_KEY, signHeader);
        String securityHeader = TokenConstants.SERVICE_ISSUE_TOKEN_PREFIX + signHeader;

        return securityHeader;
    }

    private String parseSecurityHeader(String signHeader) {
        String securityHeader = signHeader.replaceAll(TokenConstants.SERVICE_ISSUE_TOKEN_PREFIX, "");
        return securityHeader;
    }
}
