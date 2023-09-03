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
package com.photowey.wechat.sdk.core.crypto;

import com.photowey.wechat.sdk.core.cert.WechatX509Certificate;
import com.photowey.wechat.sdk.core.domain.sign.verify.Verifier;
import com.photowey.wechat.sdk.core.exception.WechatRequestException;
import com.photowey.wechat.sdk.core.sign.WechatSignature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Base64Utils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Signature;

/**
 * {@code WechatVerifier}
 *
 * @author photowey
 * @date 2023/09/03
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVerifier implements Serializable {

    private static final String BC_PROVIDER = "BC";
    private static final String ALGO_SIGNATURE = "SHA256withRSA";

    private WechatX509Certificate certificate;
    private Verifier verifier;

    public boolean verify() {
        try {
            final String signatureStr = WechatSignature.populateSignTxt(this.verifier);
            Signature signer = Signature.getInstance(ALGO_SIGNATURE, BC_PROVIDER);
            signer.initVerify(this.certificate.x509Certificate());
            signer.update(signatureStr.getBytes(StandardCharsets.UTF_8));

            return signer.verify(Base64Utils.decodeFromString(this.verifier.wechatpaySignature()));
        } catch (Exception e) {
            if (e instanceof WechatRequestException) {
                throw (WechatRequestException) e;
            }
            throw new WechatRequestException(e, "Verify wechat response sign error");
        }
    }
}