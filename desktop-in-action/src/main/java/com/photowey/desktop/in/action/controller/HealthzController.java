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
package com.photowey.desktop.in.action.controller;

import com.photowey.desktop.in.action.core.domain.dto.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code HealthzController}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/30
 */
@Slf4j
@RestController
public class HealthzController {

    @GetMapping(value = "/healthz", produces = "application/json")
    public ResponseEntity<Status> healthz() {
        return ResponseEntity.ok(Status.up());
    }

}
