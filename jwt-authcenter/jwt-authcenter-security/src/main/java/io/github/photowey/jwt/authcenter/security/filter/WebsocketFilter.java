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
package io.github.photowey.jwt.authcenter.security.filter;

import jakarta.servlet.http.HttpServletRequest;

/**
 * {@code WebsocketFilter}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/02
 */
public interface WebsocketFilter extends RootFilter {

    String HEADER_CONNECTION_KEY = "Connection";
    String HEADER_CONNECTION_VALUE_UPGRADE = "Upgrade";
    String HEADER_UPGRADE_KEY = "Upgrade";
    String HEADER_UPGRADE_VALUE_WEBSOCKET = "websocket";

    /**
     * 判断请求是否为 {@code Websocket} 请求
     *
     * @param httpRequest {@link HttpServletRequest}
     * @return {@code boolean} true: 是; false: 否
     */
    default boolean determineIsWebSocketRequest(HttpServletRequest httpRequest) {
        String conn = httpRequest.getHeader(HEADER_CONNECTION_KEY);
        String upgrade = httpRequest.getHeader(HEADER_UPGRADE_KEY);

        return HEADER_CONNECTION_VALUE_UPGRADE.equalsIgnoreCase(conn)
            && HEADER_UPGRADE_VALUE_WEBSOCKET.equalsIgnoreCase(upgrade);
    }
}
