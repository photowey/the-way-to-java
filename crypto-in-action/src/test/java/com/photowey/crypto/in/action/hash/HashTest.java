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
package com.photowey.crypto.in.action.hash;

import com.photowey.crypto.in.action.reader.ClassPathReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@code HashTest}
 *
 * @author photowey
 * @date 2022/03/03
 * @since 1.0.0
 */
@Slf4j
class HashTest {

    static final String CONTENT = "我是密文？";

    @Test
    void testEncrypt() {
        String md5 = Hash.md5(CONTENT);
        Assertions.assertEquals("8cca8a39ab835eb404e27d54508218d4", md5);
    }

    @Test
    void testSha1() {
        String sha1 = Hash.sha1(CONTENT);
        Assertions.assertEquals("5b472351cd914f010b21e6ccb87bd6ce63d949b4", sha1);
    }

    @Test
    void tesSha256() {
        String publicKey = ClassPathReader.joinRead("key/public-key.txt");
        String sha256 = Hash.sha256(publicKey);
        Assertions.assertEquals("3ef6c6a28baacb8d8e37318ed81d211bc189dbffa942d76574a7cff678b9bcc7", sha256);
    }

}
