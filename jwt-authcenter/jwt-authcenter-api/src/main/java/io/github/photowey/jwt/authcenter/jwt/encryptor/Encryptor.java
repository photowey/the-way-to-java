/*
 * Copyright © 2025 the original author or authors.
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
package io.github.photowey.jwt.authcenter.jwt.encryptor;

/**
 * {@code Encryptor}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/15
 */
public interface Encryptor {

    String ENCRYPT_SUBJECT_TEMPLATE = "{}{}{}";
    String DECRYPT_SUBJECT_TEMPLATE = "{}{}";

    String SUBJECT_ENCRYPT_SEPARATOR = ".";
    String SUBJECT_ENCRYPT_PREFIX = "enc";

    /**
     * 加密
     *
     * @param key  密钥
     * @param data 待加密数据
     * @return 解密数据
     */
    String encrypt(String key, String data);

    /**
     * 解密
     *
     * @param key        密钥
     * @param base64Data Base64 加密数据
     * @return 解密数据
     */
    String decrypt(String key, String base64Data);
}

