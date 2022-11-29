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
package com.photowey.crypto.in.action.crypto;

import com.photowey.crypto.in.action.base64.Base64Utils;
import com.photowey.crypto.in.action.hash.Hash;
import com.photowey.crypto.in.action.ras.CryptoRsaReader;
import com.photowey.crypto.in.action.ras.RsaPair;
import com.photowey.crypto.in.action.sm4.SM4Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code CryptoJava}
 * {@code Crypto} {@code Java} 语言部分实现
 * 类结构设计思想来源于 前端 {@code CryptoJS} {@see * https://cryptojs.gitbook.io/docs/}
 *
 * @author photowey
 * @date 2022/03/15
 * @since 1.0.0
 */
public final class CryptoJava {

    private final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private CryptoJava() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static final class AES implements Serializable {

        private static final String AES_ALGORITHM = "AES";
        private static final String AES_CBC_NO_PADDING = "AES/CBC/NoPadding";
        private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";

        private AES() {
            throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
        }

        public static final class NoPadding implements Serializable {

            private NoPadding() {
                throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
            }

            public static String encrypt(String rules, String context, String ivString) {
                return encrypt(rules, context, ivString, AES_CBC_NO_PADDING);
            }

            public static String decrypt(String rules, String encrypted, String ivString) {
                return decrypt(rules, encrypted, ivString, AES_CBC_NO_PADDING);
            }

            public static String encrypt(String secretKey, String context, String ivString, String cipherString) {
                byte[] iv = ivString.getBytes(StandardCharsets.UTF_8);
                try {
                    Cipher cipher = Cipher.getInstance(cipherString);
                    int blockSize = cipher.getBlockSize();
                    byte[] dataBytes = context.getBytes(StandardCharsets.UTF_8);
                    int length = dataBytes.length;
                    if (length % blockSize != 0) {
                        length = length + (blockSize - (length % blockSize));
                    }
                    byte[] plainText = new byte[length];
                    System.arraycopy(dataBytes, 0, plainText, 0, dataBytes.length);

                    SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
                    IvParameterSpec ivSpec = new IvParameterSpec(iv);
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
                    byte[] encrypted = cipher.doFinal(plainText);

                    return Base64Utils.encrypt(encrypted);

                } catch (Exception e) {
                    throw new SecurityException(String.format("handle aes:[%s] encrypt exception", cipherString), e);
                }
            }

            public static String decrypt(String secretKey, String encrypted, String ivString, String cipherString) {
                byte[] iv = ivString.getBytes(StandardCharsets.UTF_8);
                try {
                    byte[] dataBytes = Base64Utils.decrypt(encrypted);
                    Cipher cipher = Cipher.getInstance(cipherString);
                    SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
                    IvParameterSpec ivSpec = new IvParameterSpec(iv);
                    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
                    byte[] original = cipher.doFinal(dataBytes);

                    // 为什么? 还需要 再次 trim() -> 可能会有空格
                    return new String(original, StandardCharsets.UTF_8).trim();
                } catch (Exception e) {
                    throw new SecurityException(String.format("handle aes:[%s] decrypt exception", cipherString), e);
                }
            }
        }

        public static final class PKCS5Padding implements Serializable {

            private static final int INIT_VECTOR_LENGTH = 16;

            public static String encrypt(String secretKey, String context) {
                return encrypt(secretKey, context, AES_CBC_PKCS5_PADDING);
            }

            public static String encrypt(String secretKey, String context, String cipherString) {
                if (!isKeyLengthValid(secretKey)) {
                    throw new RuntimeException("secret key's length must be 128, 192 or 256 bits");
                }
                try {
                    SecureRandom secureRandom = new SecureRandom();
                    byte[] iv = new byte[INIT_VECTOR_LENGTH / 2];
                    secureRandom.nextBytes(iv);
                    String initVector = bytesToHex(iv);
                    iv = initVector.getBytes(StandardCharsets.UTF_8);

                    IvParameterSpec ivSpec = new IvParameterSpec(iv);
                    SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);

                    Cipher cipher = Cipher.getInstance(cipherString);
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
                    byte[] plainText = context.getBytes(StandardCharsets.UTF_8);
                    byte[] encrypted = cipher.doFinal(plainText);

                    ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + encrypted.length);
                    byteBuffer.put(iv);
                    byteBuffer.put(encrypted);

                    return Base64Utils.encrypt(byteBuffer.array());
                } catch (Throwable e) {
                    throw new SecurityException(String.format("handle aes:[%s] encrypt exception", cipherString), e);
                }
            }

            public static String decrypt(String secretKey, String cipherText) {
                return decrypt(secretKey, cipherText, AES_CBC_PKCS5_PADDING);
            }

            public static String decrypt(String secretKey, String encrypted, String cipherString) {
                if (!isKeyLengthValid(secretKey)) {
                    throw new RuntimeException("secret key's length must be 128, 192 or 256 bits");
                }
                try {
                    byte[] cipherText = Base64Utils.decrypt(encrypted);

                    IvParameterSpec ivSpec = new IvParameterSpec(cipherText, 0, INIT_VECTOR_LENGTH);
                    SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);

                    Cipher cipher = Cipher.getInstance(cipherString);
                    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                    byte[] data = cipher.doFinal(cipherText, INIT_VECTOR_LENGTH, cipherText.length - INIT_VECTOR_LENGTH);

                    return new String(data, StandardCharsets.UTF_8);
                } catch (Throwable e) {
                    throw new SecurityException(String.format("handle aes:[%s] decrypt exception", cipherString), e);
                }
            }

            public static boolean isKeyLengthValid(String key) {
                return key.length() == 16 || key.length() == 24 || key.length() == 32;
            }
        }
    }

    public static final class RSA implements Serializable {

        /**
         * 加密算法RSA
         */
        private static final String RSA_ALGORITHM = "RSA";

        /**
         * 签名算法
         */
        public static final String SIGNATURE_MD5_WITH_RSA_ALGORITHM = "MD5withRSA";
        public static final String SIGNATURE_SHA256_WITH_RSA_ALGORITHM = "SHA256withRSA";

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

        private RSA() {
            throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
        }

        public static class MD5 implements Serializable {

            public static String sign(String data, String privateKey) throws Exception {
                return sign(data.getBytes(StandardCharsets.UTF_8), privateKey);
            }

            public static String sign(byte[] data, String privateKey) throws Exception {
                return RSA.sign(data, privateKey);
            }

            public static boolean verify(String data, String publicKey, String sign) throws Exception {
                return verify(data.getBytes(StandardCharsets.UTF_8), publicKey, sign);
            }

            public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
                return RSA.verify(data, publicKey, sign);
            }
        }

        public static class SHA256 implements Serializable {

            public static String sign(String data, String privateKey) throws Exception {
                return sign(data.getBytes(StandardCharsets.UTF_8), privateKey);
            }

            public static String sign(byte[] data, String privateKey) throws Exception {
                return RSA.sign(data, privateKey, SIGNATURE_SHA256_WITH_RSA_ALGORITHM);
            }

            public static boolean verify(String data, String publicKey, String sign) throws Exception {
                return verify(data.getBytes(StandardCharsets.UTF_8), publicKey, sign);
            }

            public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
                return RSA.verify(data, publicKey, sign, SIGNATURE_SHA256_WITH_RSA_ALGORITHM);
            }
        }

        // --------------------------------------------------------- gen key pair

        public static KeyPair keyPair() throws SecurityException {
            return keyPair(KEY_SIZE);
        }

        public static KeyPair keyPair(int keySize) throws SecurityException {
            try {
                KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
                keyPairGen.initialize(keySize);
                KeyPair keyPair = keyPairGen.generateKeyPair();
                RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
                RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

                return new KeyPair(publicKey, privateKey);
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
                KeyPair keyPair = keyPairGen.generateKeyPair();
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
            return sign(data, privateKey, SIGNATURE_MD5_WITH_RSA_ALGORITHM);
        }

        public static String sign(byte[] data, String privateKey, String algorithm) throws Exception {
            PrivateKey privateK = privateKeyFromString(privateKey);
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(privateK);
            signature.update(data);

            return Base64Utils.encrypt(signature.sign());
        }

        public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
            PublicKey publicK = publicKeyFromString(publicKey);
            Signature signature = Signature.getInstance(SIGNATURE_MD5_WITH_RSA_ALGORITHM);
            signature.initVerify(publicK);
            signature.update(data);

            return verify(data, publicKey, sign, SIGNATURE_MD5_WITH_RSA_ALGORITHM);
        }

        public static boolean verify(byte[] data, String publicKey, String sign, String algorithm) throws Exception {
            PublicKey publicK = publicKeyFromString(publicKey);
            Signature signature = Signature.getInstance(algorithm);
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
            return CryptoRsaReader.privateKey(privateKeyPath);
        }

        public static PrivateKey privateKeyFromString(String privateKeyBase64Value) {
            return CryptoRsaReader.privateKeyFromString(privateKeyBase64Value);
        }

        public static PublicKey publicKey(String publicKeyPath) {
            return CryptoRsaReader.publicKey(publicKeyPath);
        }

        public static PublicKey publicKeyFromString(String publicKeyBase64Value) {
            return CryptoRsaReader.publicKeyFromString(publicKeyBase64Value);
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

        public static String privateKey(RsaPair keyPair) throws NullPointerException {
            Key key = keyPair.getPrivateKey();
            if (null == key) {
                key = keyPair.privateKey();
            }

            if (null == key) {
                throw new NullPointerException("can't retrieve the private key instance");
            }

            return Base64Utils.encrypt(key.getEncoded());
        }

        public static String publicKey(RsaPair keyPair) throws NullPointerException {
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

    public static final class HASH implements Serializable {

        public static final class MD5 implements Serializable {

            private static final String ALGORITHM_MD5 = "MD5";

            public static String md5(String data) {
                return hash(ALGORITHM_MD5, data);
            }
        }

        public static final class SHA implements Serializable {

            private static final String ALGORITHM_SHA1 = "SHA-1";
            private static final String ALGORITHM_SHA256 = "SHA-256";
            private static final String ALGORITHM_SHA384 = "SHA-384";
            private static final String ALGORITHM_SHA512 = "SHA-512";

            public static String sha1(String data) {
                return hash(ALGORITHM_SHA1, data);
            }

            public static String sha256(String data) {
                return hash(ALGORITHM_SHA256, data);
            }

            public static String sha384(String data) {
                return hash(ALGORITHM_SHA384, data);
            }

            public static String sha512(String data) {
                return hash(ALGORITHM_SHA512, data);
            }
        }

        public static String hash(String algorithm, String content) {
            return Hash.hash(algorithm, content);
        }
    }

    public static final class SM2 implements Serializable {

        private SM2() {
            throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
        }
    }

    public static final class SM4 implements Serializable {

        private SM4() {
            throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
        }

        public static byte[] generateKey() throws Exception {
            return SM4Utils.generateKey();
        }

        public static byte[] generateKey(int keySize) throws Exception {
            return SM4Utils.generateKey(keySize);
        }

        public static final class ECB implements Serializable {

            private ECB() {
                throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
            }

            public static String encryptEcb(String keyHex, String data) throws Exception {
                return SM4Utils.encryptEcb(keyHex, data);
            }

            public static String encryptEcbb(String keyHex, String data) throws Exception {
                return SM4Utils.encryptEcbb(keyHex, data);
            }

            public static String decryptEcb(String keyHex, String cipherHex) throws Exception {
                return SM4Utils.decryptEcb(keyHex, cipherHex);
            }

            public static String decryptEcbb(String keyHex, String cipherBase64) throws Exception {
                return SM4Utils.decryptEcbb(keyHex, cipherBase64);
            }

            public static boolean verifyEcb(String keyHex, String cipherHex, String data) throws Exception {
                return SM4Utils.verifyEcb(keyHex, cipherHex, data);
            }

            public static boolean verifyEcbb(String keyHex, String cipherHex, String data) throws Exception {
                return SM4Utils.verifyEcbb(keyHex, cipherHex, data);
            }
        }

        public static final class CBC implements Serializable {

            private CBC() {
                throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
            }

            public static String encryptCbc(String keyHex, String data) throws Exception {
                throw new UnsupportedOperationException("UnSupported now");
            }

            public static String encryptCbcb(String keyHex, String data) throws Exception {
                throw new UnsupportedOperationException("UnSupported now");
            }

            public static String decryptCbc(String keyHex, String cipherHex) throws Exception {
                throw new UnsupportedOperationException("UnSupported now");
            }

            public static String decryptCbcb(String keyHex, String cipherBase64) throws Exception {
                throw new UnsupportedOperationException("UnSupported now");
            }

            public static boolean verifyCbc(String keyHex, String cipherHex, String data) throws Exception {
                throw new UnsupportedOperationException("UnSupported now");
            }

            public static boolean verifyCbcb(String keyHex, String cipherHex, String data) throws Exception {
                throw new UnsupportedOperationException("UnSupported now");
            }
        }
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
