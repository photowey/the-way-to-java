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
    void testCryptoAESNoPadding() {
        String secretKey = "1234567887654321";
        String ivString = "1234567887654321";
        String password = "hello@git.com%*_1024";

        String encrypted = CryptoJava.AES.NoPadding.encrypt(secretKey, password, ivString);
        String decrypt = CryptoJava.AES.NoPadding.decrypt(secretKey, encrypted, ivString);

        Assertions.assertEquals(password, decrypt);
    }

    @Test
    void testCryptoAESPKCS5Padding() {
        String secretKey = "1234567887654321";
        String password = "hello@git.com%*_1024";

        String pkcsEncrypted = CryptoJava.AES.PKCS5Padding.encrypt(secretKey, password);
        String pkcsDecrypt = CryptoJava.AES.PKCS5Padding.decrypt(secretKey, pkcsEncrypted);

        Assertions.assertEquals(password, pkcsDecrypt);
    }

    @Test
    void testCryptoRSA() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String CONTENT = "我是密文？";
        String encrypt = CryptoJava.RSA.encrypt(CONTENT, publicKey);
        String decrypt = CryptoJava.RSA.decrypt(encrypt, privateKey);

        Assertions.assertEquals(CONTENT, decrypt);
    }

    @Test
    void testCryptoRSASign_MD5() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String CONTENT = "我是密文？";
        String sign = CryptoJava.RSA.sign(CONTENT.getBytes(StandardCharsets.UTF_8), privateKey);
        boolean match = CryptoJava.RSA.verify(CONTENT.getBytes(StandardCharsets.UTF_8), publicKey, sign);

        Assertions.assertTrue(match);
    }

    @Test
    void testCryptoRSASign_MD5_v2() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String CONTENT = "我是密文？";
        String sign = CryptoJava.RSA.MD5.sign(CONTENT.getBytes(StandardCharsets.UTF_8), privateKey);
        boolean match = CryptoJava.RSA.MD5.verify(CONTENT.getBytes(StandardCharsets.UTF_8), publicKey, sign);

        Assertions.assertTrue(match);
    }

    @Test
    void testCryptoRSASign_MD5_v3() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String CONTENT = "我是密文？";
        String sign = CryptoJava.RSA.MD5.sign(CONTENT, privateKey);
        boolean match = CryptoJava.RSA.MD5.verify(CONTENT, publicKey, sign);

        Assertions.assertTrue(match);
    }

    @Test
    void testCryptoRSASign_SHA256() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String CONTENT = "我是密文？";
        String sign = CryptoJava.RSA.sign(CONTENT.getBytes(StandardCharsets.UTF_8),
                privateKey, CryptoJava.RSA.SIGNATURE_SHA256_WITH_RSA_ALGORITHM);
        boolean match = CryptoJava.RSA.verify(CONTENT.getBytes(StandardCharsets.UTF_8),
                publicKey, sign, CryptoJava.RSA.SIGNATURE_SHA256_WITH_RSA_ALGORITHM);

        Assertions.assertTrue(match);
    }

    @Test
    void testCryptoRSASign_SHA256_v2() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String CONTENT = "我是密文？";
        String sign = CryptoJava.RSA.SHA256.sign(CONTENT.getBytes(StandardCharsets.UTF_8), privateKey);
        boolean match = CryptoJava.RSA.SHA256.verify(CONTENT.getBytes(StandardCharsets.UTF_8), publicKey, sign);

        Assertions.assertTrue(match);
    }

    @Test
    void testCryptoRSASign_SHA256_v3() throws Exception {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String privateKey = ClassPathReader.joinRead("key/private-key.txt");

        String CONTENT = "我是密文？";
        String sign = CryptoJava.RSA.SHA256.sign(CONTENT, privateKey);
        boolean match = CryptoJava.RSA.SHA256.verify(CONTENT, publicKey, sign);

        Assertions.assertTrue(match);
    }

    @Test
    void testCryptoHashMD5() throws Exception {
        String CONTENT = "我是密文？";
        String md5 = CryptoJava.HASH.MD5.md5(CONTENT);
        Assertions.assertEquals("8cca8a39ab835eb404e27d54508218d4", md5);
    }

    @Test
    void testCryptoHashSha1() throws Exception {
        String CONTENT = "我是密文？";
        String sha1 = CryptoJava.HASH.SHA.sha1(CONTENT);
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
        String CONTENT = "我是密文？";
        String sha384 = CryptoJava.HASH.SHA.sha384(CONTENT);
        Assertions.assertEquals("eda292f6009cbf4b18b31e31b017509f72853d4663acc523a21e915d0188dfb89f16d257b96d66d20a5b43843b280cd1", sha384);
    }

    @Test
    void testCryptoHashSha512() throws Exception {
        String CONTENT = "我是密文？";
        String sha512 = CryptoJava.HASH.SHA.sha512(CONTENT);
        Assertions.assertEquals("7210d5023d5ede3223a0220599e5615d0b88c2338fdaff5917392656833bf5b35325356f21dda195e5408d9a0acdb52b765ef3d0d6eb053c0ef51b85fcbff404", sha512);
    }
}