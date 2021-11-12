package com.photowey.spring.in.action.mvc.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * {@code HelloFilter}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Slf4j
public class HelloFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("--- com.photowey.spring.in.action.mvc.filter.HelloFilter#init ---");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("--- com.photowey.spring.in.action.mvc.filter.HelloFilter#doFilter ---");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("--- com.photowey.spring.in.action.mvc.filter.HelloFilter#destroy ---");
    }
}
