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
package com.photowey.druid.in.action.filter;

import com.alibaba.druid.util.Utils;

import javax.servlet.*;
import java.io.IOException;

/**
 * {@code DruidAdFilter}
 *
 * @author photowey
 * @date 2022/02/20
 * @since 1.0.0
 */
public class DruidAdFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        response.resetBuffer();
        final String filePath = "support/http/resources/js/common.js";
        String text = Utils.readFromResource(filePath);
        text = text.replaceAll("<a.*?banner\"></a><br/>", "");
        text = text.replaceAll("powered.*?shrek.wang</a>", "");
        response.getWriter().write(text);
    }

    @Override
    public void destroy() {
    }
}
