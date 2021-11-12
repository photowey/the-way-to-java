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
package com.photowey.spring.in.action.mvc.app;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

/**
 * {@code HelloApp}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
public class HelloApp {

    public static void main(String[] args) {
        run(HelloApp.class, args);
    }

    public static void run(Object app, String[] args) {
        try {
            // 创建 Tomcat 容器
            Tomcat tomcatServer = new Tomcat();
            tomcatServer.setPort(7923);
            String basePath = System.getProperty("user.dir") + File.separator + "spring-in-action" + File.separator;
            tomcatServer.getHost().setAppBase(basePath);

            StandardContext ctx = (StandardContext) tomcatServer.addWebapp("/", basePath + "src" + File.separator + "main" + File.separator + "resources");

            // 禁止重新载入
            ctx.setReloadable(false);
            // class 文件读取地址
            File additionWebInfClasses = new File("spring-in-action/target/classes");
            // 创建WebRoot
            WebResourceRoot resources = new StandardRoot(ctx);
            // tomcat 内部读取 class 执行
            resources.addPreResources(
                    new DirResourceSet(resources, "/spring-in-action/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));

            ctx.setResources(resources);
            tomcatServer.start();
            // 异步等待请求执行
            tomcatServer.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

}