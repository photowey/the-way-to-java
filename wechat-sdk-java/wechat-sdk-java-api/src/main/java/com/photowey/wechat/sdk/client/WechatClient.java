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
package com.photowey.wechat.sdk.client;

import com.photowey.wechat.sdk.core.enums.WechatV3APIEnum;
import com.photowey.wechat.sdk.core.sign.SignatureProvider;
import org.springframework.web.client.RestOperations;

/**
 * {@code WechatClient}
 *
 * @author photowey
 * @date 2024/03/06
 * @since 1.0.0
 */
public interface WechatClient {

    /**
     * 签名器
     *
     * @return {@link SignatureProvider}
     */
    SignatureProvider signatureProvider();

    /**
     * 请求客户端
     *
     * @return {@link RestOperations}
     */
    RestOperations restTemplate();

    /**
     * API
     *
     * @param api   {@link WechatV3APIEnum}
     * @param model 数据包
     * @param <M>   数据包类型
     * @return M 类型
     */
    <M> RequestExecutor<M> api(WechatV3APIEnum api, M model);
}
