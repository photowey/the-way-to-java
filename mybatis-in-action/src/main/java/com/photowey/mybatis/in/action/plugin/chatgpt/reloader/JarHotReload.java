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
package com.photowey.mybatis.in.action.plugin.chatgpt.reloader;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;

/**
 * {@code JarHotReload}
 *
 * @author photowey
 * @date 2023/06/03
 * @since 1.0.0
 */
@Component
public class JarHotReload implements ApplicationContextAware {

    private ConfigurableApplicationContext applicationContext;
    private URLClassLoader classLoader;
    private WatchService watchService;

    @Autowired
    private BeanRegistrar beanRegistrar;

    @Value("${hotReload.jarPath}")
    private String jarPath;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
        try {
            // 创建URLClassLoader，设置jar文件路径为URL
            URL jarUrl = new File(jarPath).toURI().toURL();
            classLoader = new URLClassLoader(new URL[]{jarUrl}, applicationContext.getClassLoader());

            // 创建WatchService，监测jar文件的变动
            watchService = FileSystems.getDefault().newWatchService();

            Path directory = Paths.get(jarPath).getParent();
            directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            // 启动一个线程进行热加载
            Thread watchThread = new Thread(this::watchJarChanges);
            watchThread.setDaemon(true);
            watchThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void watchJarChanges() {
        try {
            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.context().toString().equals(jarPath)) {
                        reloadJar();
                        break;
                    }
                }
                key.reset();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void reloadJar() {
        try {
            // 卸载旧的类
            unloadClasses();

            // 重新创建URLClassLoader，加载新的类
            URL jarUrl = new File(jarPath).toURI().toURL();
            classLoader = new URLClassLoader(new URL[]{jarUrl}, applicationContext.getClassLoader());

            // 调用Spring Boot的refresh()方法刷新容器
            applicationContext.refresh();

            System.out.println("Jar file reloaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadJar_v2() {
        try {
            // 卸载旧的类
            unloadClasses();

            // 重新创建URLClassLoader，加载新的类
            URL jarUrl = new File(jarPath).toURI().toURL();
            classLoader = new URLClassLoader(new URL[]{jarUrl}, applicationContext.getClassLoader());

            // 实例化新的类
            Class<?> hotLoadedClass = classLoader.loadClass("com.example.HotLoadedClass");
            Object hotLoadedObject = hotLoadedClass.newInstance();

            // 注册新的类实例到IOC容器中
            beanRegistrar.registerBean("hotLoadedBean", hotLoadedObject);

            // 调用Spring Boot的refresh()方法刷新容器
            applicationContext.refresh();

            System.out.println("Jar file reloaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unloadClasses() throws Exception {
        if (classLoader instanceof URLClassLoader) {
            URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
            Method method = URLClassLoader.class.getDeclaredMethod("close");
            method.setAccessible(true);
            method.invoke(urlClassLoader);
        }
    }

    // 调用热加载的类
    public void invokeHotLoadedClass() {
        try {
            Class<?> hotLoadedClass = classLoader.loadClass("com.example.HotLoadedClass");
            Object hotLoadedObject = hotLoadedClass.newInstance();

            // 在这里可以调用热加载的类的方法或进行其他操作
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
