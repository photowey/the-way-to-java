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

import com.photowey.crypto.in.action.base64.Base64Utils;
import com.photowey.crypto.in.action.reader.ClassPathReader;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * {@code CryptoRsaReader}
 *
 * @author weichangjun
 * @date 2022/02/07
 * @since 1.0.0
 */
public final class CryptoRsaReader {

    private static final String RSA = "RSA";

    private CryptoRsaReader() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static PrivateKey privateKey(String privateKeyPath) {
        String privateKeyBase64Value = ClassPathReader.joinRead(privateKeyPath);
        return privateKeyFromString(privateKeyBase64Value);
    }

    public static PrivateKey privateKeyFromString(String privateKeyBase64Value) {
        try {
            byte[] keyBytes = Base64Utils.decrypt(privateKeyBase64Value);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new SecurityException("load the private key exception", e);
        }
    }

    public static PublicKey publicKey(String publicKeyPath) {
        String publicKeyBase64Value = ClassPathReader.joinRead(publicKeyPath);
        return publicKeyFromString(publicKeyBase64Value);
    }

    public static PublicKey publicKeyFromString(String publicKeyBase64Value) {
        try {
            byte[] keyBytes = Base64Utils.decrypt(publicKeyBase64Value);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);

            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new SecurityException("load the public key exception", e);
        }
    }

}
