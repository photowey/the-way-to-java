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
package com.photowey.oauth2.authentication.api.security.mapping;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * {@code RequestMappingHandlerMappingExt}
 *
 * @author photowey
 * @date 2022/01/29
 * @since 1.0.0
 */
public class RequestMappingHandlerMappingExt extends RequestMappingHandlerMapping {

    public HandlerMethod getHandlerExternal(HttpServletRequest request) {
        try {
            return super.getHandlerInternal(request);
        } catch (Exception e) {
            throw new RuntimeException("find handler-method exception");
        }

    }
}
