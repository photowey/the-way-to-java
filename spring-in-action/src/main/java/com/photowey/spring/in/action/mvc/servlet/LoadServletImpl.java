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
