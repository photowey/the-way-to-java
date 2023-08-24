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
package com.photowey.validator.in.action.example.domain.dto;

import com.photowey.validator.in.action.annotation.AllowableValues;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code HelloDTO}
 *
 * @author photowey
 * @date 2022/02/24
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloDTO implements Serializable {

    private static final long serialVersionUID = -3424831853386042605L;

    /**
     * 恒为正
     * 可以用 {@link javax.validation.constraints.Min} 来替代
     */
    @AllowableValues(required = true, value = "range[0-infinity)")
    @ApiModelProperty(value = "主键标识", example = "1491741940859924481", required = true)
    private Long id;

    /**
     * 不允许: html 输入
     */
    @NotBlank(message = "请输入:正确的姓名")
    @Length(min = 0, max = 64, message = "请输入:正确的姓名,最长支持:64个字")
    @SafeHtml(message = "", whitelistType = SafeHtml.WhiteListType.NONE)
    @ApiModelProperty(value = "名称", example = "探戈玫瑰", required = true)
    private String name;

    /**
     * 连续型数值
     * {@literal @}AllowableValues 可以 用 {@link javax.validation.constraints.Min} 和 {@link javax.validation.constraints.Max} 来替代
     */
    // @Min(value = 0, message = "请输入:人类正确的年龄,最小为:0")
    // @Max(value = 150, message = "请输入:人类正确的年龄,最大为:150")
    @AllowableValues(required = true, value = "range[0-150]", message = "请输入:人类正确的年龄(0,150)")
    @ApiModelProperty(value = "年龄", example = "18", required = true)
    private Integer age;

    @AllowableValues(required = true, value = "range(-infinity-10000]", message = "请输入:正确的温度范围(-infinity,10000]")
    @ApiModelProperty(value = "温度", example = "25", required = true)
    private BigDecimal temperature;

    /**
     * {@link BigDecimal} 校验整数
     */
    @Digits(integer = 10_000_000, fraction = 4, message = "请输入:正确的价格")
    @ApiModelProperty(value = "价格", example = "9.9", required = true)
    private BigDecimal price;

    @AllowableValues(required = true, value = "2,4,8", message = "请输入:正确的订单状态(2,4,8)")
    @ApiModelProperty(value = "订单状态", example = "4", required = true)
    private Integer orderState;

    /**
     * 嵌套验证
     */
    @Valid
    @NotEmpty(message = "请选择:您的爱好")
    private List<HobbyDTO> hobbies = new ArrayList<>();

    /**
     * 列表类型的不好写 example
     */
    @AllowableValues(required = true, value = "range(0.0000-0.9999]", message = "请输入:正确的利率(0.0000,0.9999)")
    private List<BigDecimal> ratios = new ArrayList<>();
}
