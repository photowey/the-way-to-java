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
package com.photowey.scheduled.in.action.dingtalk.sign;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * {@code DingtalkSignAlgorithm}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
public interface DingtalkSignAlgorithm<T extends SignModel> extends SignAlgorithm<T> {

    String HmacSHA256 = "HmacSHA256";

    T sign(String secret);

    default String algorithm(Long timestamp, String secret) throws Exception {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance(HmacSHA256);
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HmacSHA256));
        byte[] signData = mac.doFinal(
            stringToSign.getBytes(StandardCharsets.UTF_8)
        );

        return URLEncoder.encode(
            Base64.getEncoder().encodeToString(signData),
            StandardCharsets.UTF_8
        );
    }
}

