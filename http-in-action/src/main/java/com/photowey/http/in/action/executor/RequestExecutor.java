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
package com.photowey.http.in.action.executor;

import com.photowey.http.in.action.context.RequestContext;
import com.photowey.http.in.action.enums.HttpMethod;
import com.photowey.http.in.action.query.RequestHeaders;
import com.photowey.http.in.action.query.RequestParameters;
import okhttp3.MediaType;
import org.springframework.context.ApplicationContextAware;

/**
 * {@code RequestExecutor}
 *
 * @author photowey
 * @date 2022/02/28
 * @since 1.0.0
 */
public interface RequestExecutor extends ApplicationContextAware {

    String HTTPS = "https";
    String HTTPS_TLS = "TLS";

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    MediaType FORM = MediaType.parse("application/x-www-form-urlencoded");

    // --------------------------------------------------------- get

    String doGet(String url);

    String doGet(String url, RequestParameters parameters);

    String doGet(String url, RequestHeaders requestHeaders);

    String doGet(String url, RequestParameters parameters, RequestHeaders headers);

// --------------------------------------------------------- post-form

    String doPost(String url, RequestParameters parameters);

    String doPost(String url, RequestParameters parameters, RequestHeaders requestHeaders);

    // --------------------------------------------------------- post-json

    String doPost(String url, String body);

    String doPost(String url, String body, RequestHeaders requestHeaders);

    // --------------------------------------------------------- combine

    /**
     * 聚合请求
     * 支持 {@link HttpMethod} 多类请求
     *
     * @param context {@code RequestContext}
     * @return 响应字符串
     */
    String execute(RequestContext context);
}
