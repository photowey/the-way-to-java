package com.photowey.spring.in.action.controller;

import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * {@code HelloHttpRequestHandler}
 * FIXME 404 curl http://localhost:7923/hello/http/request/handler
 *
 * @author photowey
 * @date 2021/11/12
 * @see * http://localhost:7923/hello/http/request/handler
 * @since 1.0.0
 */
public class HelloHttpRequestHandler implements HttpRequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        writer.println(String.format("Say hello from spring-in-action hello http-request-handler,path:%s", "/hello/http/request/handler"));
        writer.flush();

        writer.close();
    }
}
