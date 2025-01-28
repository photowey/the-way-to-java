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
package io.github.photowey.jwt.authcenter.core.constant;

import io.github.photowey.jwt.authcenter.core.util.Strings;

/**
 * {@code CommonConstants}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/01/28
 */
public interface CommonConstants {

    interface Symbol {
        String SLASH = "/";

        String DOT = ".";
        String COMMA = ",";
        String SEMICOLON = ";";
    }

    interface Unit {
        long MILLIS_UNIT = 1000L;
    }

    interface Api {

        int HTTP_STATUS_OK = 200;
        int HTTP_STATUS_BAD_REQUEST = 400;
        int HTTP_STATUS_UNAUTHORIZED = 401;
        int HTTP_STATUS_FORBIDDEN = 403;
        int HTTP_STATUS_SYSTEM_ERROR = 500;

        // ----------------------------------------------------------------

        String HEARTBEAT_PING = "PING";
        String HEARTBEAT_PONG = "PONG";

        // ----------------------------------------------------------------

        /**
         * [/]xxx/**
         * http[s]://${domain}/${path}[/]
         */
        String API_TEMPLATE = "%s%s";

        // ----------------------------------------------------------------

        static String cleanPrefixIfNecessary(String api) {
            if (Strings.isNotEmpty(api) && api.startsWith(Symbol.SLASH)) {
                return api.substring(1);
            }

            return api;
        }

        static String insertPrefixIfNecessary(String api) {
            if (Strings.isNotEmpty(api) && !api.startsWith(Symbol.SLASH)) {
                return String.format(API_TEMPLATE, Symbol.SLASH, api);
            }

            return api;
        }

        static String appendSuffixIfNecessary(String api) {
            if (Strings.isNotEmpty(api) && !api.endsWith(Symbol.SLASH)) {
                return String.format(API_TEMPLATE, api, Symbol.SLASH);
            }

            return api;
        }

        static String cleanSuffixIfNecessary(String api) {
            if (Strings.isNotEmpty(api) && api.endsWith(Symbol.SLASH)) {
                return api.substring(0, api.length() - 1);
            }

            return api;
        }
    }
}

