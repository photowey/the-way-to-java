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
package com.photowey.crypto.in.action.ras.oaep;

import com.photowey.crypto.in.action.base64.Base64Utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * {@code RSAOAEP}
 *
 * @author photowey
 * @date 2023/03/09
 * @since 1.0.0
 */
public class RSAOAEP {

    private static final String ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final int KEY_SIZE = 2048;
    private static final int SHA_256_HASH_LENGTH = 32;

    // 生成RSA密钥对
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE); // 设置密钥长度
        return keyPairGenerator.generateKeyPair();
    }

    // RSA OAEP分段加密
    public static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION); // 设置加密模式和填充方式
        cipher.init(Cipher.ENCRYPT_MODE, publicKey); // 设置公钥和加密模式
        int inputLen = data.length; // 输入数据长度
        int maxLen = KEY_SIZE / 8 - 2 * SHA_256_HASH_LENGTH - 2; // 最大分段长度（根据密钥长度和填充方式确定）
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) { // 创建字节数组输出流
            int offSet = 0; // 偏移量
            byte[] cache;   // 缓存数组
            while (inputLen - offSet > 0) { // 循环分段加密
                if (inputLen - offSet > maxLen) { // 如果剩余数据大于最大分段长度，则取最大分段长度的数据进行加密
                    cache = cipher.doFinal(data, offSet, maxLen);
                } else { // 否则，取剩余数据进行加密
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache); // 将加密结果写入输出流中
                offSet += maxLen; // 更新偏移量
            }
            return out.toByteArray(); // 返回输出流中的字节数组
        }
    }

    public static String encryptBase64(String data, PublicKey publicKey) throws Exception {
        return Base64Utils.encrypt(encrypt(data.getBytes(StandardCharsets.UTF_8), publicKey));
    }

    // RSA OAEP分段解密
    public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION); // 设置解密模式和填充方式
        cipher.init(Cipher.DECRYPT_MODE, privateKey); // 设置私钥和解密模式
        int inputLen = data.length; // 输入数据长度
        int maxLen = KEY_SIZE / 8;
        // 最大分段长度（根据密钥长度和填充方式确定）
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) { // 创建字节数组输出流
            int offSet = 0; // 偏移量
            byte[] cache;   // 缓存数组
            while (inputLen - offSet > 0) { // 循环分段解密
                if (inputLen - offSet > maxLen) { // 如果剩余数据大于最大分段长度，则取最大分段长度的数据进行解密
                    cache = cipher.doFinal(data, offSet, maxLen);
                } else { // 否则，取剩余数据进行解密
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache); // 将解密结果写入输出流中
                offSet += maxLen; // 更新偏移量
            }
            return out.toByteArray(); // 返回输出流中的字节数组
        }
    }

    public static String decryptBase64(String encryptedBase64, PrivateKey privateKey) throws Exception {
        return new String(decrypt(Base64Utils.decrypt(encryptedBase64), privateKey), StandardCharsets.UTF_8);
    }
}