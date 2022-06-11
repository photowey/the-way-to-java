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
package com.photowey.crypto.in.action.ras;

import com.photowey.crypto.in.action.base64.Base64Utils;
import com.photowey.crypto.in.action.reader.ClassPathReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * {@code RSAUtilsTest}
 *
 * @author photowey
 * @date 2022/03/03
 * @since 1.0.0
 */
@Slf4j
class RSAUtilsTest {

    private static String privateKey;
    private static String publicKey;

    static final String CONTENT = "我是密文？";

    @BeforeAll
    static void init() {
        publicKey = ClassPathReader.joinRead("key/public-key.txt");
        privateKey = ClassPathReader.joinRead("key/private-key.txt");
    }

    @Test
    void testEncrypt() throws Exception {
        RSAUtils.encryptByPublicKey(CONTENT.getBytes(StandardCharsets.UTF_8), publicKey);
        RSAUtils.encrypt(CONTENT, publicKey);
    }

    @Test
    void testDecrypt() throws Exception {
        byte[] data = RSAUtils.encryptByPublicKey(CONTENT.getBytes(StandardCharsets.UTF_8), RSAUtilsTest.publicKey);
        String encrypt = Base64Utils.encrypt(data);

        byte[] bytes = RSAUtils.decryptByPrivateKey(Base64Utils.decrypt(encrypt), privateKey);
        String decrypt = new String(bytes);

        Assertions.assertEquals(CONTENT, decrypt);
    }

    @Test
    void testDecryptDefault() throws Exception {
        String encryptedData = RSAUtils.encrypt(CONTENT, RSAUtilsTest.publicKey);
        String decrypt = RSAUtils.decrypt(encryptedData, privateKey);

        Assertions.assertEquals(CONTENT, decrypt);
    }
}