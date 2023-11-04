/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.spi.in.action.extension.loader;

import com.photowey.spi.in.action.core.annotation.SPI;
import com.photowey.spi.in.action.core.entity.ExtensionEntity;
import com.photowey.spi.in.action.core.enums.Scoped;
import com.photowey.spi.in.action.extension.generator.DefaultExtensionNameGenerator;
import com.photowey.spi.in.action.extension.generator.ExtensionNameGenerator;
import com.photowey.spi.in.action.extension.lifecycle.InitializeLifeCycle;
import com.photowey.spi.in.action.extension.lifecycle.LifeCycle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * {@code ExtensionLoader}
 *
 * @author photowey
 * @date 2023/11/04
 * @since 1.0.0
 */
public final class ExtensionLoader<T> {

    private static final Logger log = LoggerFactory.getLogger(ExtensionLoader.class);

    private static final String EXTENSION_DIRECTORY = "META-INF/extensions/";

    private static final Map<Class<?>, ExtensionLoader<?>> LOADERS = new ConcurrentHashMap<>();

    private final TypeHolder<List<ExtensionEntity>> entitiesHolder = new TypeHolder<>();
    private final Map<String, TypeHolder<T>> cachedSingletonInstances = new ConcurrentHashMap<>();

    private final Map<String, ExtensionEntity> nameToEntityMap = new ConcurrentHashMap<>();
    private final Map<Class<?>, ExtensionEntity> classToEntityMap = new ConcurrentHashMap<>();

    private final ExtensionNameGenerator extensionNameGenerator;

    private final Class<T> clazz;

    private ExtensionLoader(final Class<T> clazz) {
        this.clazz = clazz;
        this.extensionNameGenerator = this.initBeanNameGenerator();
    }

    private ExtensionNameGenerator initBeanNameGenerator() {
        return new DefaultExtensionNameGenerator();
    }

    public static Map<Class<?>, ExtensionLoader<?>> loaders() {
        return LOADERS;
    }

