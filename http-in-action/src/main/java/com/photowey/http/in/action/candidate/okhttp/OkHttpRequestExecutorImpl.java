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
package com.photowey.http.in.action.candidate.okhttp;

import com.photowey.http.in.action.context.RequestContext;
import com.photowey.http.in.action.enums.BodyType;
import com.photowey.http.in.action.enums.HttpMethod;
import com.photowey.http.in.action.executor.RequestExecutor;
import com.photowey.http.in.action.interceptor.RequestInterceptor;
import com.photowey.http.in.action.property.PlatformHttpProperties;
import com.photowey.http.in.action.query.RequestHeaders;
import com.photowey.http.in.action.query.RequestParameters;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * {@code OkHttpRequestExecutorImpl}
 * {@code OkHttp} {@link RequestExecutor}
 *
 * @author photowey
 * @date 2022/02/28
 * @since 1.0.0
 */
@Slf4j
public class OkHttpRequestExecutorImpl implements OkHttpRequestExecutor {

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private PlatformHttpProperties platformHttpProperties;

    @Autowired
    private X509TrustManager trustAnyTrustManager;
    @Autowired
    private HostnameVerifier trustAnyHostnameVerifier;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public String doGet(String url) {
        return this.doGet(url, new RequestParameters(), new RequestHeaders());
    }

    @Override
    public String doGet(String url, RequestParameters parameters) {
        return this.doGet(url, parameters, new RequestHeaders());
    }

    @Override
    public String doGet(String url, RequestHeaders requestHeaders) {
        return this.doGet(url, new RequestParameters(), requestHeaders);
    }

    @Override
    public String doGet(String url, RequestParameters parameters, RequestHeaders headers) {
        StringBuilder queryStrings = new StringBuilder(url);
        this.populateParams(parameters, queryStrings);
        Request.Builder builder = new Request.Builder();
        this.populateHeaders(headers, builder);

        Request request = builder.url(queryStrings.toString()).build();
        log.info("do get request and url:[{}]", queryStrings);

        return this.syncExecute(request);
    }

    // ----------------------------------------------------------

    /**
     * 表单形式的 {@code POST}
     */
    @Override
    public String doPost(String url, RequestParameters parameters) {
        return this.doPost(url, parameters, new RequestHeaders());
    }

    @Override
    public String doPost(String url, RequestParameters parameters, RequestHeaders requestHeaders) {
        FormBody.Builder builder = new FormBody.Builder();
        if (parameters != null && parameters.get().keySet().size() > 0) {
            for (String key : parameters.get().keySet()) {
                builder.add(key, parameters.get(key));
            }
        }

        Request.Builder postBuilder = new Request.Builder().url(url).post(builder.build());
        this.populateHeaders(requestHeaders, postBuilder);

        Request request = postBuilder.build();

        return this.syncExecute(request);
    }

    // ----------------------------------------------------------

    @Override
    public String doPost(String url, String body) {
        log.info("do post request and url:[{}]", url);
        return this.executePost(url, body, JSON);
    }

    @Override
    public String doPost(String url, String body, RequestHeaders requestHeaders) {
        log.info("do post request and url:[{}]", url);
        return this.executePost(url, body, JSON, requestHeaders);
    }

    // ----------------------------------------------------------

    @Override
    public String doXml(String url, String xml) {
        log.info("do post request and url:[{}]", url);
        return this.executePost(url, xml, XML);
    }

    @Override
    public String doXml(String url, String xml, RequestHeaders requestHeaders) {
        log.info("do post request and url:[{}]", url);
        return this.executePost(url, xml, XML, requestHeaders);
    }

    // ----------------------------------------------------------

    @Override
    public String execute(RequestContext context) {
        HttpMethod httpMethod = context.getHttpMethod();
        Request request = null;
        switch (httpMethod) {
            case GET:
                request = this.populateGetRequest(context);
                break;
            case POST:
            case PUT:
            case PATCH:
            case DELETE:
                request = this.populateRequest(httpMethod, context);
                break;
            default:
                break;
        }

        OkHttpClient okHttpClient = this.okHttpClient;
        if (context.getNewClient()) {
            OkHttpClient.Builder builder = this.determineClient(context.getUrl());
            this.preBuildClient(builder);
            okHttpClient = this.buildOkHttpClient(builder);
        }

        this.preRequest(okHttpClient, request);

        this.preHandle(context, request);

        return this.handleResponse(request, okHttpClient, context);
    }

