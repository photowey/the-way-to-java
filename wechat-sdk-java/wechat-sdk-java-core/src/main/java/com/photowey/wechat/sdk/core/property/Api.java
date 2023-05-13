/*
 * Copyright © 2023 the original author or authors.
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
package com.photowey.wechat.sdk.core.property;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * {@code Api}
 *
 * @author photowey
 * @date 2023/05/13
 * @since 1.0.0
 */
@Data
@Validated
public class Api implements Serializable {

    private static final long serialVersionUID = 2766294243027215278L;

    @NotBlank(message = "请配置:App ID")
    private String appId;

    @NotBlank(message = "请配置:App 密钥")
    private String appSecret;
    /**
     * v3 API 密钥
     * TODO 非必须 - 支持: 从数据库加载
     */
    private String apiV3Key;
    /**
     * 商户号
     * TODO 非必须 - 支持: 从数据库加载
     */
    private String mchId;
    /**
     * classpath: /cert/dev/apiclient_cert.p12
     * abs: file:///cert/dev/apiclient_cert.p12
     * TODO 非必须 - 支持: 从数据库加载
     */
    private String certPath;
    @NotBlank(message = "请配置:微信回调域名")
    private String domain;

    public String appId() {
        return this.appId;
    }

    public String appSecret() {
        return this.appSecret;
    }

    public String apiV3Key() {
        return this.apiV3Key;
    }

    public String mchId() {
        return this.mchId;
    }

    public String certPath() {
        return this.certPath;
    }

    public String domain() {
        return this.domain;
    }
}
