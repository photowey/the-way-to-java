package com.photowey.http.in.action.executor;

import com.photowey.http.in.action.context.RequestContext;
import com.photowey.http.in.action.query.RequestHeaders;
import com.photowey.http.in.action.query.RequestParameters;
import okhttp3.MediaType;
import org.springframework.context.ApplicationContextAware;

/**
 * {@code RequestExecutor}
 *
 * @author weichangjun
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
     * 支持 {@link com.uphicoo.cloud.platform.http.enums.HttpMethod} 多类请求
     *
     * @param context {@code RequestContext}
     * @return 响应字符串
     */
    String execute(RequestContext context);
}
