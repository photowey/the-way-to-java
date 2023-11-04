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
package com.photowey.spi.in.action.extention.factory;

import com.photowey.spi.in.action.extention.loader.ExtensionLoader;

/**
 * {@code ExtensionLoaderFactory}
 *
 * @author photowey
 * @date 2023/11/04
 * @since 1.0.0
 */
public final class ExtensionLoaderFactory {

    private ExtensionLoaderFactory() {
        throw new AssertionError("No " + ExtensionLoaderFactory.class.getName() + " instances for you!");
    }

    public static <T> ExtensionLoader<T> create(final Class<T> ext) {
        return ExtensionLoader.getExtensionLoader(ext);
    }
}
