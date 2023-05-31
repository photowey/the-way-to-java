package com.photowey.mybatis.in.action.plugin.shared;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * {@code PluginClassLoader}
 *
 * @author photowey
 * @date 2023/05/31
 * @since 1.0.0
 */
@Slf4j
public class PluginClassLoader extends URLClassLoader {

    private JarFile jarFile;

    private Map<String, byte[]> classBytesMap = new HashMap<>();
    private Map<String, Class<?>> classCacheMap = new HashMap<>();

    @Getter
    private List<String> registeredBean = new ArrayList<>();
    @Getter
    private List<String> controllerBean = new ArrayList<>();

    public PluginClassLoader(URL url, ClassLoader parent) {
        super(new URL[]{url}, parent);
        String jarPath = url.getPath();
        try {
            jarFile = new JarFile(jarPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.init();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (this.findLoadedClass(name) == null) {
            return super.loadClass(name);
        } else {
            return classCacheMap.get(name);
        }
    }

    private void init() {
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final JarEntry jar = entries.nextElement();
            String name = jar.getName();
            if (name.endsWith(".class")) {
                String className = name.replace(".class", "").replaceAll("/", ".");
                try (InputStream input = jarFile.getInputStream(jar)) {
                    byte[] bytes = input.readAllBytes();
                    classBytesMap.put(className, bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        this.cacheClasses();
    }

    private void cacheClasses() {
        // 将每一个字节码进行 Class 载入
        for (Map.Entry<String, byte[]> entry : classBytesMap.entrySet()) {
            String key = entry.getKey();
            try {
                Class<?> clazz = this.loadClass(key);
                classCacheMap.put(key, clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void initBean() {
        for (Map.Entry<String, Class<?>> entry : classCacheMap.entrySet()) {
            String className = entry.getKey();
            final Class<?> clazz = entry.getValue();
            if (this.isSpringBeanClass(clazz)) {
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
                AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
                beanDefinition.setScope("singleton");
                String beanName = className.substring(className.indexOf(".") + 1);
                beanName = StringUtils.uncapitalize(beanName);

                final DefaultListableBeanFactory beanFactory = SpringUtil.getBeanFactory();
                beanFactory.registerBeanDefinition(beanName, beanDefinition);

                if (isControllerBean(clazz)) {
                    controllerBean.add(beanName);
                }
                registeredBean.add(beanName);
            }
        }
    }

    private boolean isSpringBeanClass(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        if (clazz.isInterface()) {
            return false;
        }
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return false;
        }
        return clazz.getAnnotation(Component.class) != null
                || clazz.getAnnotation(Service.class) != null
                || clazz.getAnnotation(Repository.class) != null;
    }

    private boolean isControllerBean(Class<?> clazz) {
        return AnnotatedElementUtils.hasAnnotation(clazz, Controller.class)
                || AnnotatedElementUtils.hasAnnotation(clazz, RequestMapping.class);
    }
}