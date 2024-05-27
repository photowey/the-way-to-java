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
package com.photowey.cryptono.in.action.handler;

import com.photowey.crypto.in.action.crypto.CryptoJava;
import com.photowey.crypto.in.action.reader.ClassPathReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code PhoneNumberEncryptorTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/26
 */
@SpringBootTest
class PhoneNumberEncryptorTest {

    @Autowired
    private PhoneNumberEncryptor numberEncryptor;

    @Test
    void testRsaEncrypt() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String content = "我是密文？";
        String encrypted = CryptoJava.RSA.encrypt(content, publicKey);
        String decrypted = CryptoJava.RSA.decrypt(encrypted, privateKey);

        Assertions.assertEquals(content, decrypted);
    }

    @Test
    void testEncrypt() {
        String phoneNumber = "13112345678";
        String encryptedPhoneNumber = this.numberEncryptor.encryptPhoneNumber(phoneNumber);
        Assertions.assertNotNull(encryptedPhoneNumber);
    }

    @Test
    void testGenerateDictionary() {
        List<String> dictionary = new ArrayList<>();
        for (int i = 0; i < 1_000; i++) {
            dictionary.add(String.format("%03d", i));
        }

        Assertions.assertEquals(1_000, dictionary.size());
    }
}