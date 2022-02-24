/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * {@code hobbyDTO}
 *
 * @author photowey
 * @date 2022/02/24
 * @since 1.0.0
 */
@Data
public class HobbyDTO implements Serializable {

    @NotBlank(message = "请输入:正确的爱好")
    @Length(min = 0, max = 64, message = "请输入:正确的爱好,最长支持:64个字")
    @SafeHtml(message = "", whitelistType = SafeHtml.WhiteListType.NONE)
    @ApiModelProperty(value = "爱好", example = "badminton", required = true)
    @AllowableValues(required = true, value = "football,basketball,badminton,pingpangball", message = "请输入:正确的爱好(football,basketball,badminton,pingpangball)")
    private String hobby;
}
