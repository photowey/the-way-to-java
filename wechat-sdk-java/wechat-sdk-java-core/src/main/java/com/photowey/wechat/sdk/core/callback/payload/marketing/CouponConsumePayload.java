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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.photowey.wechat.sdk.core.annotation.Required;
import com.photowey.wechat.sdk.core.callback.notify.AbstractNotifier;
import com.photowey.wechat.sdk.core.enums.CouponStatusEnum;
import com.photowey.wechat.sdk.core.enums.CouponTypeEnum;
import com.photowey.wechat.sdk.core.serializer.time.fastjson.LocalDateTimeRfcPatternFastjsonDeserializer;
import com.photowey.wechat.sdk.core.serializer.time.jackson.LocalDateTimeRfcPatternDeserializer;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@code CouponConsumePayload}
 *
 * @author photowey
 * @date 2023/05/02
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CouponConsumePayload extends AbstractNotifier implements Serializable {

    private static final long serialVersionUID = -5717485729322277993L;

    /**
     * 创建批次的商户号
     * <p>
     * 批次创建方商户号。
     * 示例值：9800064
     */
    @Required
    @NotBlank(message = "创建批次的商户号:为空")
    @JsonProperty(value = "stock_creator_mchid")
    @JSONField(name = "stock_creator_mchid")
    private String creatorMchId;
    /**
     * 批次号
     * <p>
     * 微信为每个代金券批次分配的唯一id。
     * 示例值：9865888
     */
    @Required
    @NotBlank(message = "批次号:为空")
    @JsonProperty(value = "stock_id")
    @JSONField(name = "stock_id")
    private String stockId;
    /**
     * 代金券id
     * <p>
     * 微信为代金券唯一分配的id。
     * 示例值：98674556
     */
    @Required
    @NotBlank(message = "代金券id:为空")
    @JsonProperty(value = "coupon_id")
    @JSONField(name = "coupon_id")
    private String couponId;
    /**
     * 单品优惠特定信息
     */
    @Valid
    @JsonProperty(value = "singleitem_discount_off")
    @JSONField(name = "singleitem_discount_off")
    private SingleitemDiscountOff discountOff;
    /**
     * 减至优惠特定信息
     * <p>
     * 减至优惠限定字段,仅减至优惠场景有返回
     */
    @Valid
    @JsonProperty(value = "discount_to")
    @JSONField(name = "discount_to")
    private DiscountTo discountTo;
    /**
     * 代金券名称
     * <p>
     * 代金券名称
     * 示例值：微信支付代金券
     */
    @Required
    @NotBlank(message = "代金券名称:为空")
    @JsonProperty(value = "coupon_name")
    @JSONField(name = "coupon_name")
    private String couponName;
    /**
     * 代金券状态
     * <p>
     * 代金券状态：
     * SENDED：可用
     * USED：已实扣
     * EXPIRED：已过期
     * 示例值：EXPIRED
     */
    @Required
    @NotNull(message = "代金券状态:为空")
    @JsonProperty(value = "status")
    @JSONField(name = "status")
    private CouponStatusEnum couponStatus;
    /**
     * 代金券描述
     * <p>
     * 代金券描述说明字段。
     * 示例值：微信支付营销
     */
    @JsonProperty(value = "description")
    @JSONField(name = "description")
    private String description;
    /**
     * 领券时间
     * <p>
     * 领券时间，遵循rfc3339标准格式，
     * 格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE，
     * yyyy-MM-DD表示年月日，
     * T出现在字符串中，表示time元素的开头，
     * HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。
     * 例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * 示例值：2015-05-20T13:29:35+08:00
     */
    @Required
    @NotNull(message = "领券时间:为空")
    @JsonProperty(value = "create_time")
    @JSONField(name = "create_time", deserializeUsing = LocalDateTimeRfcPatternFastjsonDeserializer.class)
    @JsonDeserialize(using = LocalDateTimeRfcPatternDeserializer.class)
    private LocalDateTime createTime;
    /**
     * 券类型
     * <p>
     * NORMAL：满减券
     * CUT_TO：减至券
     * 示例值：CUT_TO
     */
    @Required
    @NotNull(message = "券类型:为空")
    @JsonProperty(value = "coupon_type")
    @JSONField(name = "coupon_type")
    private CouponTypeEnum couponType;
    /**
     * 是否无资金流
     * <p>
     * true：是
     * false：否
     * 示例值：true
     */
    @Required
    @JsonProperty(value = "no_cash")
    @JSONField(name = "no_cash")
    @NotNull(message = "是否无资金流:为空")
    private Boolean noCash;
    /**
     * 可用开始时间
     * <p>
     * 可用开始时间，遵循rfc3339标准格式，格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE，yyyy-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * 示例值：2015-05-20T13:29:35+08:00
     */
    @Required
    @JsonProperty(value = "available_begin_time")
    @NotNull(message = "可用开始时间:为空")
    @JSONField(name = "available_begin_time", deserializeUsing = LocalDateTimeRfcPatternFastjsonDeserializer.class)
    @JsonDeserialize(using = LocalDateTimeRfcPatternDeserializer.class)
    private LocalDateTime availableBeginTime;
    /**
     * 可用结束时间
     * <p>
     * 可用结束时间，遵循rfc3339标准格式，格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE，yyyy-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * 示例值：2015-05-20T13:29:35+08:00
     */
    @Required
    @NotNull(message = "可用结束时间:为空")
    @JsonProperty(value = "available_end_time")
    @JSONField(name = "available_end_time", deserializeUsing = LocalDateTimeRfcPatternFastjsonDeserializer.class)
    @JsonDeserialize(using = LocalDateTimeRfcPatternDeserializer.class)
    private LocalDateTime availableEndTime;
    /**
     * 是否单品优惠
     * <p>
     * true：是
     * false：否
     * 示例值：true
     */
    @Required
    @NotNull(message = "是否单品优惠:为空")
    @JsonProperty(value = "singleitem")
    @JSONField(name = "singleitem")
    private Boolean singleitem;
    /**
     * 实扣代金券信息
     * <p>
     * 已实扣代金券信息
     */
    @Valid
    @JsonProperty(value = "normal_coupon_information")
    @JSONField(name = "normal_coupon_information")
    private NormalCouponInformation couponInfo;
    /**
     * 普通满减券信息
     * <p>
     * 普通满减券面额、门槛信息
     */
    @Valid
    @JsonProperty(value = "consume_information")
    @JSONField(name = "consume_information")
    private ConsumeInformation consumeInfo;
}
