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
package com.photowey.http.in.action.context;

import com.photowey.http.in.action.enums.BodyType;
import com.photowey.http.in.action.enums.HttpMethod;
import com.photowey.http.in.action.query.RequestHeaders;
import com.photowey.http.in.action.query.RequestParameters;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.net.URL;

/**
 * {@code RequestContext}
 *
 * @author photowey
 * @date 2022/03/01
 * @since 1.0.0
 */
@Data
public class RequestContext implements Serializable {

    private String url;
    private String requestBody;
    private RequestHeaders headers = new RequestHeaders();
    private RequestParameters parameters = new RequestParameters();
    private HttpMethod httpMethod = HttpMethod.GET;
    private BodyType bodyType = BodyType.JSON;

    private Boolean newClient = Boolean.FALSE;

    public RequestContext() {
    }

    public RequestContext(String url) {
        this.url = url;
    }

    public RequestContext(String url, HttpMethod httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod;
    }

    public RequestContext(String url, String requestBody, HttpMethod httpMethod) {
        this.url = url;
        this.requestBody = requestBody;
        this.httpMethod = httpMethod;
    }

    public BodyType getBodyType() {
        return null == bodyType ? BodyType.JSON : bodyType;
    }

    public RequestHeaders getHeaders() {
        return null == headers ? new RequestHeaders() : headers;
    }

    public RequestParameters getParameters() {
        return null == parameters ? new RequestParameters() : parameters;
    }

    public HttpMethod getHttpMethod() {
        return null == httpMethod ? HttpMethod.GET : httpMethod;
    }

    public String schema() {
        if (StringUtils.isEmpty(this.getUrl())) {
            return "";
        }
        try {
            URL url = new URL(this.getUrl());
            return url.getProtocol();
        } catch (Exception ignored) {
        }

        throw new RuntimeException(String.format("parse the url:[%s] for protocol exception", this.getUrl()));
    }

    public String host() {
        if (StringUtils.isEmpty(this.getUrl())) {
            return "";
        }
        try {
            URL url = new URL(this.getUrl());
            return url.getHost();
        } catch (Exception ignored) {
        }

        throw new RuntimeException(String.format("parse the url:[%s] for host exception", this.getUrl()));
    }

}
