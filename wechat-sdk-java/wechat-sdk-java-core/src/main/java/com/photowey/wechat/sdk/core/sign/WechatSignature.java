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
package com.photowey.wechat.sdk.core.sign;

import com.photowey.wechat.sdk.core.domain.sign.verify.Verifier;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * {@code WechatSignature}
 *
 * @author photowey
 * @date 2023/09/03
 * @since 1.0.0
 */
public interface WechatSignature {

    String BC_PROVIDER = "BC";
    String ALGO_SIGNATURE = "SHA256withRSA";

    String SCHEMA = "WECHATPAY2-SHA256-RSA2048";
    String TOKEN_PATTERN = "mchid=\"{}\",nonce_str=\"{}\",timestamp=\"{}\",serial_no=\"{}\",signature=\"{}\"";

    static String populateSignTxt(String... components) {
        return populateSignTxt(true, components);
    }

    static String populateSignTxt(boolean newLine, String... components) {
        String suffix = newLine ? "\n" : "";
        return Arrays.stream(components)
                .collect(Collectors.joining("\n", "", suffix));
    }

    static String populateSignTxt(Verifier verifier) {
        return populateSignTxt(verifier.wechatpayTimestamp(), verifier.wechatpayNonce(), verifier.body());
    }

    static String populateStringSecondTimestamp() {
        long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));

        return String.valueOf(timestamp);
    }
}
