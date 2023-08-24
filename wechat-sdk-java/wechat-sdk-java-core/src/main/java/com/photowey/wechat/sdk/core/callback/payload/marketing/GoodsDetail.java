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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * {@code GoodsDetail}
 *
 * @author photowey
 * @date 2023/05/02
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetail implements Serializable {

    private static final long serialVersionUID = -3145926932933280878L;

    /**
     * 单品编码
     * <p>
     * 单品券创建时录入的单品编码。
     * 示例值：a_goods1
     */
    @Required
    @NotBlank(message = "单品编码:为空")
    @JsonProperty(value = "goods_id")
    @JSONField(name = "goods_id")
    private String goodsId;
    /**
     * 单品数量
     * <p>
     * 单品数据
     * 示例值：7
     */
    @Required
    @NotNull(message = "单品数量:为空")
    @JsonProperty(value = "quantity")
    @JSONField(name = "quantity")
    private Long quantity;
    /**
     * 单品单价
     * <p>
     * 单品单价
     * 示例值：1
     */
    @Required
    @JsonProperty(value = "price")
    @JSONField(name = "price")
    @NotNull(message = "单品单价:为空")
    private Long price;
    /**
     * 优惠金额
     * <p>
     * 优惠金额
     * 示例值：4
     */
    @Required
    @JsonProperty(value = "discount_amount")
    @JSONField(name = "discount_amount")
    @NotNull(message = "优惠金额:为空")
    private Long discountAmount;
}
