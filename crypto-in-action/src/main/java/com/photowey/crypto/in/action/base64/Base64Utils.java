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
package com.photowey.crypto.in.action.base64;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

/**
 * {@code Base64Utils}
 * {@code base64} 编解码
 *
 * @author photowey
 * @date 2022/03/03
 * @since 1.0.0
 */
public final class Base64Utils {

    private Base64Utils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static String encrypt(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    public static String encrypt(String data) {
        return Base64.encodeBase64String(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String decryptBase64(String base64Str) {
        return new String(decrypt(base64Str), StandardCharsets.UTF_8);
    }

    public static byte[] decrypt(String base64) {
        return Base64.decodeBase64(base64.getBytes(StandardCharsets.UTF_8));
    }

}
