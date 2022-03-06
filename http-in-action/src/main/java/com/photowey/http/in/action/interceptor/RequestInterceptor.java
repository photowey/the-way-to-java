package com.photowey.http.in.action.interceptor;

import com.photowey.http.in.action.context.RequestContext;
import okhttp3.Request;
import okhttp3.Response;

/**
 * {@code RequestInterceptor}
 * 请求拦截器
 *
 * @author weichangjun
 * @date 2022/03/01
 * @since 1.0.0
 */
public interface RequestInterceptor {

    void preHandle(RequestContext context, Request request);

    void postHandle(RequestContext context, Response response);
}
