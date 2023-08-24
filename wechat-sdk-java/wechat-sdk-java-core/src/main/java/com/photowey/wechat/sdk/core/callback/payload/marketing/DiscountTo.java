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
 * {@code DiscountTo}
 *
 * @author photowey
 * @date 2023/05/02
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountTo implements Serializable {

    private static final long serialVersionUID = 5235716886441179794L;

    /**
     * 减至后优惠单价
     * <p>
     * 减至后优惠单价，单位：分。
     * 示例值：100
     */
    @Required
    @NotNull(message = "减至后优惠单价:为空")
    @JsonProperty(value = "cut_to_price")
    @JSONField(name = "cut_to_price")
    private Long cutToPrice;

    /**
     * 最高价格
     * <p>
     * 可享受优惠的最高价格，单位：分。
     * 示例值：20
     */
    @Required
    @NotNull(message = "最高价格:为空")
    @JsonProperty(value = "max_price")
    @JSONField(name = "max_price")
    private Long maxPrice;
}