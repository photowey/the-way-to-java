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
