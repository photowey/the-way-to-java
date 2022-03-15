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
package com.photowey.crypto.in.action.crypto;

import com.photowey.crypto.in.action.base64.Base64Utils;
import com.photowey.crypto.in.action.ras.CryptoRsaReader;
import com.photowey.crypto.in.action.ras.RsaPair;

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
 *
 * @author weichangjun
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
        private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

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

        // --------------------------------------------------------- gen key pair

        public static RsaPair keyPair() throws Exception {
            return keyPair(KEY_SIZE);
        }

        public static RsaPair keyPair(int keySize) throws Exception {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            keyPairGen.initialize(keySize);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            return new RsaPair(publicKey, privateKey);
        }

        // --------------------------------------------------------- gen key context

        public static Map<String, Key> keyPairContext() throws Exception {
            return keyPairContext(KEY_SIZE);
        }

        public static Map<String, Key> keyPairContext(int keySize) throws Exception {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            keyPairGen.initialize(keySize);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, Key> keyContext = new HashMap<>(4);
            keyContext.put(PUBLIC_KEY, publicKey);
            keyContext.put(PRIVATE_KEY, privateKey);

            return keyContext;
        }

        // --------------------------------------------------------- encrypt

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

        // --------------------------------------------------------- encrypt

        private static byte[] encrypt(byte[] data, KeyFactory keyFactory, Key key) throws Exception {
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            int dataLength = data.length;
            byte[] encryptedData = new byte[128];
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
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
                encryptedData = out.toByteArray();
            }

            return encryptedData;
        }

        // --------------------------------------------------------- decrypt

        private static byte[] decrypt(byte[] encryptedData, KeyFactory keyFactory, Key key) throws Exception {
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);
            int dataLength = encryptedData.length;
            byte[] decryptedData = new byte[128];
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
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
                decryptedData = out.toByteArray();
            }

            return decryptedData;
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

        public static String privateKey(RsaPair rsaPair) throws Exception {
            Key key = rsaPair.getPrivateKey();
            if (null == key) {
                key = rsaPair.privateKey();
            }

            if (null == key) {
                throw new NullPointerException("can't retrieve the private key instance");
            }

            return Base64Utils.encrypt(key.getEncoded());
        }

        public static String publicKey(RsaPair rsaPair) throws Exception {
            Key key = rsaPair.getPublicKey();
            if (null == key) {
                key = rsaPair.publicKey();
            }
            if (null == key) {
                throw new NullPointerException("can't retrieve the public key instance");
            }

            return Base64Utils.encrypt(key.getEncoded());
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
