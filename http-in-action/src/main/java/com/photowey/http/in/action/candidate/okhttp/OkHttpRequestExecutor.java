package com.photowey.http.in.action.candidate.okhttp;

import com.photowey.http.in.action.executor.RequestExecutor;
import com.photowey.http.in.action.query.RequestHeaders;
import okhttp3.MediaType;

/**
 * {@code OkHttpRequestExecutor}
 *
 * @author weichangjun
 * @date 2022/02/28
 * @since 1.0.0
 */
public interface OkHttpRequestExecutor extends RequestExecutor {

    MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    String doXml(String url, String xml);

    String doXml(String url, String xml, RequestHeaders requestHeaders);

}
