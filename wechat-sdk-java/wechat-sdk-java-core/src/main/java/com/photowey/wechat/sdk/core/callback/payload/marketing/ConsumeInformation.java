/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.photowey.wechat.sdk.core.annotation.Required;
import com.photowey.wechat.sdk.core.serializer.time.fastjson.LocalDateTimeRfcPatternFastjsonDeserializer;
import com.photowey.wechat.sdk.core.serializer.time.jackson.LocalDateTimeRfcPatternDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * {@code ConsumeInformation}
 *
 * @author photowey
 * @date 2023/05/02
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumeInformation implements Serializable {

    private static final long serialVersionUID = 6307159382706344710L;

    /**
     * consume_time
     * <p>
     * 代金券核销时间，遵循rfc3339标准格式，
     * 格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE，
     * yyyy-MM-DD表示年月日，
     * T出现在字符串中，表示time元素的开头，
     * HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。
     * 例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * 示例值：2015-05-20T13:29:35+08:00
     */
    @Required
    @NotNull(message = "代金券核销时间:为空")
    @JsonProperty(value = "consume_time")
    @JSONField(name = "consume_time", deserializeUsing = LocalDateTimeRfcPatternFastjsonDeserializer.class)
    @JsonDeserialize(using = LocalDateTimeRfcPatternDeserializer.class)
    private LocalDateTime consumeTime;
    /**
     * 核销商户号
     * <p>
     * 核销代金券的商户号。
     * 校验规则：
     * 该参数目前现在返回的是收款商户号，间连模式下，目前传的是银联和网联的商户号
     * 如果需要知道核销的二级商户号，可以在下载核销明细API里查询看到
     * 示例值：9856081
     */
    @Required
    @NotBlank(message = "核销商户号:为空")
    @JsonProperty(value = "consume_mchid")
    @JSONField(name = "consume_mchid")
    private String consumeMchId;
    /**
     * 核销订单号
     * <p>
     * 微信支付系统生成的订单号
     * 示例值：4200752501201407033233368018
     */
    @Required
    @NotBlank(message = "核销订单号:为空")
    @JsonProperty(value = "transaction_id")
    @JSONField(name = "transaction_id")
    private String transactionId;
    /**
     * 单品信息
     * <p>
     * 商户下单接口传的单品信息。
     */
    @Valid
    @JsonProperty(value = "goods_detail")
    @JSONField(name = "goods_detail")
    private List<GoodsDetail> goodsDetail;
}
