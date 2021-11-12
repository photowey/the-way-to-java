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
package com.photowey.spring.in.action.circular;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * {@code CircularConstructorA}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Data
@Component
public class CircularConstructorA {

    private String name = "I'm CircularConstructorA~";

    private final CircularConstructorB circularConstructorB;

    public CircularConstructorA(CircularConstructorB circularConstructorB) {
        this.circularConstructorB = circularConstructorB;
    }
}
