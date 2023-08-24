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

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * {@code RSAOAEPTest}
 *
 * @author photowey
 * @date 2023/03/09
 * @since 1.0.0
 */
class RSAOAEPTest {

    @Test
    void testRSAOAEP() throws Exception {
        KeyPair keyPair = RSAOAEP.generateKeyPair(); // 生成密钥对
        PublicKey publicKey = keyPair.getPublic(); // 获取公钥
        PrivateKey privateKey = keyPair.getPrivate(); // 获取私钥

        String data = "Hello world!"; // 待加密数据
        for (int i = 0; i < 10; i++) {
            data += "Hello world!";
        }

        byte[] encryptedData = RSAOAEP.encrypt(data.getBytes(), publicKey); // 加密数据
        System.out.println("加密后的数据：" + new String(encryptedData));
        byte[] decryptedData = RSAOAEP.decrypt(encryptedData, privateKey); // 解密数据
        System.out.println("解密后的数据：" + new String(decryptedData));

        String encrypted = RSAOAEP.encryptBase64(data, publicKey);
        System.out.println("Base64:加密后的数据：" + encrypted);
        String decrypted = RSAOAEP.decryptBase64(encrypted, privateKey);
        System.out.println("Base64:解密后的数据：" + decrypted);

    }
}