    private void preHandle(RequestContext context, Request request) {
        // pre
        Collection<RequestInterceptor> requestInterceptors = this.applicationContext.getBeansOfType(RequestInterceptor.class).values();
        for (RequestInterceptor requestInterceptor : requestInterceptors) {
            requestInterceptor.preHandle(context, request);
        }
    }

    private String handleResponse(final Request request, OkHttpClient okHttpClient, RequestContext context) {
        try (Response response = this.executeRequest(okHttpClient, request)) {
            this.postHandle(context, response);

            return this.populateResponseString(request, response);
        } catch (Exception e) {
            throw new RuntimeException(String.format("handle okhttp3 request:[%s] exception", request.url().toString()), e);
        }
    }

    private void postHandle(RequestContext context, Response response) {
        // post
        Collection<RequestInterceptor> requestInterceptors = this.applicationContext.getBeansOfType(RequestInterceptor.class).values();
        for (RequestInterceptor requestInterceptor : requestInterceptors) {
            requestInterceptor.postHandle(context, response);
        }
    }

    // ----------------------------------------------------------

    protected void preRequest(OkHttpClient okHttpClient, Request request) {
        // do some for subclass if necessary
    }

    protected OkHttpClient buildOkHttpClient(OkHttpClient.Builder builder) {
        OkHttpClient client = builder.build();

        return client;
    }

    protected void preBuildClient(OkHttpClient.Builder builder) {
        // do some for subclass if necessary
    }

    // ---------------------------------------------------------- REQUEST

    protected Request populateGetRequest(String url) {
        return this.populateGetRequest(new RequestContext(url));
    }

    protected Request populateGetRequest(RequestContext context) {
        return this.populateRequest(HttpMethod.GET, context);
    }

    protected Request populatePostRequest(RequestContext context) {
        return this.populateRequest(HttpMethod.POST, context);
    }

    protected Request populatePuttRequest(RequestContext context) {
        return this.populateRequest(HttpMethod.PUT, context);
    }

    protected Request populatePatchRequest(RequestContext context) {
        return this.populateRequest(HttpMethod.PATCH, context);
    }

    protected Request populateDeleteRequest(RequestContext context) {
        return this.populateRequest(HttpMethod.DELETE, context);
    }

    protected Request populateRequest(HttpMethod httpMethod, RequestContext context) {
        BodyType bodyType = context.getBodyType();
        MediaType mediaType = BodyType.JSON.equals(bodyType) ? JSON : FORM;
        String url = context.getUrl();

        RequestBody body = this.determineRequestBody(context, mediaType);

        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("Illegal parameter:url");
        }

