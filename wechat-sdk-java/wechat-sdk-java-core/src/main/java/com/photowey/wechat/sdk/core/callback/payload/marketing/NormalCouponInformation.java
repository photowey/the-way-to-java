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
package com.photowey.wechat.sdk.core.callback.payload.marketing;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.photowey.wechat.sdk.core.annotation.Required;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * {@code NormalCouponInformation}
 *
 * @author photowey
 * @date 2023/05/02
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormalCouponInformation implements Serializable {

    private static final long serialVersionUID = -3805900263476683808L;

    /**
     * 面额
     * <p>
     * 面额，单位：分。
     * 示例值：100
     */
    @Required
    @NotNull(message = "面额:为空")
    @JsonProperty(value = "coupon_amount")
    @JSONField(name = "coupon_amount")
    private Long couponAmount;
    /**
     * 门槛
     * <p>
     * 使用券金额门槛，单位：分。
     * 示例值：100
     */
    @Required
    @NotNull(message = "使用券金额门槛:为空")
    @JsonProperty(value = "transaction_minimum")
    @JSONField(name = "transaction_minimum")
    private Long transactionMinimum;
}
