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

    private static final String PLAIN_TXT = "我那些残梦 灵异九霄" +
            "徒忙漫奋斗 满目沧愁" +
            "在滑翔之后 完美坠落" +
            "在四维宇宙 眩目遨游" +
            "我那些烂曲 流窜九州" +
            "云游魂飞奏 音愤符吼" +
            "在宿命身后 不停挥手" +
            "视死如归仇 毫无保留" +
            "黑色的不是夜晚 是漫长的孤单" +
            "看脚下一片黑暗 望头顶星光璀璨" +
            "叹世万物皆可盼 唯真爱最短暂" +
            "失去的永不复返 世守恒而今倍还" +
            "摇旗呐喊的热情 携光阴渐远去" +
            "人世间悲喜烂剧 昼夜轮播不停" +
            "纷飞的滥情男女 情仇爱恨别离" +
            "一代人终将老去 但总有人正年轻";

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
    void testEncrypt_large() throws Exception {
        RSAUtils.encryptByPublicKey(PLAIN_TXT.getBytes(StandardCharsets.UTF_8), publicKey);
        RSAUtils.encrypt(PLAIN_TXT, publicKey);
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
    void testDecrypt_large() throws Exception {
        byte[] data = RSAUtils.encryptByPublicKey(PLAIN_TXT.getBytes(StandardCharsets.UTF_8), RSAUtilsTest.publicKey);
        String encrypt = Base64Utils.encrypt(data);

        byte[] bytes = RSAUtils.decryptByPrivateKey(Base64Utils.decrypt(encrypt), privateKey);
        String decrypt = new String(bytes);

        Assertions.assertEquals(PLAIN_TXT, decrypt);
    }

    @Test
    void testDecryptDefault() throws Exception {
        String encryptedData = RSAUtils.encrypt(CONTENT, RSAUtilsTest.publicKey);
        String decrypt = RSAUtils.decrypt(encryptedData, privateKey);

        Assertions.assertEquals(CONTENT, decrypt);
    }
}