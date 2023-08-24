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
package com.photowey.oauth2.authentication.crypto.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * {@code AlgorithmCombine}
 *
 * @author photowey
 * @date 2022/02/07
 * @since 1.0.0
 */
public final class AlgorithmCombine {

    private AlgorithmCombine() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static final String ALGORITHM_MD5 = "MD5";
    public static final String ALGORITHM_SHA1 = "SHA-1";
    public static final String ALGORITHM_SHA256 = "SHA-256";
    public static final String ALGORITHM_SHA384 = "SHA-384";
    public static final String ALGORITHM_SHA512 = "SHA-512";
    public static final String ALGORITHM_SHA1RSA = "SHA1withRSA";
    public static final String ALGORITHM_SHA256RSA = "SHA256withRSA";

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

    private static final char[] CHAR_ARRAY = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private static final Integer SALT_LENGTH_32 = 32;
    private static final Integer SALT_LENGTH_64 = 64;

    public static String md5(String content) {
        return AlgorithmCombine.hash(ALGORITHM_MD5, content);
    }

    public static String sha1(String content) {
        return AlgorithmCombine.hash(ALGORITHM_SHA1, content);
    }

    public static String sha256(String content) {
        return AlgorithmCombine.hash(ALGORITHM_SHA256, content);
    }

    public static String sha384(String content) {
        return AlgorithmCombine.hash(ALGORITHM_SHA384, content);
    }

    public static String sha512(String content) {
        return AlgorithmCombine.hash(ALGORITHM_SHA512, content);
    }

    public static String hash(String algorithm, String content) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(content.getBytes(StandardCharsets.UTF_8));
            return AlgorithmCombine.toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    /**
     * md5 128bit 16bytes
     * sha1 160bit 20bytes
     * sha256 256bit 32bytes
     * sha384 384bit 48bytes
     * sha512 512bit 64bytes
     *
     * @param saltLength 盐值长度
     * @return 盐值
     */
    public static String generateSalt(int saltLength) {
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < saltLength; i++) {
            salt.append(CHAR_ARRAY[SECURE_RANDOM.nextInt(CHAR_ARRAY.length)]);
        }
        return salt.toString();
    }

    public static String generateSaltForSha256() {
        return AlgorithmCombine.generateSalt(SALT_LENGTH_32);
    }

    public static String generateSaltForSha512() {
        return AlgorithmCombine.generateSalt(SALT_LENGTH_64);
    }

    public static boolean slowEquals(byte[] a, byte[] b) {
        if (a == null || b == null) {
            return false;
        }

        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
}
