/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.crypto.in.action.ras;

import java.io.Serializable;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code KeyPair}
 * 密钥对
 * 由于 {@link KeyPair} 容易个 {@link java.security.KeyPair} 引起误解, 故采用 {@link RsaPair} 替换
 *
 * @author photowey
 * @date 2022/03/03
 * @since 1.0.0
 */
@Deprecated
public class KeyPair implements Serializable {

    private static final long serialVersionUID = -3679107437235147567L;

    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    private PublicKey publicKey;
    private PrivateKey privateKey;

    private final Map<String, Key> keyContext = new HashMap<>(4);

    public KeyPair() {
    }

    public KeyPair(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public KeyPair setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public KeyPair setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public <T extends Key> void publicKey(T key) {
        this.keyContext.put(PUBLIC_KEY, key);
    }

    public <T extends Key> void privateKey(T key) {
        this.keyContext.put(PRIVATE_KEY, key);
    }

    public <T extends Key> T publicKey() {
        return this.get(PUBLIC_KEY);
    }

    public <T extends Key> T privateKey() {
        return this.get(PRIVATE_KEY);
    }

    public <T extends Key> void set(String key, T source) {
        this.keyContext.put(key, source);
    }

    public <T extends Key> T get(String key) {
        return (T) this.keyContext.get(key);
    }
}
