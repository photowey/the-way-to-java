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
package com.photowey.webservice.core.in.action.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.ConstructorDetector;
import com.photowey.webservice.core.in.action.proxy.objectmapper.ObjectMapperProxy;
import com.photowey.webservice.core.in.action.proxy.objectmapper.ObjectMapperProxyImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * {@code XmlObjectMapperConfigure}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/05
 */
@Configuration
public class XmlObjectMapperConfigure {

    private static final Map<?, Boolean> FEATURE_DEFAULTS;

    static {
        Map<Object, Boolean> featureDefaults = new HashMap<>();
        featureDefaults.put(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        featureDefaults.put(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        FEATURE_DEFAULTS = Collections.unmodifiableMap(featureDefaults);
    }

    @Bean
    public StandardJackson2XmlObjectMapperBuilderCustomizer standardJackson2XmlObjectMapperBuilderCustomizer(
        ApplicationContext applicationContext,
        JacksonProperties jacksonProperties) {
        return new StandardJackson2XmlObjectMapperBuilderCustomizer(applicationContext, jacksonProperties);
    }

    @Bean
    public ObjectMapperProxy objectMapperProxy(
        ApplicationContext applicationContext,
        List<Jackson2XmlObjectMapperBuilderCustomizer> customizers,
        JacksonProperties properties,
        ObjectMapper objectMapper) {
        Jackson2ObjectMapperBuilder builder = this.jacksonObjectMapperBuilder(applicationContext, customizers);
        ObjectMapper xmlObjectMapper = builder.build();

        return new ObjectMapperProxyImpl(properties, objectMapper, xmlObjectMapper);
    }

    private Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder(
        ApplicationContext applicationContext,
        List<Jackson2XmlObjectMapperBuilderCustomizer> customizers) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.applicationContext(applicationContext);
        this.customize(builder, customizers);

        return builder;
    }

    private void customize(Jackson2ObjectMapperBuilder builder, List<Jackson2XmlObjectMapperBuilderCustomizer> customizers) {
        for (Jackson2XmlObjectMapperBuilderCustomizer customizer : customizers) {
            customizer.customize(builder);
        }
    }

    public interface Jackson2XmlObjectMapperBuilderCustomizer {

        /**
         * Customize the JacksonObjectMapperBuilder.
         *
         * @param jacksonObjectMapperBuilder the JacksonObjectMapperBuilder to customize
         */
        void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder);
    }

    static final class StandardJackson2XmlObjectMapperBuilderCustomizer
        implements Jackson2XmlObjectMapperBuilderCustomizer, Ordered {

        private final ApplicationContext applicationContext;

        private final JacksonProperties jacksonProperties;

        StandardJackson2XmlObjectMapperBuilderCustomizer(
            ApplicationContext applicationContext,
            JacksonProperties jacksonProperties) {
            this.applicationContext = applicationContext;
            this.jacksonProperties = jacksonProperties;
        }

        @Override
        public int getOrder() {
            return 0;
        }

        @Override
        public void customize(Jackson2ObjectMapperBuilder builder) {
            if (this.jacksonProperties.getDefaultPropertyInclusion() != null) {
                builder.serializationInclusion(this.jacksonProperties.getDefaultPropertyInclusion());
            }
            if (this.jacksonProperties.getTimeZone() != null) {
                builder.timeZone(this.jacksonProperties.getTimeZone());
            }
            configureFeatures(builder, FEATURE_DEFAULTS);
            configureVisibility(builder, this.jacksonProperties.getVisibility());
            configureFeatures(builder, this.jacksonProperties.getDeserialization());
            configureFeatures(builder, this.jacksonProperties.getSerialization());
            configureFeatures(builder, this.jacksonProperties.getMapper());
            configureFeatures(builder, this.jacksonProperties.getParser());
            configureFeatures(builder, this.jacksonProperties.getGenerator());
            configureDateFormat(builder);
            configurePropertyNamingStrategy(builder);
            configureModules(builder);
            configureLocale(builder);
            configureDefaultLeniency(builder);
            configureConstructorDetector(builder);

            // Supports XML
            configureXmlMapper(builder);
        }

        private void configureFeatures(Jackson2ObjectMapperBuilder builder, Map<?, Boolean> features) {
            features.forEach((feature, value) -> {
                if (value != null) {
                    if (value) {
                        builder.featuresToEnable(feature);
                    } else {
                        builder.featuresToDisable(feature);
                    }
                }
            });
        }

        private void configureVisibility(Jackson2ObjectMapperBuilder builder,
                                         Map<PropertyAccessor, JsonAutoDetect.Visibility> visibilities) {
            visibilities.forEach(builder::visibility);
        }

        private void configureDateFormat(Jackson2ObjectMapperBuilder builder) {
            // We support a fully qualified class name extending DateFormat or a date
            // pattern string value
            String dateFormat = this.jacksonProperties.getDateFormat();
            if (dateFormat != null) {
                try {
                    Class<?> dateFormatClass = ClassUtils.forName(dateFormat, null);
                    builder.dateFormat((DateFormat) BeanUtils.instantiateClass(dateFormatClass));
                } catch (ClassNotFoundException ex) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                    // Since Jackson 2.6.3 we always need to set a TimeZone (see
                    // gh-4170). If none in our properties fallback to the Jackson's
                    // default
                    TimeZone timeZone = this.jacksonProperties.getTimeZone();
                    if (timeZone == null) {
                        timeZone = new ObjectMapper().getSerializationConfig().getTimeZone();
                    }
                    simpleDateFormat.setTimeZone(timeZone);
                    builder.dateFormat(simpleDateFormat);
                }
            }
        }