    public static <T> ExtensionLoader<T> getExtensionLoader(final Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("extension clazz is null");
        }
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("extension clazz (" + clazz + "is not interface!");
        }
        ExtensionLoader<T> extensionLoader = (ExtensionLoader<T>) LOADERS.get(clazz);
        if (extensionLoader != null) {
            return extensionLoader;
        }
        LOADERS.putIfAbsent(clazz, new ExtensionLoader<>(clazz));
        return (ExtensionLoader<T>) LOADERS.get(clazz);
    }

    public T load(final ClassLoader loader) {
        return this.loadExtension(loader);
    }

    public T load(final String name, final ClassLoader loader) {
        return this.loadExtension(name, loader, null, null);
    }

    public T load(final String name, final Object[] args, final ClassLoader loader) {
        Class<?>[] argsType = null;
        if (args != null && args.length > 0) {
            argsType = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                argsType[i] = args[i].getClass();
            }
        }
        return this.loadExtension(name, loader, argsType, args);
    }

    public T load(final String name, final Class<?>[] argsType, final Object[] args, final ClassLoader loader) {
        return this.loadExtension(name, loader, argsType, args);
    }

    public List<T> loads(final ClassLoader loader) {
        return this.loads(null, null, loader);
    }

    public void stop() {
        for (Map.Entry<String, TypeHolder<T>> entry : this.cachedSingletonInstances.entrySet()) {
            TypeHolder<T> holder = entry.getValue();
            T target = holder.getValue();
            if (target instanceof InitializeLifeCycle) {
                ((InitializeLifeCycle) target).stop();
            }
        }
    }

    private List<T> loads(final Class<?>[] argsType, final Object[] args, final ClassLoader loader) {
        List<Class<?>> all = this.getAllExtensionClass(loader);
        if (all.isEmpty()) {
            return Collections.emptyList();
        }
        return all.stream().map(clazz -> {
            ExtensionEntity ent = this.classToEntityMap.get(clazz);
            return this.getExtensionInstance(ent, argsType, args);
        }).collect(Collectors.toList());
    }

    private List<Class<?>> getAllExtensionClass(final ClassLoader loader) {
        return this.loadAllExtensionClass(loader);
    }

    private T loadExtension(final ClassLoader loader) {
        this.loadAllExtensionClass(loader);
        ExtensionEntity extensionEntity = this.getDefaultExtensionEntity();
        return this.getExtensionInstance(extensionEntity, null, null);
    }

    private T loadExtension(final String name, final ClassLoader loader, final Class<?>[] argTypes, final Object[] args) {
        this.loadAllExtensionClass(loader);
        ExtensionEntity ent = this.getCachedExtensionEntity(name);
        return this.getExtensionInstance(ent, argTypes, args);
    }

    private T getExtensionInstance(final ExtensionEntity entity, final Class<?>[] argTypes, final Object[] args) {
        if (entity == null) {
            log.error("Not found target service implements for: " + this.clazz.getName());
            return null;
        }

        if (Scoped.SINGLETON.equals(entity.getScope())) {
            String entityName = entity.getName();
            if (null == entityName) {
                entityName = this.extensionNameGenerator.generate(entity.getTargetClass());
            }

            TypeHolder<T> holder = this.cachedSingletonInstances.get(entityName);
            if (holder == null) {
                this.cachedSingletonInstances.putIfAbsent(entityName, new TypeHolder<>());
                holder = this.cachedSingletonInstances.get(entityName);
            }

            T instance = holder.getValue();
            if (instance == null) {
                synchronized (this.cachedSingletonInstances) {
                    instance = holder.getValue();
                    if (instance == null) {
                        instance = this.newInstance(entity, argTypes, args);
                        holder.setValue(instance);
                    }
                }
            }

            return instance;
        }

        return this.newInstance(entity, argTypes, args);
    }

    private T newInstance(final ExtensionEntity entity, final Class<?>[] argTypes, final Object[] args) {
        try {
            return this.initInstance(entity.getTargetClass(), argTypes, args);
        } catch (Exception t) {
            throw new IllegalStateException("Extension instance(entity: " + entity + ", class: " + this.clazz + ")  could not be instantiated: " + t.getMessage(), t);
        }
    }

    private ExtensionEntity getDefaultExtensionEntity() {
        return this.entitiesHolder.getValue().stream().findFirst().orElse(null);
    }

    private ExtensionEntity getCachedExtensionEntity(final String name) {
        return this.nameToEntityMap.get(name);
    }

    private List<Class<?>> loadAllExtensionClass(final ClassLoader loader) {
        List<ExtensionEntity> entityList = this.entitiesHolder.getValue();
        if (null == entityList) {
            synchronized (this.entitiesHolder) {
                entityList = this.entitiesHolder.getValue();
                if (null == entityList) {
                    entityList = this.findAllExtensionEntity(loader);
                    this.entitiesHolder.setValue(entityList);
                }
            }
        }

        return entityList.stream()
                .map(ExtensionEntity::getTargetClass)
                .collect(Collectors.toList());
    }

    private List<ExtensionEntity> findAllExtensionEntity(final ClassLoader loader) {
        List<ExtensionEntity> entityList = new ArrayList<>();
        this.loadDirectory(EXTENSION_DIRECTORY + this.clazz.getName(), loader, entityList);
        return entityList.stream()
                .sorted(Comparator.comparing(ExtensionEntity::getOrder))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private void loadDirectory(final String dir, final ClassLoader classLoader, final List<ExtensionEntity> entityList) {
        try {
            Enumeration<URL> urls = classLoader != null ? classLoader.getResources(dir) : ClassLoader.getSystemResources(dir);
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    this.loadResources(entityList, url, classLoader);
                }
            }
        } catch (IOException t) {
            log.error("Load @SPI extension class error {}", dir, t);
        }
    }

    private void loadResources(final List<ExtensionEntity> entityList, final URL url, final ClassLoader classLoader) {
        try (InputStream inputStream = url.openStream()) {
            Properties properties = new Properties();
            properties.load(inputStream);
            properties.forEach((k, v) -> {
                String name = (String) k;
                if (null != name && !name.isEmpty()) {
                    try {
                        this.loadClass(entityList, name, classLoader);
                    } catch (ClassNotFoundException e) {
                        log.warn("Load @SPI extension:[{}] class failed. {}", name, e.getMessage());
                    }
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException("Load @SPI extension resources error", e);
        }
    }

    private void loadClass(final List<ExtensionEntity> entityList, final String className, final ClassLoader loader) throws ClassNotFoundException {
        if (!this.containsClazz(className, loader)) {
            Class<?> targetClass = Class.forName(className, true, loader);
            if (!this.clazz.isAssignableFrom(targetClass)) {
                throw new IllegalStateException("Load @SPI extension resources error," + clazz + " subtype is not of " + targetClass);
            }

            String entityName = this.extensionNameGenerator.generate(targetClass);

            SPI spi = targetClass.getAnnotation(SPI.class);

            ExtensionEntity.ExtensionEntityBuilder builder = ExtensionEntity.builder()
                    .name(entityName)
                    .order(0)
                    .scope(Scoped.SINGLETON)
                    .targetClass(targetClass);

            if (null != spi) {
                entityName = spi.value();
                builder.name(entityName).order(spi.order()).scope(spi.scope());
            }

            ExtensionEntity ext = builder.build();
            entityList.add(ext);

            this.classToEntityMap.put(targetClass, ext);

            if (null != spi) {
                this.nameToEntityMap.put(entityName, ext);
            } else {
                this.nameToEntityMap.put(entityName, ext);
            }
        }
    }

    private boolean containsClazz(final String className, final ClassLoader loader) {
        return this.classToEntityMap.entrySet().stream()
                .filter(entry -> entry.getKey().getName().equals(className))
                .anyMatch(entry -> Objects.equals(entry.getValue().getTargetClass().getClassLoader(), loader));
    }

    private T initInstance(final Class<?> implClazz, final Class<?>[] argTypes, final Object[] args)
            throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        T entity;

        if (null != argTypes && null != args) {
            Constructor<?> constructor = implClazz.getDeclaredConstructor(argTypes);
            entity = this.clazz.cast(constructor.newInstance(args));
        } else {
            entity = this.clazz.cast(implClazz.getDeclaredConstructor().newInstance());
        }

        if (entity instanceof LifeCycle) {
            ((LifeCycle) entity).start();
            if (entity instanceof InitializeLifeCycle) {
                ((InitializeLifeCycle) entity).init();
            }
        }

        return entity;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TypeHolder<T> {

        private volatile T value;
    }
}
