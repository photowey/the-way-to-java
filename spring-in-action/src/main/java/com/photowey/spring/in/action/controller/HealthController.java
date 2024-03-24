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
package com.photowey.spring.in.action.controller;

import com.photowey.spring.in.action.ext.spring.web.bind.annotation.HeadMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code HealthController}
 *
 * @author photowey
 * @date 2024/03/24
 * @since 1.0.0
 */
@RestController
public class HealthController {

    /**
     * curl -I http://localhost:7923/healthz
     */
    @HeadMapping("/healthz")
    public void health() {
        System.out.println("health");
    }

}