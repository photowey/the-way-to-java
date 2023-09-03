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

import com.photowey.wechat.sdk.core.domain.meta.Meta;
import com.photowey.wechat.sdk.core.exception.WechatRequestException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * {@code WechatDecryptor}
 *
 * @author photowey
 * @date 2023/09/03
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatDecryptor implements Serializable {

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String BC_PROVIDER = "BC";
    private static final String ALGO_AES = "AES";
    private static final int LEN = 128;

    private Meta meta;
    private String nonce;
    private String data;
    private String ciphertext;

    public String decrypt() {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION, BC_PROVIDER);
            String apiV3Key = meta.api().apiV3Key();
            SecretKeySpec key = new SecretKeySpec(apiV3Key.getBytes(StandardCharsets.UTF_8), ALGO_AES);
            GCMParameterSpec spec = new GCMParameterSpec(LEN, nonce.getBytes(StandardCharsets.UTF_8));

            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(data.getBytes(StandardCharsets.UTF_8));

            try {
                byte[] buf = cipher.doFinal(Base64Utils.decodeFromString(ciphertext));
                return new String(buf, StandardCharsets.UTF_8);
            } catch (GeneralSecurityException e) {
                throw new WechatRequestException(e, "解密微信响应数据报文失败");
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException |
                 InvalidAlgorithmParameterException | NoSuchProviderException e) {
            throw new WechatRequestException(e, "不支持微信 API 加解密算法");
        }
    }
}