package com.photowey.translator.http.okhttp;

import com.photowey.translator.http.RequestExecutor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * {@code OkhttpRequestExecutor}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public interface OkhttpRequestExecutor extends RequestExecutor {

    Response executeRequest(OkHttpClient okHttpClient, Request request) throws IOException;

    void executeRequest(OkHttpClient okHttpClient, Request request, Consumer<Response> callback) throws IOException;
}
