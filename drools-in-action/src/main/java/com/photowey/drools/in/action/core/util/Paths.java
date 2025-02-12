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
package com.photowey.drools.in.action.core.util;

import com.photowey.drools.in.action.core.constant.DroolsConstants;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * {@code Paths}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/12
 */
public final class Paths {

    private Paths() {
        throwz(Paths.class);
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    public static String tryTrim(String path) {
        String tmp = path;
        if (tmp.startsWith(DroolsConstants.SLUSH)) {
            tmp = tmp.substring(1);
        }

        if (tmp.endsWith(DroolsConstants.SLUSH)) {
            tmp = tmp.substring(0, tmp.length() - 1);
        }

        return tmp;
    }

    public static String clean(String path) {
        if (!isWindows()) {
            return path;
        }

        return Objects.isNull(path) ? null : path.replace("\\", "/");
    }

    public static String tryExtractRelativePath(Resource resource) {
        try {
            return extractRelativePath(DroolsConstants.JAVA_LOCAL_CLASSPATH_PREFIX, resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractRelativePath(String classpathPrefix, Resource resource)
        throws IOException {
        URL url = resource.getURL();
        String absolutePath = url.toString();

        if (absolutePath.startsWith(DroolsConstants.JAR_PROTOCOL)) {
            int startOfPath = absolutePath.indexOf(DroolsConstants.JAR_PROTOCOL_SYMBOL) + 2;
            return absolutePath.substring(startOfPath);
        }

        if (absolutePath.startsWith(DroolsConstants.FILE_PROTOCOL)) {
            int index = absolutePath.indexOf(classpathPrefix);
            if (index != -1) {
                String fileClasspathPath = absolutePath.substring(index + classpathPrefix.length());

                return clean(fileClasspathPath);
            }

            return absolutePath;
        }

        throw new IllegalArgumentException(
            "Unsupported resource URL protocol: " + url.getProtocol());
    }

    public static <T> void throwz(Class<T> clazz) {
        throw new AssertionError("No " + clazz.getName() + " instances for you!");
    }
}
