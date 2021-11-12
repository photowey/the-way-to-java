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
package com.photowey.spring.in.action.mvc.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class LoadServletImpl implements LoadServlet {

    @Override
    public void onStartup(ServletContext servletContext) {

        ServletRegistration.Dynamic helloServlet = servletContext.addServlet("helloServlet", HelloServlet.class);
        helloServlet.setLoadOnStartup(1);
        helloServlet.addMapping("/hello");


        ServletRegistration registration = servletContext.getServletRegistration("default");
        registration.addMapping("*.css", "*.gif", "*.jpg", "*.js", "*.JPG");
    }
}
