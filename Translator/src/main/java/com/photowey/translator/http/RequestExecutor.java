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
package com.photowey.translator.http;

import com.google.common.net.MediaType;
import com.photowey.translator.http.model.RequestHeaders;
import com.photowey.translator.http.model.RequestParameters;

/**
 * {@code RequestExecutor}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public interface RequestExecutor {

    String HTTPS = "https";
    String HTTPS_TLS = "TLS";

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    MediaType FORM = MediaType.parse("application/x-www-form-urlencoded");

    // --------------------------------------------------------- get

    String doGet(String url);

    String doGet(String url, RequestParameters parameters);

    String doGet(String url, RequestHeaders requestHeaders);

    String doGet(String url, RequestParameters parameters, RequestHeaders headers);

}
