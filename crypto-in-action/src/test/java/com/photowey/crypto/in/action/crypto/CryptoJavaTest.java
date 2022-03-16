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

}