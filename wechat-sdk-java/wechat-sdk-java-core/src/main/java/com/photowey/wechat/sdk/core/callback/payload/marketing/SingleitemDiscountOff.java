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
 * {@code SingleitemDiscountOff}
 *
 * @author photowey
 * @date 2023/05/02
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingleitemDiscountOff implements Serializable {

    private static final long serialVersionUID = -1072994662254986014L;

    /**
     * 单品最高优惠价格
     * <p>
     * 单品最高优惠价格，单位：分。
     * 示例值：100
     */
    @Required
    @NotNull(message = "单品最高优惠价格:为空")
    @JsonProperty(value = "single_price_max")
    @JSONField(name = "single_price_max")
    private Long priceMax;
}
