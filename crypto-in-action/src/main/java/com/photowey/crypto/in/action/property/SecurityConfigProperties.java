/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.crypto.in.action.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.Map;

/**
 * {@code SecurityConfigProperties}
 *
 * @author photowey
 * @date 2022/02/07
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "crypto.security")
public class SecurityConfigProperties implements Serializable {

    private static final long serialVersionUID = 7907529498709566589L;
    private String platformPublicKey;
    private String platformPrivateKey;
    private String keyStorePath;
    private String aesKey;
    private Map<String, Item> params;

    public static class Item implements Serializable {
        private static final long serialVersionUID = -4473402181307219393L;
        private String cert;
        private String aesKey;

        public String getCert() {
            return cert;
        }

        public void setCert(String cert) {
            this.cert = cert;
        }

        public String getAesKey() {
            return aesKey;
        }

        public void setAesKey(String aesKey) {
            this.aesKey = aesKey;
        }
    }

    public String getPlatformPublicKey() {
        return platformPublicKey;
    }

    public void setPlatformPublicKey(String platformPublicKey) {
        this.platformPublicKey = platformPublicKey;
    }

    public String getPlatformPrivateKey() {
        return platformPrivateKey;
    }

    public void setPlatformPrivateKey(String platformPrivateKey) {
        this.platformPrivateKey = platformPrivateKey;
    }

    public String getKeyStorePath() {
        return keyStorePath;
    }

    public void setKeyStorePath(String keyStorePath) {
        this.keyStorePath = keyStorePath;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public Map<String, Item> getParams() {
        return params;
    }

    public void setParams(Map<String, Item> params) {
        this.params = params;
    }
}