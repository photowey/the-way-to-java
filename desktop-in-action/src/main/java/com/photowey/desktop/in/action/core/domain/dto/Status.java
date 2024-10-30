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
package com.photowey.desktop.in.action.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code Status}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status implements Serializable {

    public static final String STATUS_UP = "UP";
    public static final String STATUS_DOWN = "DOWN";

    private String status;

    public static Status up() {
        return Status.builder()
            .status(STATUS_UP)
            .build();
    }

    public static Status down() {
        return Status.builder()
            .status(STATUS_DOWN)
            .build();
    }
}
