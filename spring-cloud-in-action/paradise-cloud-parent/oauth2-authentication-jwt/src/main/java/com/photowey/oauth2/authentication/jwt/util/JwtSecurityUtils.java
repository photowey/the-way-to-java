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
package com.photowey.oauth2.authentication.jwt.util;

import com.photowey.oauth2.authentication.crypto.util.Base64Utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * {@code JwtSecurityUtils}
 *
 * @author photowey
 * @date 2022/01/18
 * @since 1.0.0
 */
public final class JwtSecurityUtils {

    protected static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";
    protected static final String SHA_WITH_RSA = "SHA1withRSA";
    protected static final String RSA = "RSA";
    protected static final String X_509 = "X.509";
    protected static final String AES = "AES";
    protected static final String PROVIDER_BC = "BC";
    protected static final String JKS = "JKS";

    private JwtSecurityUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static KeyPair getKeyPair() {
        return getKeyPair("security/key/dev/public-key.txt", "security/key/dev/private-key.txt");
    }

    public static KeyPair getKeyPair(String publicKeyPath, String privateKeyPath) {
        try {
            PublicKey publicKey = publicKey(publicKeyPath);
            PrivateKey privateKey = privateKey(privateKeyPath);
            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            throw new IllegalArgumentException("load oauth key exception");
        }
    }

    public static PrivateKey privateKey(String privateKeyPath) {
        String privateKeyBase64Value = ClassPathReader.read(privateKeyPath);
        return privateKeyFromConfigurer(privateKeyBase64Value);
    }

    public static PrivateKey privateKeyFromConfigurer(String privateKeyBase64Value) {
        try {
            privateKeyBase64Value = privateKeyBase64Value
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] keyBytes = Base64Utils.decrypt(privateKeyBase64Value);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);

            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new SecurityException("load  the private key exception", e);
        }
    }

    public static PublicKey publicKey(String publicKeyPath) {
        String publicKeyBase64Value = ClassPathReader.read(publicKeyPath);
        return publicKeyFromConfigurer(publicKeyBase64Value);
    }

    public static PublicKey publicKeyFromConfigurer(String publicKeyBase64Value) {
        try {
            publicKeyBase64Value = publicKeyBase64Value
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] keyBytes = Base64Utils.decrypt(publicKeyBase64Value);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);

            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new SecurityException("load  the public key exception", e);
        }
    }
}
