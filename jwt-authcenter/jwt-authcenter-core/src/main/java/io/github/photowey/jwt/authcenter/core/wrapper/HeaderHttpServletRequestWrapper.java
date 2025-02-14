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
package io.github.photowey.jwt.authcenter.core.wrapper;

import io.github.photowey.jwt.authcenter.core.util.Objects;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.http.HttpHeaders;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code HeaderHttpServletRequestWrapper}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
public class HeaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final HttpHeaders headers;

    public HeaderHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.headers = new HttpHeaders();
    }

    public HeaderHttpServletRequestWrapper(HttpServletRequest request, HttpHeaders headers) {
        super(request);
        this.headers = headers;
    }

    public void addHeader(String name, String value) {
        headers.add(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = this.headers.getFirst(name);
        if (headerValue != null) {
            return headerValue;
        }

        return this.tryHttpServletRequest().getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Map<String, String> allHeaders = new HashMap<>(1 << 4);
        if (Objects.isNotNull(this.headers)) {
            this.headers.forEach((key, value) -> allHeaders.put(key, value.get(0)));
        }

        Enumeration<String> originalHeaders = this.tryHttpServletRequest().getHeaderNames();
        while (originalHeaders.hasMoreElements()) {
            String headerName = originalHeaders.nextElement();
            allHeaders.putIfAbsent(headerName, this.tryHttpServletRequest().getHeader(headerName));
        }

        return Collections.enumeration(allHeaders.keySet());
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (this.headers.containsKey(name)) {
            return Collections.enumeration(Collections.singletonList(this.headers.getFirst(name)));
        }

        return this.tryHttpServletRequest().getHeaders(name);
    }

    private HttpServletRequest tryHttpServletRequest() {
        return (HttpServletRequest) this.getRequest();
    }
}

