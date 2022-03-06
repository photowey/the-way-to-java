/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.http.in.action.interceptor;

import com.photowey.http.in.action.context.RequestContext;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

/**
 * {@code DefaultRequestInterceptor}
 *
 * @author photowey
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