        Request.Builder builder = new Request.Builder().url(url);
        switch (httpMethod) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(body);
                break;
            case PUT:
                builder.put(body);
                break;
            case PATCH:
                builder.patch(body);
                break;
            case DELETE:
                builder.delete(body);
                break;
            default:
                break;
        }

        RequestHeaders headers = context.getHeaders();
        this.populateHeaders(builder, headers);

        return builder.build();
    }

    // ---------------------------------------------------------- CLIENT

    public OkHttpClient.Builder determineClient(String url) throws RuntimeException {
        OkHttpClient.Builder builder = this.populateOkHttpClient(url);

        return builder;
    }

    public OkHttpClient.Builder populateOkHttpClient(final String url) {
        OkHttpClient.Builder builder = new OkHttpClient()
                .newBuilder()
                .hostnameVerifier(this.trustAnyHostnameVerifier)
                .connectTimeout(this.platformHttpProperties.getOkHttp().getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(this.platformHttpProperties.getOkHttp().getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(this.platformHttpProperties.getOkHttp().getWriteTimeout(), TimeUnit.SECONDS);

        if (url.contains(HTTPS)) {
            SSLSocketFactory sslSocketFactory = this.createIgnoreVerifySSL();
            builder.sslSocketFactory(sslSocketFactory, this.trustAnyTrustManager);
        }

        return builder;
    }

    private RequestBody determineRequestBody(RequestContext context, MediaType mediaType) {

        if (HttpMethod.GET.equals(context.getHttpMethod())) {
            return null;
        }

        RequestBody body = RequestBody.create(mediaType, context.getRequestBody());
        if (BodyType.FORM.equals(context.getBodyType())) {
            FormBody.Builder builder = new FormBody.Builder();
            if (context.getParameters().get().keySet().size() > 0) {
                for (String key : context.getParameters().get().keySet()) {
                    builder.add(key, context.getParameters().get(key));
                }
            }

            body = builder.build();
        }

        return body;
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------

    // ---------------------------------------------------------- SSL

    /**
     * 创建忽略 {@code https} {@code host} 验证的 {@code SSLSocketFactory}
     *
     * @return {@link SSLSocketFactory}
     */
    private SSLSocketFactory createIgnoreVerifySSL() {
        try {
            TrustManager[] tm = {this.trustAnyTrustManager};
            SSLContext sslContext = SSLContext.getInstance(HTTPS_TLS);
            sslContext.init(null, tm, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException("handle the https SSLSocketFactory exception", e);
        }
    }

    // ----------------------------------------------------------

    private String executePost(String url, String data, MediaType contentType) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        Request request = new Request.Builder().url(url).post(requestBody).build();

        return this.syncExecute(request);
    }

    private String executePost(String url, String data, MediaType contentType, RequestHeaders requestHeaders) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        Request.Builder postBuilder = new Request.Builder().url(url).post(requestBody);
        this.populateHeaders(requestHeaders, postBuilder);

        Request request = postBuilder.build();

        return this.syncExecute(request);
    }

    // ----------------------------------------------------------

    private String syncExecute(Request request) {
        return this.syncExecute(this.okHttpClient, request);
    }

    private String syncExecute(OkHttpClient okHttpClient, Request request) {
        try (Response response = this.executeRequest(okHttpClient, request)) {
            return this.populateResponseString(request, response);
        } catch (Exception e) {
            throw new RuntimeException(String.format("okhttp3 request:[%s] exception", request.url().toString()), e);
        }
    }

    private String populateResponseString(Request request, Response response) throws IOException {
        String result = null != response.body() ? response.body().string() : "";
        if (response.isSuccessful()) {
            // do nothing
            if (log.isDebugEnabled()) {
                log.error("okhttp3 request:[{}] successfully...", request.url().toString());
            }
        } else {
            log.error("okhttp3 request:[{}] failure, status:[{}], result:[{}]", request.url().toString(), response.code(), result);
        }
        return result;
    }

    // ---------------------------------------------------------- HEADERS

    private void populateHeaders(Request.Builder builder, RequestHeaders headers) {
        if (Objects.nonNull(headers)) {
            Set<String> headerKeys = headers.get().keySet();
            for (String header : headerKeys) {
                builder.addHeader(header, String.valueOf(headers.get(header)));
            }
        }
    }

    private void populateHeaders(RequestHeaders requestHeaders, Request.Builder builder) {
        if (requestHeaders != null && requestHeaders.get().keySet().size() > 0) {
            Set<String> keySet = requestHeaders.get().keySet();
            for (String key : keySet) {
                builder.addHeader(key, requestHeaders.get(key));
            }
        }
    }

    private void populateParams(RequestParameters requestParameters, StringBuilder queryStrings) {
        if (requestParameters != null && requestParameters.get().keySet().size() > 0) {
            boolean firstAt = true;
            for (String key : requestParameters.get().keySet()) {
                String value = requestParameters.get(key);
                if (firstAt) {
                    queryStrings.append("?").append(key).append("=").append(value);
                    firstAt = false;
                } else {
                    queryStrings.append("&").append(key).append("=").append(value);
                }
            }
        }
    }

    // ---------------------------------------------------------- EXEC

    private Response executeRequest(OkHttpClient okHttpClient, Request request) throws IOException {
        return okHttpClient.newCall(request).execute();
    }
}
