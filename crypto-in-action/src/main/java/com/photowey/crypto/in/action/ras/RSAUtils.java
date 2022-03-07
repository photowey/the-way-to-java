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

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code RSAUtils}
 * {@code RSA} 加解密
 *
 * @author photowey
 * @date 2022/03/03
 * @since 1.0.0
 */
public final class RSAUtils {

    /**
     * 加密算法RSA
     */
    public static final String RSA_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 获取公钥的 key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的 key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * RSA 密钥长度
     */
    private static final int KEY_SIZE = 2048;

    private RSAUtils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    // --------------------------------------------------------- gen key pair

    public static java.security.KeyPair keyPair() throws SecurityException {
        return keyPair(KEY_SIZE);
    }

    public static java.security.KeyPair keyPair(int keySize) throws SecurityException {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            keyPairGen.initialize(keySize);
            java.security.KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            return new java.security.KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            throw new SecurityException(e);
        }

    }

    // --------------------------------------------------------- gen key context

    public static Map<String, Key> keyPairContext() throws SecurityException {
        return keyPairContext(KEY_SIZE);
    }

    public static Map<String, Key> keyPairContext(int keySize) throws SecurityException {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            keyPairGen.initialize(keySize);
            java.security.KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, Key> keyContext = new HashMap<>(4);
            keyContext.put(PUBLIC_KEY, publicKey);
            keyContext.put(PRIVATE_KEY, privateKey);

            return keyContext;
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    // --------------------------------------------------------- encrypt

    /**
     * 公钥加密
     *
     * @param data      明文的字节数据
     * @param publicKey 公钥-{@code Base64}字符串
     * @return 加密-密文
     * @throws Exception
     */
    public static String encrypt(String data, String publicKey) throws Exception {
        PublicKey publicK = publicKeyFromString(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        byte[] encryptedData = encrypt(data.getBytes(StandardCharsets.UTF_8), keyFactory, publicK);

        return Base64Utils.encrypt(encryptedData);
    }

    /**
     * 公钥解密
     *
     * @param data      明文的字节数据
     * @param publicKey 公钥-{@code Base64}字符串
     * @return 加密-字节数据
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        PublicKey publicK = publicKeyFromString(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);

        return encrypt(data, keyFactory, publicK);
    }

    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        PrivateKey privateK = privateKeyFromString(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);

        return encrypt(data, keyFactory, privateK);
    }

    public static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return encrypt(data, keyFactory, publicKey);
    }

    public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return encrypt(data, keyFactory, privateKey);
    }

    // --------------------------------------------------------- decrypt

    /**
     * 私钥解密
     *
     * @param encryptedData 公钥解密的 {@code Base64} 字节数据
     * @param privateKey    私钥
     * @return 原文-字符串
     * @throws Exception
     */
    public static String decrypt(String encryptedData, String privateKey) throws Exception {
        PrivateKey privateK = privateKeyFromString(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        byte[] decryptedData = decrypt(Base64Utils.decrypt(encryptedData), keyFactory, privateK);

        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 公钥解密的 {@code Base64} 字节数据
     * @param privateKey    私钥
     * @return 原文-字节数据
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        PrivateKey privateK = privateKeyFromString(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);

        return decrypt(encryptedData, keyFactory, privateK);
    }

    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decrypt(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(x509KeySpec);

        return decrypt(encryptedData, keyFactory, publicK);
    }

    public static byte[] decryptByPrivateKey(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);

        return decrypt(encryptedData, keyFactory, privateKey);
    }

    public static byte[] decryptByPublicKey(byte[] encryptedData, PublicKey publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);

        return decrypt(encryptedData, keyFactory, publicKey);
    }

    // --------------------------------------------------------- sign

    public static String sign(byte[] data, String privateKey) throws Exception {
        PrivateKey privateK = privateKeyFromString(privateKey);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);

        return Base64Utils.encrypt(signature.sign());
    }

    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        PublicKey publicK = publicKeyFromString(publicKey);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);

        return signature.verify(Base64Utils.decrypt(sign));
    }

    // ---------------------------------------------------------

    private static byte[] encrypt(byte[] data, KeyFactory keyFactory, Key key) throws SecurityException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            int dataLength = data.length;
            int offSet = 0;
            byte[] cache = new byte[128];
            int i = 0;
            while (dataLength - offSet > 0) {
                if (dataLength - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, dataLength - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            return out.toByteArray();
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    private static byte[] decrypt(byte[] encryptedData, KeyFactory keyFactory, Key key) throws SecurityException {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);
            int dataLength = encryptedData.length;
            int offSet = 0;
            byte[] cache = new byte[128];
            int i = 0;
            while (dataLength - offSet > 0) {
                if (dataLength - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, dataLength - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            return out.toByteArray();
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    // --------------------------------------------------------- key

    public static PrivateKey privateKey(String privateKeyPath) {
        String privateKeyBase64Value = ClassPathReader.joinRead(privateKeyPath);

        return privateKeyFromString(privateKeyBase64Value);
    }

    public static PrivateKey privateKeyFromString(String privateKeyBase64Value) {
        try {
            byte[] keyBytes = Base64Utils.decrypt(privateKeyBase64Value);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
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
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);

            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new SecurityException("load the public key exception", e);
        }
    }

    // --------------------------------------------------------- key context

    public static <T extends Key> String privateKey(Map<String, T> keyContext) throws Exception {
        Key key = keyContext.get(PRIVATE_KEY);

        return Base64Utils.encrypt(key.getEncoded());
    }

    public static <T extends Key> String publicKey(Map<String, T> keyContext) throws Exception {
        Key key = keyContext.get(PUBLIC_KEY);

        return Base64Utils.encrypt(key.getEncoded());
    }

    // --------------------------------------------------------- key pair

    public static String privateKey(KeyPair keyPair) throws NullPointerException {
        Key key = keyPair.getPrivateKey();
        if (null == key) {
            key = keyPair.privateKey();
        }

        if (null == key) {
            throw new NullPointerException("can't retrieve the private key instance");
        }

        return Base64Utils.encrypt(key.getEncoded());
    }

    public static String publicKey(KeyPair keyPair) throws NullPointerException {
        Key key = keyPair.getPublicKey();
        if (null == key) {
            key = keyPair.publicKey();
        }
        if (null == key) {
            throw new NullPointerException("can't retrieve the public key instance");
        }

        return Base64Utils.encrypt(key.getEncoded());
    }

}
