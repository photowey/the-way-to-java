/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.photowey.delayqueue.in.action.shared.io.netty.util;

import java.util.Locale;

/**
 * {@code Functions}
 *
 * @author photowey
 * @date 2023/03/26
 * @since 1.0.0
 * copy from netty
 */
interface Functions {

    static final char PACKAGE_SEPARATOR_CHAR = '.';

    String NORMALIZED_OS = normalizeOs(System.getProperty("os.name", ""));
    boolean IS_WINDOWS = isWindows0();
    String OS_WINDOWS = "windows";

    static boolean isWindows() {
        return IS_WINDOWS;
    }

    static boolean isWindows0() {
        return OS_WINDOWS.equals(NORMALIZED_OS);
    }

    static String normalizeOs(String value) {
        value = normalize(value);
        if (value.startsWith("aix")) {
            return "aix";
        }
        if (value.startsWith("hpux")) {
            return "hpux";
        }
        if (value.startsWith("os400")) {
            // Avoid the names such as os4000
            if (value.length() <= 5 || !Character.isDigit(value.charAt(5))) {
                return "os400";
            }
        }
        if (value.startsWith("linux")) {
            return "linux";
        }
        if (value.startsWith("macosx") || value.startsWith("osx") || value.startsWith("darwin")) {
            return "osx";
        }
        if (value.startsWith("freebsd")) {
            return "freebsd";
        }
        if (value.startsWith("openbsd")) {
            return "openbsd";
        }
        if (value.startsWith("netbsd")) {
            return "netbsd";
        }
        if (value.startsWith("solaris") || value.startsWith("sunos")) {
            return "sunos";
        }
        if (value.startsWith("windows")) {
            return "windows";
        }

        return "unknown";
    }

    static String normalize(String value) {
        return value.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
    }

    /**
     * Checks that the given argument is not null. If it is, throws {@link NullPointerException}.
     * Otherwise, returns the argument.
     */
    static <T> T checkNotNull(T arg, String text) {
        if (arg == null) {
            throw new NullPointerException(text);
        }
        return arg;
    }

    /**
     * Checks that the given argument is in range. If it is not, throws {@link IllegalArgumentException}.
     * Otherwise, returns the argument.
     */
    static int checkInRange(int i, int start, int end, String name) {
        if (i < start || i > end) {
            throw new IllegalArgumentException(name + ": " + i + " (expected: " + start + "-" + end + ")");
        }
        return i;
    }

    /**
     * Generates a simplified name from a {@link Class}.  Similar to {@link Class#getSimpleName()}, but it works fine
     * with anonymous classes.
     */
    static String simpleClassName(Class<?> clazz) {
        String className = checkNotNull(clazz, "clazz").getName();
        final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
        if (lastDotIdx > -1) {
            return className.substring(lastDotIdx + 1);
        }
        return className;
    }
}

