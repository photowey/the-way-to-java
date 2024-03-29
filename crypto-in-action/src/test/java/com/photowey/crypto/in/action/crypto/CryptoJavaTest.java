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
package com.photowey.crypto.in.action.crypto;

import com.photowey.crypto.in.action.reader.ClassPathReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * {@code CryptoJavaTest}
 *
 * @author photowey
 * @date 2022/03/16
 * @since 1.0.0
 */
class CryptoJavaTest {

    @Test
    void testCryptoAESSHA1PRNG() {
        String rules = "1234567887654321";
        String content = "hello@git.com%*_1024";

        String encrypted = CryptoJava.AES.SHA1PRNG.encrypt(rules, content);
        String decrypted = CryptoJava.AES.SHA1PRNG.decrypt(rules, encrypted);

        Assertions.assertEquals(content, decrypted);
    }

    @Test
    void testCryptoAESNoPadding() {
        String rules = "1234567887654321";
        String ivString = "1234567887654321";
        String content = "hello@git.com%*_1024";

        String encrypted = CryptoJava.AES.NoPadding.encrypt(rules, content, ivString);
        String decrypted = CryptoJava.AES.NoPadding.decrypt(rules, encrypted, ivString);

        Assertions.assertEquals(content, decrypted);
    }

    @Test
    void testCryptoAESPKCS5Padding() {
        String rules = "1234567887654321";
        String content = "hello@git.com%*_1024";

        String encrypted = CryptoJava.AES.PKCS5Padding.encrypt(rules, content);
        String decrypted = CryptoJava.AES.PKCS5Padding.decrypt(rules, encrypted);

        Assertions.assertEquals(content, decrypted);
    }

    @Test
    void testCryptoRSA() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String content = "我是密文？";
        String encrypted = CryptoJava.RSA.encrypt(content, publicKey);
        String decrypted = CryptoJava.RSA.decrypt(encrypted, privateKey);

        Assertions.assertEquals(content, decrypted);
    }

    @Test
    void testCryptoRSASign_MD5() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String content = "我是密文？";
        String sign = CryptoJava.RSA.sign(content.getBytes(StandardCharsets.UTF_8), privateKey);
        boolean match = CryptoJava.RSA.verify(content.getBytes(StandardCharsets.UTF_8), publicKey, sign);

        Assertions.assertTrue(match);
    }

    @Test
    void testCryptoRSASign_MD5_v2() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String content = "我是密文？";
        String sign = CryptoJava.RSA.MD5.sign(content.getBytes(StandardCharsets.UTF_8), privateKey);
        boolean match = CryptoJava.RSA.MD5.verify(content.getBytes(StandardCharsets.UTF_8), publicKey, sign);

        Assertions.assertTrue(match);
    }

    @Test
    void testCryptoRSASign_MD5_v3() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String content = "我是密文？";
        String sign = CryptoJava.RSA.MD5.sign(content, privateKey);
        boolean match = CryptoJava.RSA.MD5.verify(content, publicKey, sign);

        Assertions.assertTrue(match);
    }

    @Test
    void testCryptoRSASign_SHA256() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String content = "我是密文？";
        String sign = CryptoJava.RSA.sign(content.getBytes(StandardCharsets.UTF_8),
                privateKey, CryptoJava.RSA.SIGNATURE_SHA256_WITH_RSA_ALGORITHM);
        boolean match = CryptoJava.RSA.verify(content.getBytes(StandardCharsets.UTF_8),
                publicKey, sign, CryptoJava.RSA.SIGNATURE_SHA256_WITH_RSA_ALGORITHM);

        Assertions.assertTrue(match);
    }

    @Test
    void testCryptoRSASign_SHA256_v2() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String content = "我是密文？";
        String sign = CryptoJava.RSA.SHA256.sign(content.getBytes(StandardCharsets.UTF_8), privateKey);
        boolean match = CryptoJava.RSA.SHA256.verify(content.getBytes(StandardCharsets.UTF_8), publicKey, sign);

        Assertions.assertTrue(match);
    }

    @Test
    void testCryptoRSASign_SHA256_v3() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String content = "我是密文？";
        String sign = CryptoJava.RSA.SHA256.sign(content, privateKey);
        boolean match = CryptoJava.RSA.SHA256.verify(content, publicKey, sign);

        Assertions.assertTrue(match);
    }

    @Test
    void testCryptoHashMD5() throws Exception {
        String content = "我是密文？";
        String md5 = CryptoJava.HASH.MD5.md5(content);

        Assertions.assertEquals("8cca8a39ab835eb404e27d54508218d4", md5);
    }

    @Test
    void testCryptoHashSha1() throws Exception {
        String content = "我是密文？";
        String sha1 = CryptoJava.HASH.SHA.sha1(content);

        Assertions.assertEquals("5b472351cd914f010b21e6ccb87bd6ce63d949b4", sha1);
    }

    @Test
    void testCryptoHashSha256() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String sha256 = CryptoJava.HASH.SHA.sha256(publicKey);

        Assertions.assertEquals("3ef6c6a28baacb8d8e37318ed81d211bc189dbffa942d76574a7cff678b9bcc7", sha256);
    }

    @Test
    void testCryptoHashSha384() throws Exception {
        String content = "我是密文？";
        String sha384 = CryptoJava.HASH.SHA.sha384(content);

        Assertions.assertEquals("eda292f6009cbf4b18b31e31b017509f72853d4663acc523a21e915d0188dfb89f16d257b96d66d20a5b43843b280cd1", sha384);
    }

    @Test
    void testCryptoHashSha512() throws Exception {
        String content = "我是密文？";
        String sha512 = CryptoJava.HASH.SHA.sha512(content);

        Assertions.assertEquals("7210d5023d5ede3223a0220599e5615d0b88c2338fdaff5917392656833bf5b35325356f21dda195e5408d9a0acdb52b765ef3d0d6eb053c0ef51b85fcbff404", sha512);
    }

    @Test
    void testSM4() throws Exception {
        String content = "1234567887654321";
        String key = "65C00054F4B4F1EFAC2CB0F62445CD32";
        String encrypted = CryptoJava.SM4.ECB.encryptEcb(key, content);
        String decryptedEcb = CryptoJava.SM4.ECB.decryptEcb(key, encrypted);

        Assertions.assertEquals(decryptedEcb, content);
        Assertions.assertTrue(CryptoJava.SM4.ECB.verifyEcb(key, encrypted, content));
    }
}