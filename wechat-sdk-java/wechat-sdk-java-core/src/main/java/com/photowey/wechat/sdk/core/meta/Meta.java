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
package com.photowey.wechat.sdk.core.meta;

import com.photowey.wechat.sdk.core.meta.cert.Cert;
import com.photowey.wechat.sdk.core.property.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * {@code Meta}
 *
 * @author photowey
 * @date 2024/02/25
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class Meta implements Serializable {

    private static final long serialVersionUID = 8497850853075154844L;

    private String tenantId;
    private Cert cert;
    private Api api;

    public String getTenantId() {
        return tenantId;
    }

    public Cert getCert() {
        return cert;
    }

    public Api getApi() {
        return api;
    }
}
