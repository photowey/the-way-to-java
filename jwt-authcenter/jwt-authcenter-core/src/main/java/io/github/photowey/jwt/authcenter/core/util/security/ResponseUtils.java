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

import io.github.photowey.jwt.authcenter.core.domain.json.JSON;
import io.github.photowey.jwt.authcenter.core.domain.model.ExceptionBody;
import io.github.photowey.jwt.authcenter.core.enums.ExceptionStatus;
import io.github.photowey.jwt.authcenter.core.exception.AuthcenterException;
import io.github.photowey.jwt.authcenter.core.exception.AuthcenterSecurityException;
import io.github.photowey.jwt.authcenter.core.thrower.AssertionErrorThrower;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * {@code ResponseUtils}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/14
 */
public final class ResponseUtils {

    private ResponseUtils() {
        AssertionErrorThrower.throwz(ResponseUtils.class);
    }

    public static PrintWriter getPrintWriter() throws IOException {
        HttpServletResponse response = RequestUtils.getResponse();
        assert response != null;
        return getPrintWriter(response);
    }

    public static PrintWriter getPrintWriter(HttpServletResponse response) throws IOException {
        return response.getWriter();
    }

    public static void toJSONBody(String body) {
        write(JSON.toJSONString(body));
    }

    public static <T> void toJSONBody(T body) {
        write(JSON.toJSONString(body));
    }

    public static <T> void toJSONBody(AuthcenterException exception) {
        write(new ExceptionBody(exception.code(), exception.message()));
    }

    public static void toJSONBody(ExceptionStatus status) {
        write(new ExceptionBody(status));
    }

    public static void toJSONBody(ExceptionStatus status, String message) {
        write(new ExceptionBody(status, message));
    }

    public static <T> void write(T body) {
        write(JSON.toJSONString(body));
    }

    public static void write(String text) {
        write(text, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    public static void write(String text, String contentType) {
        HttpServletResponse response = RequestUtils.getResponse();
        assert response != null;
        write(response, text, contentType);
    }

    public static void write(HttpServletResponse response, String text, String contentType) {
        response.setContentType(contentType);
        try (PrintWriter writer = getPrintWriter(response)) {
            writer.write(text);
            writer.flush();
        } catch (Exception e) {
            throw new AuthcenterSecurityException(e);
        }
    }
}

