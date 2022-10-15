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
