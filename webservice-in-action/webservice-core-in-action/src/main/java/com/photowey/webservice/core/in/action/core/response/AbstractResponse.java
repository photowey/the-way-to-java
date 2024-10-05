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
package com.photowey.webservice.core.in.action.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;

/**
 * {@code AbstractResponse}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/06
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
//@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractResponse implements Serializable {

    public static final String API_OK = "000000";

    @JsonProperty("CODE")
    //@XmlElement(name = "CODE", required = true)
    protected String code;
    @JsonProperty("MESSAGE")
    //@XmlElement(name = "MESSAGE", required = true)
    protected String message;

    public boolean predicateIsSuccess() {
        return Objects.nonNull(this.code) && API_OK.equalsIgnoreCase(this.code);
    }

    public boolean predicateIsIsFailed() {
        return !this.predicateIsSuccess();
    }
}
