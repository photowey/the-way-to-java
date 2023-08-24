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
package com.photowey.spring.in.action.global;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code ExceptionModel}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionModel implements Serializable {

    private static final long serialVersionUID = -5118177527084759916L;

    private Integer status;
    private Integer code;
    private String message;
}
