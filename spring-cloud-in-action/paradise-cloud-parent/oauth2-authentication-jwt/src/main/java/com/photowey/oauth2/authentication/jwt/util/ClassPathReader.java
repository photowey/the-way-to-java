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
package com.photowey.oauth2.authentication.jwt.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * {@code ClassPathReader}
 *
 * @author photowey
 * @date 2022/01/18
 * @since 1.0.0
 */
public final class ClassPathReader {

    private ClassPathReader() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static String read(String path) {
        final Resource resource = new ClassPathResource(path);
        try (
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
        ) {
            StringBuilder target = new StringBuilder();
            String content = "";
            while (null != (content = bufferedReader.readLine())) {
                target.append(content);
            }
            return target.toString();
        } catch (IOException e) {
            throw new SecurityException(e);
        }
    }

    public static String joinRead(String path) {
        return joinRead(path, "");
    }

    public static String joinRead(String path, CharSequence delimiter) {
        final ClassPathResource target = new ClassPathResource(path);
        try (
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(target.getInputStream(), StandardCharsets.UTF_8))
        ) {
            return bufferedReader.lines().collect(Collectors.joining(delimiter));
        } catch (IOException e) {
            throw new SecurityException(e);
        }
    }
}
