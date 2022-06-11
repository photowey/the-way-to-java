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
package com.photowey.spring.in.action.mvc.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * {@code HelloServlet}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Slf4j
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("com.photowey.spring.in.action.mvc.servlet.HelloServlet#doGet");
        PrintWriter writer = resp.getWriter();
        writer.print("<h1>HelloServlet</h1>");

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/jsp/ok.jsp");
        requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("com.photowey.spring.in.action.mvc.servlet.HelloServlet#doPost");
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.info("com.photowey.spring.in.action.mvc.servlet.HelloServlet#init");
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("com.photowey.spring.in.action.mvc.servlet.HelloServlet#service");
        super.service(req, resp);
    }
}
