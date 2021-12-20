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
package com.photowey.emqtt.in.action.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * {@code EmqttMessage}
 *
 * @author photowey
 * @date 2021/12/20
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmqttMessage implements Serializable {

    private static final long serialVersionUID = -4114987132928427022L;

    @NotBlank(message = "topic: can't be null")
    private String topic;
    @NotBlank(message = "content: can't be null")
    private String content;
    @Min(value = 1, message = "the qos min value:1")
    @NotNull(message = "qos: can't be null")
    private Integer qos = 1;
}
