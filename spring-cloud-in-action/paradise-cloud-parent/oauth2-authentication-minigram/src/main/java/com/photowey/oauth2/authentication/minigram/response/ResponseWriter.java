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
package com.photowey.oauth2.authentication.minigram.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * {@code ResponseWriter}
 *
 * @author photowey
 * @date 2022/07/30
 * @since 1.0.0
 */
@Slf4j
public abstract class ResponseWriter {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public void write(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String body = this.objectMapper.writeValueAsString(this.toBody(request));
        PrintWriter printWriter = response.getWriter();
        printWriter.print(body);
        printWriter.flush();
        printWriter.close();
    }

    protected abstract ResponseEntity<?> toBody(HttpServletRequest request);

}