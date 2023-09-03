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

import com.photowey.wechat.sdk.core.constant.WechatConstants;
import com.photowey.wechat.sdk.core.domain.meta.Meta;
import com.photowey.wechat.sdk.core.domain.sign.Signer;
import com.photowey.wechat.sdk.core.exception.WechatRequestException;
import com.photowey.wechat.sdk.core.formatter.StringFormatter;
import com.photowey.wechat.sdk.core.nanoid.NanoidUtils;
import com.photowey.wechat.sdk.core.sign.WechatSignature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Base64Utils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;

/**
 * {@code WechatSigner}
 *
 * @author photowey
 * @date 2023/09/03
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatSigner implements WechatSignature, Serializable {

    private Signer signer;
    private Meta meta;

    public String sign() {
        String timestamp = WechatSignature.populateStringSecondTimestamp();
        String nonceStr = NanoidUtils.randomLowerNanoId();

        PrivateKey privateKey = meta.cert().keyPair().getPrivate();
        String signature = this.doSign(privateKey, signer.method(), signer.url(), timestamp, nonceStr, signer.body());
        String serialNo = meta.cert().serialNumber();
        String token = StringFormatter.format(TOKEN_PATTERN, meta.api().mchId(), nonceStr, timestamp, serialNo, signature);

        StringBuilder buf = new StringBuilder();
        buf.append(SCHEMA).append(WechatConstants.WHITE_SPACE_STRING).append(token);

        return buf.toString();
    }

    public String doSign(PrivateKey privateKey, String... orderedComponents) {
        try {
            Signature signer = Signature.getInstance(ALGO_SIGNATURE, BC_PROVIDER);
            signer.initSign(privateKey);
            final String signatureTxt = WechatSignature.populateSignTxt(orderedComponents);
            signer.update(signatureTxt.getBytes(StandardCharsets.UTF_8));
            return Base64Utils.encodeToString(signer.sign());
        } catch (Exception e) {
            throw new WechatRequestException(e, "创建微信请求报文签名失败");
        }
    }
}
