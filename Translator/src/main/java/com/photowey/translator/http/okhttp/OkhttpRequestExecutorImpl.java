package com.photowey.translator.http.okhttp;

import com.photowey.translator.constant.TranslatorConstants;
import com.photowey.translator.http.model.RequestHeaders;
import com.photowey.translator.http.model.RequestParameters;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

/**
 * {@code OkhttpRequestExecutorImpl}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public class OkhttpRequestExecutorImpl implements OkhttpRequestExecutor {

    private final OkHttpClient okHttpClient;

    public OkhttpRequestExecutorImpl(OkHttpClient okHttpClient ) {
        this.okHttpClient = okHttpClient;
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
            throw new RuntimeException("翻译失败", e);
        }
    }

    private String parseUrl(Request request) {
        return request.url().toString();
    }

    private String populateResponseString(Request request, Response response) throws IOException {
        String result = null != response.body() ? response.body().string() : TranslatorConstants.STRING_EMPTY;
        if (response.isSuccessful()) {
            return result;
        }

        throw new RuntimeException("翻译失败");
    }

    // ---------------------------------------------------------- HEADERS

    private void populateHeaders(RequestHeaders requestHeaders, Request.Builder builder) {
        if (requestHeaders != null && requestHeaders.get().keySet().size() > 0) {
            Set<String> keySet = requestHeaders.get().keySet();
            for (String key : keySet) {
                Object hv = requestHeaders.get(key);
                builder.addHeader(key, hv.toString());
            }
        }
    }

    private void populateParams(RequestParameters requestParameters, StringBuilder queryStrings) {
        if (requestParameters != null && requestParameters.get().keySet().size() > 0) {
            boolean firstAt = true;
            for (String key : requestParameters.get().keySet()) {
                Object value = requestParameters.get(key);
                if (firstAt) {
                    queryStrings.append("?").append(key).append("=").append(value.toString());
                    firstAt = false;
                } else {
                    queryStrings.append("&").append(key).append("=").append(value.toString());
                }
            }
        }
    }

    // ---------------------------------------------------------- EXEC

    @Override
    public Response executeRequest(OkHttpClient okHttpClient, Request request) throws IOException {
        return okHttpClient.newCall(request).execute();
    }

    @Override
    public void executeRequest(OkHttpClient okHttpClient, Request request, Consumer<Response> callback) throws IOException {
        try (Response response = this.executeRequest(okHttpClient, request)) {
            callback.accept(response);
        }
    }
}
