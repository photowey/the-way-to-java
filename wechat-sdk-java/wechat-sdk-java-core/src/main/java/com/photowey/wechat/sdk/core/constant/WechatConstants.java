/*
 * Copyright © 2023 the original author or authors.
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
package com.photowey.wechat.sdk.core.constant;

import com.photowey.wechat.sdk.core.checker.WechatExceptionChecker;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponents;

/**
 * {@code WechatConstants}
 *
 * @author photowey
 * @date 2023/05/13
 * @since 1.0.0
 */
public interface WechatConstants {

    MediaType APPLICATION_JSON = MediaType.parseMediaType("application/json;charset=UTF-8");

    String EMPTY_STRING = "";
    String WHITE_SPACE_STRING = " ";

    String HEADER_REQUEST_ID = "Request-ID";
    String HEADER_AUTHORIZATION = HttpHeaders.AUTHORIZATION;
    String HEADER_USER_AGENT = "User-Agent";
    String HEADER_WECHAT_PAY_META = "Wechatpay-Meta";
    String HEADER_WECHAT_PAY_TENANT = "Wechatpay-TenantId";
    String HEADER_WECHAT_PAY_SERIAL = "Wechatpay-Serial";
    String HEADER_WECHAT_PAY_SIGNATURE = "Wechatpay-Signature";
    String HEADER_WECHAT_PAY_TIMESTAMP = "Wechatpay-Timestamp";
    String HEADER_WECHAT_PAY_NANCE = "Wechatpay-Nonce";

    /**
     * 包装 URI
     *
     * @param uri {@link UriComponents}
     * @return URL
     */
    static String wrapUriIfNecessary(UriComponents uri) {
        String url = uri.getPath();
        WechatExceptionChecker.checkNotNull(url, "request uri is required");

        String encodedQuery = uri.getQuery();
        if (encodedQuery != null) {
            url += "?" + encodedQuery;
        }

        return url;
    }
}
