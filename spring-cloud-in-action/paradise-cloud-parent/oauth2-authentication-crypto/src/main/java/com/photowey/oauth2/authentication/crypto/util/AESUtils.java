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
package com.photowey.oauth2.authentication.crypto.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * {@code AESUtils}
 *
 * @author photowey
 * @date 2022/01/22
 * @since 1.0.0
 */
public final class AESUtils {

    private AESUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    private static final String AES = "AES";
    private static final String SHA1PRNG = "SHA1PRNG";
    private static final Integer KEY_INIT_LENGTH = 128;

    public static String encrypt(String encryptRules, String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG);
            secureRandom.setSeed(encryptRules.getBytes(StandardCharsets.UTF_8));
            keygen.init(KEY_INIT_LENGTH, secureRandom);

            SecretKey originalKey = keygen.generateKey();
            byte[] raw = originalKey.getEncoded();
            SecretKey secretKey = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] byteEncode = content.getBytes(StandardCharsets.UTF_8);
            byte[] byteAes = cipher.doFinal(byteEncode);

            return Base64Utils.encrypt(byteAes);
        } catch (Exception e) {
            throw new SecurityException("handle aes encrypt exception");
        }
    }

    public static String decrypt(String encryptRules, String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG);
            secureRandom.setSeed(encryptRules.getBytes(StandardCharsets.UTF_8));
            keygen.init(KEY_INIT_LENGTH, secureRandom);

            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();

            SecretKey secretKey = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] contentByte = Base64Utils.decrypt(content);
            byte[] decodeByte = cipher.doFinal(contentByte);

            return new String(decodeByte, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SecurityException("handle aes decrypt exception");
        }
    }
}
