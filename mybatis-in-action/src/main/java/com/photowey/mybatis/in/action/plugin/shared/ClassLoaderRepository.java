package com.photowey.mybatis.in.action.plugin.shared;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code ClassLoaderRepository}
 *
 * @author photowey
 * @date 2023/05/31
 * @since 1.0.0
 */
@Slf4j
public class ClassLoaderRepository {

    private final Map<String, PluginClassLoader> repositoriesMap = new ConcurrentHashMap<>();

    private ClassLoaderRepository() {
    }

    private static class ClassLoaderRepositoryHolder {
        private static final ClassLoaderRepository INSTANCE = new ClassLoaderRepository();
    }

    public static ClassLoaderRepository getInstance() {
        return ClassLoaderRepositoryHolder.INSTANCE;
    }

    public void add(String pluginName, PluginClassLoader pluginClassLoader) {
        repositoriesMap.put(pluginName, pluginClassLoader);
    }

    public boolean contains(String pluginName) {
        return repositoriesMap.containsKey(pluginName);
    }

    public void remove(String pluginName) {
        PluginClassLoader pluginClassLoader = repositoriesMap.get(pluginName);
        List<String> registeredBean = pluginClassLoader.getRegisteredBean();
        try {
            for (String beanName : registeredBean) {
                log.info("remove bean: " + beanName);
                SpringUtil.getBeanFactory().removeBeanDefinition(beanName);
            }

            pluginClassLoader.close();
            repositoriesMap.remove(pluginName);
        } catch (IOException ioException) {
            log.error("has some error {} dur remove module {}", ioException.getMessage(), pluginName);
        }

    }

    public PluginClassLoader get(String pluginName) {
        return repositoriesMap.get(pluginName);
    }
}