        private void configurePropertyNamingStrategy(Jackson2ObjectMapperBuilder builder) {
            // We support a fully qualified class name extending Jackson's
            // PropertyNamingStrategy or a string value corresponding to the constant
            // names in PropertyNamingStrategy which hold default provided
            // implementations
            String strategy = this.jacksonProperties.getPropertyNamingStrategy();
            if (strategy != null) {
                try {
                    configurePropertyNamingStrategyClass(builder, ClassUtils.forName(strategy, null));
                } catch (ClassNotFoundException ex) {
                    configurePropertyNamingStrategyField(builder, strategy);
                }
            }
        }

        private void configurePropertyNamingStrategyClass(Jackson2ObjectMapperBuilder builder,
                                                          Class<?> propertyNamingStrategyClass) {
            builder.propertyNamingStrategy(
                (PropertyNamingStrategy) BeanUtils.instantiateClass(propertyNamingStrategyClass));
        }

        private void configurePropertyNamingStrategyField(Jackson2ObjectMapperBuilder builder, String fieldName) {
            // Find the field (this way we automatically support new constants
            // that may be added by Jackson in the future)
            Field field = findPropertyNamingStrategyField(fieldName);
            Assert.notNull(field, () -> "Constant named '" + fieldName + "' not found");
            try {
                builder.propertyNamingStrategy((PropertyNamingStrategy) field.get(null));
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }

        private Field findPropertyNamingStrategyField(String fieldName) {
            try {
                return ReflectionUtils.findField(com.fasterxml.jackson.databind.PropertyNamingStrategies.class,
                    fieldName, PropertyNamingStrategy.class);
            } catch (NoClassDefFoundError ex) { // Fallback pre Jackson 2.12
                return ReflectionUtils.findField(PropertyNamingStrategy.class, fieldName,
                    PropertyNamingStrategy.class);
            }
        }

        private void configureModules(Jackson2ObjectMapperBuilder builder) {
            Collection<Module> moduleBeans = getBeans(this.applicationContext, Module.class);
            builder.modulesToInstall(moduleBeans.toArray(new Module[0]));
        }

        private void configureLocale(Jackson2ObjectMapperBuilder builder) {
            Locale locale = this.jacksonProperties.getLocale();
            if (locale != null) {
                builder.locale(locale);
            }
        }

        private void configureDefaultLeniency(Jackson2ObjectMapperBuilder builder) {
            Boolean defaultLeniency = this.jacksonProperties.getDefaultLeniency();
            if (defaultLeniency != null) {
                builder.postConfigurer((objectMapper) -> objectMapper.setDefaultLeniency(defaultLeniency));
            }
        }

        private void configureConstructorDetector(Jackson2ObjectMapperBuilder builder) {
            JacksonProperties.ConstructorDetectorStrategy strategy = this.jacksonProperties.getConstructorDetector();
            if (strategy != null) {
                builder.postConfigurer((objectMapper) -> {
                    switch (strategy) {
                        case USE_PROPERTIES_BASED:
                            objectMapper.setConstructorDetector(ConstructorDetector.USE_PROPERTIES_BASED);
                            break;
                        case USE_DELEGATING:
                            objectMapper.setConstructorDetector(ConstructorDetector.USE_DELEGATING);
                            break;
                        case EXPLICIT_ONLY:
                            objectMapper.setConstructorDetector(ConstructorDetector.EXPLICIT_ONLY);
                            break;
                        default:
                            objectMapper.setConstructorDetector(ConstructorDetector.DEFAULT);
                    }
                });
            }
        }

        private static <T> Collection<T> getBeans(ListableBeanFactory beanFactory, Class<T> type) {
            return BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, type).values();
        }

        private void configureXmlMapper(Jackson2ObjectMapperBuilder builder) {
            builder.createXmlMapper(true);
        }
    }
}
