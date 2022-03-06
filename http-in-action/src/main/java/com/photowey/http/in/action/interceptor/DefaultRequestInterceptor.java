package com.photowey.http.in.action.interceptor;

import com.uphicoo.cloud.platform.http.context.RequestContext;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

/**
 * {@code DefaultRequestInterceptor}
 *
 * @author weichangjun
 * @date 2022/03/01
 * @since 1.0.0
 */
@Component
public class DefaultRequestInterceptor implements RequestInterceptor {

    @Override
    public void preHandle(RequestContext context, Request request) {
        // do nothing
    }

    @Override
    public void postHandle(RequestContext context, Response response) {
        // do nothing
    }
}
