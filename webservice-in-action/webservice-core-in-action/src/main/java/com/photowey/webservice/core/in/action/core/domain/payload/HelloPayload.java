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
package com.photowey.webservice.core.in.action.core.domain.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * {@code HelloPayload}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("PARA")
public class HelloPayload implements Serializable {

    private static final long serialVersionUID = 8815182225078998816L;

    @JsonProperty("ID")
    private Long id;
    @JsonProperty("NAME")
    private String name;
    @JsonProperty("AGE")
    private Integer age;
    @JsonProperty("LIST")
    private List<Hobby> hobbies;
}
