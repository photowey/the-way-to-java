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
package com.photowey.spi.in.action.extension.factory;

import com.photowey.spi.in.action.extension.loader.ExtensionLoader;

import java.util.List;
import java.util.Map;

/**
 * {@code ExtensionFactory}
 *
 * @author photowey
 * @date 2023/11/04
 * @since 1.0.0
 */
public final class ExtensionFactory {

    private ExtensionFactory() {
        throw new AssertionError("No " + ExtensionFactory.class.getName() + " instances for you!");
    }

    public static <T> T create(final Class<T> ext) {
        return createLoader(ext).load(determineClassLoader());
    }

    public static void stop() {
        Map<Class<?>, ExtensionLoader<?>> loaders = createLoaders();
        loaders.forEach((key, loader) -> {
            loader.stop();
        });
    }

    public static <T> T create(final Class<T> ext, final String name) {
        return createLoader(ext).load(name, determineClassLoader());
    }

    public static <T> T create(final Class<T> ext, final ClassLoader loader) {
        return createLoader(ext).load(loader);
    }

    public static <T> T create(final Class<T> ext, final String name, final ClassLoader loader) {
        return createLoader(ext).load(name, loader);
    }

    public static <T> T create(final Class<T> ext, final String name, final Object[] args) {
        return createLoader(ext).load(name, args, determineClassLoader());
    }

    public static <T> T create(final Class<T> ext, final String name, final Class<?>[] argsType, final Object[] args) {
        return createLoader(ext).load(name, argsType, args, determineClassLoader());
    }

    public static <T> List<T> creates(final Class<T> ext) {
        return createLoader(ext).loads(determineClassLoader());
    }

    private static ClassLoader determineClassLoader() {
        return ExtensionFactory.class.getClassLoader();
    }

    private static <T> ExtensionLoader<T> createLoader(final Class<T> ext) {
        return ExtensionLoaderFactory.create(ext);
    }

    private static Map<Class<?>, ExtensionLoader<?>> createLoaders() {
        return ExtensionLoaderFactory.loaders();
    }
}
