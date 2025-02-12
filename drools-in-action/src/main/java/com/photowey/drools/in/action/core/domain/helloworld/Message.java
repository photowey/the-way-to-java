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
package com.photowey.drools.in.action.core.domain.helloworld;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * {@code Message}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = -2644911088216136292L;

    public static final int HELLO = 0;
    public static final int GOODBYE = 1;

    private String message;

    private int status;

    public static Message doSomething(Message message) {
        return message;
    }

    public boolean isSomething(String msg, List<Object> list) {
        list.add(this);
        return this.message.equals(msg);
    }
}
