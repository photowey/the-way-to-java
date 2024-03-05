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

import com.photowey.wechat.sdk.core.domain.encrypt.EncryptedData;
import com.photowey.wechat.sdk.core.domain.meta.MetaRegistry;
import com.photowey.wechat.sdk.core.domain.sign.Signer;
import com.photowey.wechat.sdk.core.domain.sign.verify.Verifier;

/**
 * {@code SignatureProvider}
 *
 * @author photowey
 * @date 2023/09/03
 * @since 1.0.0
 */
public interface SignatureProvider extends WechatSignature {

    String sign(Signer signer);

    default boolean verify(Verifier verifier) {
        return false;
    }

    default MetaRegistry registry() {
        return null;
    }

    default String decrypt(EncryptedData encrypt) {
        return encrypt.data();
    }

    default void refreshWechatPlatformCert() {
        this.refreshWechatPlatformCert(false);
    }

    default void refreshWechatPlatformCert(boolean skip) {

    }
}
