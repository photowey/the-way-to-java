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
package com.photowey.oauth2.authentication.jwt.model.oauth2;

import com.photowey.oauth2.authentication.jwt.model.enums.ConfigLocation;
import com.photowey.oauth2.authentication.jwt.model.enums.ConfigType;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * {@code OAuth2JksProperties}
 *
 * @author photowey
 * @date 2022/01/18
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "oauth2.crypto")
public class OAuth2JksProperties implements Serializable, InitializingBean {

    private static final long serialVersionUID = 3631897829766637643L;

    /**
     * <pre>
     *     $ keytool -genkeypair -alias jwt -keyalg RSA -keypass photowey.jwt.pass -keystore jwt.jks -storepass photowey.jwt.pass
     *     $ keytool -importkeystore -srckeystore jwt.jks -destkeystore jwt.jks -deststoretype pkcs12
     * </pre>
     */
    /**
     * 根据不同的环境 - 配置不同的 {@code jks} 参数
     * e.g.:
     * 1.直接打包在项目内部 - 不推荐
     * security/jks/dev/jwt.jks
     * security/jks/test/jwt.jks
     * security/jks/prod/jwt.jks
     * 2.采用绝对路径
     * 3.直接配置在-配置中心
     */
    private String alias = "jwt";
    private String algorithm = "RAS";
    private String keystore = "security/jks/dev/jwt.jks";
    private String storepass = "photowey.jwt.pass";
    private String keypass = "photowey.jwt.pass";

    private String publicKeyPath = "security/key/%s/publicKey.txt";
    private String privateKeyPath = "security/key/%s/privateKey.txt";
    /**
     * 配置位置
     */
    private ConfigLocation configLocation = ConfigLocation.LOCAL;
    /**
     * 配置类型
     */
    private ConfigType configType = ConfigType.JKS;
    /**
     * 如果配置为 {@link ConfigLocation#CONFIG_CENTER} {@code publicKey} 和 {@code privateKey} 必须有值
     */
    private String publicKey;
    private String privateKey;

    private boolean supportRefreshToken = true;
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 3; // default 30 days.
    private int accessTokenValiditySeconds = 60 * 60 * 2; // default 12 hours.

    @Override
    public void afterPropertiesSet() throws Exception {
        // 配置在配置中心
        if (ConfigLocation.CONFIG_CENTER.equals(this.getConfigLocation())) {
            this.handleByConfigurer();
        } else {
            if (ConfigType.JKS.equals(this.getConfigType())) {
                this.handleByJks();
            } else {
                this.handleByTxt();
            }
        }
    }

    public void handleByTxt() {
        if (StringUtils.isEmpty(this.getPublicKeyPath())) {
            throw new IllegalArgumentException("check and config the oauth2 public-key file path, Please!");
        }
        if (StringUtils.isEmpty(this.getPrivateKeyPath())) {
            throw new IllegalArgumentException("check and config the oauth2 private-key file path, Please!");
        }
    }

    public void handleByJks() {
        if (StringUtils.isEmpty(this.getStorepass())) {
            throw new IllegalArgumentException("check and config the oauth2 jks file storepass, Please!");
        }
        if (StringUtils.isEmpty(this.getKeypass())) {
            throw new IllegalArgumentException("check and config the oauth2 jks file keypass, Please!");
        }
    }

    public void handleByConfigurer() {
        if (StringUtils.isEmpty(this.getPublicKey())) {
            throw new IllegalArgumentException("check and config the oauth2 publicKey, Please!");
        }
        if (StringUtils.isEmpty(this.getPrivateKey())) {
            throw new IllegalArgumentException("check and config the oauth2 privateKey, Please!");
        }
    }
}
