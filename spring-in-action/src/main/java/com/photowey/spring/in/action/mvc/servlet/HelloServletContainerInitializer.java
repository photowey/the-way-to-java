package com.photowey.spring.in.action.mvc.servlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Set;

@HandlesTypes(LoadServlet.class)
public class HelloServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        if (set != null) {
            Iterator<Class<?>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Class<?> clazz = iterator.next();
                if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()) && LoadServlet.class.isAssignableFrom(clazz)) {
                    try {
                        Constructor<?> constructor = clazz.getConstructor();
                        ((LoadServlet) constructor.newInstance()).onStartup(servletContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
