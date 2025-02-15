/*
 * Copyright © 2025 the original author or authors.
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
package io.github.photowey.jwt.authcenter.core.util.security;

import io.github.photowey.jwt.authcenter.core.thrower.AssertionErrorThrower;
import io.github.photowey.jwt.authcenter.core.wrapper.HeaderHttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * {@code RequestUtils}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
public final class RequestUtils {

    private RequestUtils() {
        AssertionErrorThrower.throwz(RequestUtils.class);
    }

    @Nullable
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    @Nullable
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        assert response != null;
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        return response;
    }

    public static void resetRequest(HttpServletRequest request) {
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
    }

    public static void resetRequest(HttpServletRequest request, HttpHeaders headers) {
        HeaderHttpServletRequestWrapper requestWrapper =
            new HeaderHttpServletRequestWrapper(request, headers);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(requestWrapper);
        RequestContextHolder.setRequestAttributes(requestAttributes);
    }
}
