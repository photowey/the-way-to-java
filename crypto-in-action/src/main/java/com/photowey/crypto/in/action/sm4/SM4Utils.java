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
package com.photowey.crypto.in.action.sm4;

import com.photowey.crypto.in.action.base64.Base64Utils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

/**
 * {@code SM4Utils}
 *
 * @author weichangjun
 * @date 2022/10/12
 * @since 1.0.0
 */
public class SM4Utils {

    private SM4Utils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ENCODING = StandardCharsets.UTF_8.displayName();
    public static final String ALGORITHM_NAME = "SM4";
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";
    public static final int DEFAULT_KEY_SIZE = 128;

    public static byte[] generateKey() throws Exception {
        return generateKey(DEFAULT_KEY_SIZE);
    }

    public static byte[] generateKey(int keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        keyGen.init(keySize, new SecureRandom());
        SecretKey secretKey = keyGen.generateKey();

        return secretKey.getEncoded();
    }

    /**
     * 加密
     * 基于 {@code Hex} 十六进制编码
     *
     * @param keyHex 十六进制秘钥
     * @param data   待加密明文
     * @return 十六进制密文
     * @throws Exception
     */
    public static String encryptEcb(String keyHex, String data) throws Exception {
        byte[] keyData = ByteUtils.fromHexString(keyHex);
        byte[] srcData = data.getBytes(ENCODING);
        byte[] cipherArray = encryptEcbPadding(keyData, srcData);

        return ByteUtils.toHexString(cipherArray);
    }

    /**
     * 基于 {@code Base64} 编码
     *
     * @param keyHex 十六进制秘钥
     * @param data   待加密明文
     * @return Base64 密文
     * @throws Exception
     */
    public static String encryptEcbb(String keyHex, String data) throws Exception {
        byte[] keyData = ByteUtils.fromHexString(keyHex);
        byte[] srcData = data.getBytes(ENCODING);
        byte[] cipherArray = encryptEcbPadding(keyData, srcData);

        return Base64Utils.encrypt(cipherArray);
    }

    /**
     * 解密
     * 基于 {@code Hex} 十六进制编码
     *
     * @param keyHex    十六进制秘钥
     * @param cipherHex 十六进制密文
     * @return 解密后的明文
     * @throws Exception
     */
    public static String decryptEcb(String keyHex, String cipherHex) throws Exception {
        byte[] keyData = ByteUtils.fromHexString(keyHex);
        byte[] cipherData = ByteUtils.fromHexString(cipherHex);
        byte[] srcData = decryptEcbPadding(keyData, cipherData);

        return new String(srcData, ENCODING);
    }

    /**
     * 解密
     * 基于 {@code Base64} 编码
     *
     * @param keyHex       十六进制秘钥
     * @param cipherBase64 Base64 编码密文
     * @return 解密后的明文
     * @throws Exception
     */
    public static String decryptEcbb(String keyHex, String cipherBase64) throws Exception {
        byte[] keyData = ByteUtils.fromHexString(keyHex);
        byte[] cipherData = Base64Utils.decrypt(cipherBase64);
        byte[] srcData = decryptEcbPadding(keyData, cipherData);

        return new String(srcData, ENCODING);
    }

    /**
     * 校验
     * 基于 {@code Hex} 十六进制编码
     *
     * @param keyHex    十六进制秘钥
     * @param cipherHex 十六进制待校验文本
     * @param data      原始文本
     * @return {@code boolean}
     * @throws Exception
     */
    public static boolean verifyEcb(String keyHex, String cipherHex, String data) throws Exception {
        byte[] keyData = ByteUtils.fromHexString(keyHex);
        byte[] cipherData = ByteUtils.fromHexString(cipherHex);
        byte[] decryptData = decryptEcbPadding(keyData, cipherData);
        byte[] srcData = data.getBytes(ENCODING);

        return Arrays.equals(decryptData, srcData);
    }

    /**
     * 校验
     * 基于 {@code Base64} 编码
     *
     * @param keyHex       十六进制秘钥
     * @param cipherBase64 Base64 编码待校验文本
     * @param data         原始文本
     * @return {@code boolean}
     * @throws Exception
     */
    public static boolean verifyEcbb(String keyHex, String cipherBase64, String data) throws Exception {
        byte[] keyData = ByteUtils.fromHexString(keyHex);
        byte[] cipherData = Base64Utils.decrypt(cipherBase64);
        byte[] decryptData = decryptEcbPadding(keyData, cipherData);
        byte[] srcData = data.getBytes(ENCODING);

        return Arrays.equals(decryptData, srcData);
    }

    public static byte[] encryptEcbPadding(byte[] key, byte[] data) throws Exception {
        Cipher cipher = generateEcbCipher(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decryptEcbPadding(byte[] key, byte[] cipherText) throws Exception {
        Cipher cipher = generateEcbCipher(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    private static Cipher generateEcbCipher(int mode, byte[] key) throws Exception {
        return generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, mode, key);
    }

    private static Cipher generateEcbCipher(String algorithm, int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }
}