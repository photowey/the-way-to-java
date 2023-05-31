package com.photowey.mybatis.in.action.plugin.shared;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * {@code JarLoader}
 *
 * @author photowey
 * @date 2023/05/31
 * @since 1.0.0
 */
@Component
public class JarLoader implements BeanFactoryAware {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SpringUtil.setBeanFactory((DefaultListableBeanFactory) beanFactory);
    }

    private final MybatisLoader mybatisLoader;
    private final ControllerLoader controllerLoader;

    public JarLoader(MybatisLoader mybatisLoader, ControllerLoader controllerLoader) {
        this.mybatisLoader = mybatisLoader;
        this.controllerLoader = controllerLoader;
    }

    public void loadJar(String jarPath, Plugin plugin) throws Exception {
        File jar = new File(jarPath);
        final URI uri = jar.toURI();
        final ClassLoaderRepository instance = ClassLoaderRepository.getInstance();

        String pluginName = plugin.getModuleName() + "-" + plugin.getVersion();
        if (instance.contains(pluginName)) {
            instance.remove(pluginName);
        }

        PluginClassLoader pluginClassLoader = new PluginClassLoader(uri.toURL(), Thread.currentThread().getContextClassLoader());

        DefaultListableBeanFactory beanFactory = SpringUtil.getBeanFactory();
        beanFactory.setBeanClassLoader(pluginClassLoader);

        Thread.currentThread().setContextClassLoader(pluginClassLoader);
        pluginClassLoader.initBean();
        mybatisLoader.doLoad();

        List<String> controllers = pluginClassLoader.getControllerBean();
        controllerLoader.registerController(controllers);

        instance.add(pluginName, pluginClassLoader);
    }

    public void deletePlugin(String pluginName) {
        if (ClassLoaderRepository.getInstance().contains(pluginName)) {
            ClassLoaderRepository.getInstance().remove(pluginName);
        }
    }
}