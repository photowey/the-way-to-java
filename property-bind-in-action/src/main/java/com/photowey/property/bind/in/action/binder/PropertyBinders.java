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
package com.photowey.property.bind.in.action.binder;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * {@code PropertyBinders}
 *
 * @author photowey
 * @date 2023/02/08
 * @since 1.0.0
 */
public final class PropertyBinders {

    private PropertyBinders() {
        // utility class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static <T> T bind(Map<?, Object> ctx, String prefix, Class<T> clazz) {
        ConfigurationPropertySource propertySource = new MapConfigurationPropertySource(ctx);

        return bind(propertySource, prefix, clazz);
    }

    public static <T> T bind(ConfigurationPropertySource propertySource, String prefix, Class<T> clazz) {
        Binder binder = new Binder(propertySource);

        return binder.bind(prefix, clazz).get();
    }

    public static <T> void bind(Map<?, Object> ctx, String prefix, T t) {
        ConfigurationPropertySource propertySource = new MapConfigurationPropertySource(ctx);
        bind(propertySource, prefix, t);
    }

    public static <T> void bind(ConfigurationPropertySource propertySource, String prefix, T t) {
        Binder binder = new Binder(propertySource);
        ResolvableType type = ResolvableType.forClass(t.getClass());
        Bindable<?> target = Bindable.of(type).withExistingValue(t);

        binder.bind(prefix, target);
    }

    public static <T> T bind(Environment environment, String prefix, Class<T> clazz) {
        Binder binder = Binder.get(environment);

        return binder.bind(prefix, clazz).get();
    }

    public static <T> void bind(Environment environment, String prefix, T t) {
        Binder binder = Binder.get(environment);
        ResolvableType type = ResolvableType.forClass(t.getClass());
        Bindable<?> target = Bindable.of(type).withExistingValue(t);

        binder.bind(prefix, target);
    }

}
