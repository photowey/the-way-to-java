/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.virtual.thread.in.action.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code HealthController}
 *
 * @author photowey
 * @date 2022/12/25
 * @since 1.0.0
 */
@RestController
@RequestMapping("/tnt")
public class HealthController {

    /**
     * <pre>
     *     curl -X GET http://localhost:7923/tnt/healthz
     *     http -v :7923/tnt/healthz
     * </pre>
     *
     * @return {@link ResponseEntity<Status>}
     */
    @GetMapping("/healthz")
    public ResponseEntity<Status> healthz() {
        return new ResponseEntity<>(new Status("Up"), HttpStatus.OK);
    }

    public record Status(String status) {

    }
}
