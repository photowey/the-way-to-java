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

/**
 * {@code Hobby}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("HOBBY")
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "HOBBY")
public class Hobby implements Serializable {

    private static final long serialVersionUID = -5614818631022278336L;

    @JsonProperty("ID")
    //@XmlElement(name = "ID", required = true)
    private Long id;
    @JsonProperty("NAME")
    //@XmlElement(name = "NAME", required = true)
    private String name;
}
