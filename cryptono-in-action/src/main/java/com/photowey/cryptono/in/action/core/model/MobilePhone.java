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
package com.photowey.cryptono.in.action.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code MobilePhone}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobilePhone implements Serializable {

    private static final long serialVersionUID = -4947093682805201695L;

    private String phoneNumber;

    public static List<String> split(int groupLength, String phoneNumber) {
        List<String> group = new ArrayList<>();

        int times = phoneNumber.length() - groupLength;
        for (int i = 0; i <= times; i++) {
            String member = phoneNumber.substring(i, i + groupLength);
            group.add(member);
        }

        return group;
    }
}