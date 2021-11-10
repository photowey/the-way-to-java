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
package com.photowey.spring.in.action.dynamic.prosessor;

import com.photowey.spring.in.action.dynamic.annotation.DynamicInjected;
import com.photowey.spring.in.action.dynamic.constant.DynamicInjectedConstants;
import com.photowey.spring.in.action.dynamic.proxy.DynamicInjectedProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code DynamicInjectedAnnotationBeanPostProcessor}
 *
 * @author photowey
 * @date 2021/11/10
 * @since 1.0.0
 */
public class DynamicInjectedAnnotationBeanPostProcessor
        implements SmartInstantiationAwareBeanPostProcessor, MergedBeanDefinitionPostProcessor, BeanFactoryAware, ApplicationContextAware {

    protected final Log logger = LogFactory.getLog(getClass());

    @Nullable
    private ConfigurableListableBeanFactory beanFactory;
    private ApplicationContext applicationContext;

    private final Map<Class<?>, Constructor<?>[]> candidateConstructorsCache = new ConcurrentHashMap<>(256);

    private final Map<String, InjectionMetadata> dynamicInjectedMetadataCache = new ConcurrentHashMap<>(256);

    private final Set<Class<? extends Annotation>> dynamicInjectedAnnotationTypes = new LinkedHashSet<>(4);

    private String requiredParameterName = "required";

    private boolean requiredParameterValue = true;

    public DynamicInjectedAnnotationBeanPostProcessor() {
        this.dynamicInjectedAnnotationTypes.add(DynamicInjected.class);
    }

    /**
     * 负责收集信息
     *
     * @param beanDefinition
     * @param beanType
     * @param beanName
     */
    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        InjectionMetadata metadata = this.findInjectedMetadata(beanName, beanType, null);
        metadata.checkConfigMembers(beanDefinition);
    }

    @Override
    @Nullable
    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, final String beanName) throws BeanCreationException {

        // Quick check on the concurrent map first, with minimal locking.
        Constructor<?>[] candidateConstructors = this.candidateConstructorsCache.get(beanClass);
        if (candidateConstructors == null) {
            // Fully synchronized resolution now...
            synchronized (this.candidateConstructorsCache) {
                candidateConstructors = this.candidateConstructorsCache.get(beanClass);
                if (candidateConstructors == null) {
                    Constructor<?>[] rawCandidates;
                    try {
                        rawCandidates = beanClass.getDeclaredConstructors();
                    } catch (Throwable ex) {
                        throw new BeanCreationException(beanName, "Resolution of declared constructors on bean Class [" +
                                beanClass.getName() + "] from ClassLoader [" + beanClass.getClassLoader() + "] failed", ex);
                    }
                    List<Constructor<?>> candidates = new ArrayList<>(rawCandidates.length);
                    Constructor<?> requiredConstructor = null;
                    Constructor<?> defaultConstructor = null;
                    Constructor<?> primaryConstructor = BeanUtils.findPrimaryConstructor(beanClass);
                    int nonSyntheticConstructors = 0;
                    for (Constructor<?> candidate : rawCandidates) {
                        if (!candidate.isSynthetic()) {
                            nonSyntheticConstructors++;
                        } else if (primaryConstructor != null) {
                            continue;
                        }
                        MergedAnnotation<?> ann = this.findMergedInjectedAnnotation(candidate);
                        if (ann == null) {
                            Class<?> userClass = ClassUtils.getUserClass(beanClass);
                            if (userClass != beanClass) {
                                try {
                                    Constructor<?> superCtor = userClass.getDeclaredConstructor(candidate.getParameterTypes());
                                    ann = this.findMergedInjectedAnnotation(superCtor);
                                } catch (NoSuchMethodException ex) {
                                    // Simply proceed, no equivalent superclass constructor found...
                                }
                            }
                        }
                        if (ann != null) {
                            if (requiredConstructor != null) {
                                throw new BeanCreationException(beanName, "Invalid dynamicInjected-marked constructor: " + candidate +
                                        ". Found constructor with 'required' Injected annotation already: " + requiredConstructor);
                            }
                            boolean required = determineRequiredStatus(ann);
                            if (required) {
                                if (!candidates.isEmpty()) {
                                    throw new BeanCreationException(beanName, "Invalid dynamicInjected-marked constructors: " +
                                            candidates + ". Found constructor with 'required' Injected annotation: " + candidate);
                                }
                                requiredConstructor = candidate;
                            }
                            candidates.add(candidate);
                        } else if (candidate.getParameterCount() == 0) {
                            defaultConstructor = candidate;
                        }
                    }
                    if (!candidates.isEmpty()) {
                        // Add default constructor to list of optional constructors, as fallback.
                        if (requiredConstructor == null) {
                            if (defaultConstructor != null) {
                                candidates.add(defaultConstructor);
                            } else if (candidates.size() == 1 && logger.isInfoEnabled()) {
                                logger.info("Inconsistent constructor declaration on bean with name '" + beanName + "': single dynamicInjected-marked constructor flagged as optional - " +
                                        "this constructor is effectively required since there is no " + "default constructor to fall back to: " + candidates.get(0));
                            }
                        }
                        candidateConstructors = candidates.toArray(new Constructor<?>[0]);
                    } else if (rawCandidates.length == 1 && rawCandidates[0].getParameterCount() > 0) {
                        candidateConstructors = new Constructor<?>[]{rawCandidates[0]};
                    } else if (nonSyntheticConstructors == 2 && primaryConstructor != null &&
                            defaultConstructor != null && !primaryConstructor.equals(defaultConstructor)) {
                        candidateConstructors = new Constructor<?>[]{primaryConstructor, defaultConstructor};
                    } else if (nonSyntheticConstructors == 1 && primaryConstructor != null) {
                        candidateConstructors = new Constructor<?>[]{primaryConstructor};
                    } else {
                        candidateConstructors = new Constructor<?>[0];
                    }
                    this.candidateConstructorsCache.put(beanClass, candidateConstructors);
                }
            }
        }

        return (candidateConstructors.length > 0 ? candidateConstructors : null);
    }

    /**
     * 负责依赖注入
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = this.findInjectedMetadata(beanName, bean.getClass(), pvs);
        try {
            metadata.inject(bean, beanName, pvs);
        } catch (BeanCreationException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "DynamicInjected of autowired dependencies failed", ex);
        }

        return pvs;
    }

    // =========================================

    private InjectionMetadata findInjectedMetadata(String beanName, Class<?> clazz, @Nullable PropertyValues pvs) {
        // Fall back to class name as cache key, for backwards compatibility with custom callers.
        String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
        // Quick check on the concurrent map first, with minimal locking.
        InjectionMetadata metadata = this.dynamicInjectedMetadataCache.get(cacheKey);
        if (InjectionMetadata.needsRefresh(metadata, clazz)) {
            synchronized (this.dynamicInjectedMetadataCache) {
                metadata = this.dynamicInjectedMetadataCache.get(cacheKey);
                if (InjectionMetadata.needsRefresh(metadata, clazz)) {
                    if (metadata != null) {
                        metadata.clear(pvs);
                    }
                    metadata = this.buildInjectedAnnotationMetadata(clazz);
                    this.dynamicInjectedMetadataCache.put(cacheKey, metadata);
                }
            }
        }

        return metadata;
    }

    private InjectionMetadata buildInjectedAnnotationMetadata(Class<?> clazz) {
        List<InjectionMetadata.InjectedElement> elements = new ArrayList<>();
        Class<?> targetClass = clazz;

        do {
            final List<InjectionMetadata.InjectedElement> currElements = new ArrayList<>();

            ReflectionUtils.doWithLocalFields(targetClass, field -> {
                AnnotationAttributes ann = this.findInjectedAnnotation(field);
                if (ann != null) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        if (logger.isInfoEnabled()) {
                            logger.info("DynamicInjected annotation is not supported on static fields: " + field);
                        }
                        return;
                    }
                    boolean required = this.determineRequiredStatus(ann);
                    currElements.add(new InjectedFieldElement(field, required, ann));
                }
            });

            ReflectionUtils.doWithLocalMethods(targetClass, method -> {
                Method bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);
                if (!BridgeMethodResolver.isVisibilityBridgeMethodPair(method, bridgedMethod)) {
                    return;
                }
                AnnotationAttributes ann = this.findInjectedAnnotation(bridgedMethod);
                if (ann != null && method.equals(ClassUtils.getMostSpecificMethod(method, clazz))) {
                    if (Modifier.isStatic(method.getModifiers())) {
                        if (logger.isInfoEnabled()) {
                            logger.info("DynamicInjected annotation is not supported on static methods: " + method);
                        }
                        return;
                    }
                    if (method.getParameterCount() == 0) {
                        if (logger.isInfoEnabled()) {
                            logger.info("DynamicInjected annotation should only be used on methods with parameters: " + method);
                        }
                    }
                    boolean required = this.determineRequiredStatus(ann);
                    PropertyDescriptor pd = BeanUtils.findPropertyForMethod(bridgedMethod, clazz);
                    currElements.add(new InjectedMethodElement(method, required, pd, ann));
                }
            });

            elements.addAll(0, currElements);
            targetClass = targetClass.getSuperclass();
        }
        while (targetClass != null && targetClass != Object.class);

        return new InjectionMetadata(clazz, elements);
    }

    // =========================================

    @Nullable
    private AnnotationAttributes findInjectedAnnotation(AccessibleObject ao) {
        // autowiring annotations have to be local
        if (ao.getAnnotations().length > 0) {
            for (Class<? extends Annotation> type : this.dynamicInjectedAnnotationTypes) {
                AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ao, type);
                if (attributes != null) {
                    return attributes;
                }
            }
        }

        return null;
    }

    @Nullable
    private MergedAnnotation<?> findMergedInjectedAnnotation(AccessibleObject ao) {
        MergedAnnotations annotations = MergedAnnotations.from(ao);
        for (Class<? extends Annotation> type : this.dynamicInjectedAnnotationTypes) {
            MergedAnnotation<?> annotation = annotations.get(type);
            if (annotation.isPresent()) {
                return annotation;
            }
        }

        return null;
    }

    // =========================================

    protected boolean determineRequiredStatus(MergedAnnotation<?> ann) {
        return determineRequiredStatus(new AnnotationAttributes(ann.getType()));
    }

    protected boolean determineRequiredStatus(AnnotationAttributes ann) {
        return (!ann.containsKey(this.requiredParameterName)
                || this.requiredParameterValue == ann.getBoolean(this.requiredParameterName));
    }

    // =========================================

    /**
     * 属性注入 {@code InjectedElement}
     */
    private class InjectedFieldElement extends InjectionMetadata.InjectedElement {

        private final boolean required;

        private volatile boolean cached = false;

        @Nullable
        private volatile Object cachedFieldValue;

        private volatile AnnotationAttributes annotationAttributes;

        public InjectedFieldElement(Field field, boolean required, AnnotationAttributes annotationAttributes) {
            super(field, null);
            this.required = required;
            this.annotationAttributes = annotationAttributes;
        }

        @Override
        protected void inject(Object bean, @Nullable String beanName, @Nullable PropertyValues pvs) throws Throwable {
            Field field = (Field) this.member;
            Class<?> candidateType = field.getType();
            if (candidateType.isInterface()) {
                String candidateName = candidateType.getName();

                String value = annotationAttributes.getString("value");
                String[] candidates = annotationAttributes.getStringArray("candidates");
                String targetProxy = annotationAttributes.getString("targetProxy");

                String beanNameTemplate = DynamicInjectedConstants.BEAN_NAME_TEMPLATE;
                String complexBeanName = String.format(beanNameTemplate, candidateName, value, String.join(",", candidates));

                Object proxy = this.cached
                        ? cachedFieldValue
                        : applicationContext.containsBean(complexBeanName) ? applicationContext.getBean(complexBeanName, candidateType) : null;
                if (null == proxy) {
                    DynamicInjectedProxy injectedProxy = applicationContext.getBean(DynamicInjectedProxy.class);
                    proxy = injectedProxy.buildProxy(targetProxy, annotationAttributes, field.getType(), applicationContext);
                    beanFactory.registerSingleton(complexBeanName, proxy);
                    this.cachedFieldValue = proxy;
                    this.cached = true;
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("registry the proxy class:%s to IOC with complex-bean-name:%s", candidateName, complexBeanName));
                    }
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("hints the proxy-object:%s cache in IOC", complexBeanName));
                    }
                }
                if (null != proxy) {
                    ReflectionUtils.makeAccessible(field);
                    field.set(bean, proxy);
                }
            }
        }

        @Nullable
        private Object resolveFieldValue(Field field, Object bean, @Nullable String beanName) {
            DependencyDescriptor desc = new DependencyDescriptor(field, this.required);
            desc.setContainingClass(bean.getClass());
            Set<String> autowiredBeanNames = new LinkedHashSet<>(1);
            Assert.state(beanFactory != null, "No BeanFactory available");
            TypeConverter typeConverter = beanFactory.getTypeConverter();
            Object value;
            try {
                value = beanFactory.resolveDependency(desc, beanName, autowiredBeanNames, typeConverter);
            } catch (BeansException ex) {
                throw new UnsatisfiedDependencyException(null, beanName, new InjectionPoint(field), ex);
            }
            synchronized (this) {
                if (!this.cached) {
                    Object cachedFieldValue = null;
                    if (value != null || this.required) {
                        cachedFieldValue = desc;
                        registerDependentBeans(beanName, autowiredBeanNames);
                        if (autowiredBeanNames.size() == 1) {
                            String autowiredBeanName = autowiredBeanNames.iterator().next();
                            if (beanFactory.containsBean(autowiredBeanName) && beanFactory.isTypeMatch(autowiredBeanName, field.getType())) {
                                cachedFieldValue = new ShortcutDependencyDescriptor(desc, autowiredBeanName, field.getType());
                            }
                        }
                    }
                    this.cachedFieldValue = cachedFieldValue;
                    this.cached = true;
                }
            }

            return value;
        }
    }

    /**
     * 方法注入 {@code InjectedElement}
     */
    private class InjectedMethodElement extends InjectionMetadata.InjectedElement {

        private final boolean required;

        private volatile boolean cached = false;

        @Nullable
        private volatile Object[] cachedMethodArguments;

        private volatile AnnotationAttributes annotationAttributes;

        public InjectedMethodElement(Method method, boolean required, @Nullable PropertyDescriptor pd, AnnotationAttributes annotationAttributes) {
            super(method, pd);
            this.required = required;
            this.annotationAttributes = annotationAttributes;
        }

        @Override
        protected void inject(Object bean, @Nullable String beanName, @Nullable PropertyValues pvs) throws Throwable {
            if (this.checkPropertySkipping(pvs)) {
                return;
            }
            Method method = (Method) this.member;
            Object[] arguments;
            if (this.cached) {
                // Shortcut for avoiding synchronization...
                arguments = resolveCachedArguments(beanName);
            } else {
                Class<?>[] paramTypes = method.getParameterTypes();
                arguments = new Object[paramTypes.length];
                DependencyDescriptor[] descriptors = new DependencyDescriptor[paramTypes.length];

                Set<String> autowiredBeans = new LinkedHashSet<>(paramTypes.length);

                Assert.state(beanFactory != null, "No BeanFactory available");

                for (int i = 0; i < arguments.length; i++) {
                    MethodParameter methodParam = new MethodParameter(method, i);
                    DependencyDescriptor currDesc = new DependencyDescriptor(methodParam, this.required);
                    currDesc.setContainingClass(bean.getClass());

                    Class<?> candidateType = paramTypes[i];
                    String candidateName = candidateType.getName();

                    String value = annotationAttributes.getString("value");
                    String[] candidates = annotationAttributes.getStringArray("candidates");
                    String targetProxy = annotationAttributes.getString("targetProxy");

                    String beanNameTemplate = DynamicInjectedConstants.BEAN_NAME_TEMPLATE;
                    String complexBeanName = String.format(beanNameTemplate, candidateName, value, String.join(",", candidates));

                    Object proxy = applicationContext.containsBean(complexBeanName) ? applicationContext.getBean(complexBeanName, candidateType) : null;
                    if (null == proxy) {
                        DynamicInjectedProxy injectedProxy = applicationContext.getBean(DynamicInjectedProxy.class);
                        proxy = injectedProxy.buildProxy(targetProxy, annotationAttributes, candidateType, applicationContext);
                        beanFactory.registerSingleton(complexBeanName, proxy);
                        autowiredBeans.add(complexBeanName);
                        if (logger.isDebugEnabled()) {
                            logger.debug(String.format("registry the proxy class:%s to IOC with complex-bean-name:%s", candidateName, complexBeanName));
                        }
                    }
                    descriptors[i] = currDesc;
                    arguments[i] = proxy;
                }
                synchronized (this) {
                    if (!this.cached) {
                        if (arguments != null) {
                            Object[] cachedMethodArguments = new Object[paramTypes.length];
                            System.arraycopy(descriptors, 0, cachedMethodArguments, 0, arguments.length);
                            registerDependentBeans(beanName, autowiredBeans);
                            if (autowiredBeans.size() == paramTypes.length) {
                                Iterator<String> it = autowiredBeans.iterator();
                                for (int i = 0; i < paramTypes.length; i++) {
                                    String autowiredBeanName = it.next();
                                    if (beanFactory.containsBean(autowiredBeanName) && beanFactory.isTypeMatch(autowiredBeanName, paramTypes[i])) {
                                        cachedMethodArguments[i] = new ShortcutDependencyDescriptor(descriptors[i], autowiredBeanName, paramTypes[i]);
                                    }
                                }
                            }
                            this.cachedMethodArguments = cachedMethodArguments;
                        } else {
                            this.cachedMethodArguments = null;
                        }
                        this.cached = true;
                    }
                }
            }
            if (arguments != null) {
                try {
                    ReflectionUtils.makeAccessible(method);
                    method.invoke(bean, arguments);
                } catch (InvocationTargetException ex) {
                    throw ex.getTargetException();
                }
            }
        }

        @Nullable
        private Object[] resolveCachedArguments(@Nullable String beanName) {
            Object[] cachedMethodArguments = this.cachedMethodArguments;
            if (cachedMethodArguments == null) {
                return null;
            }
            Object[] arguments = new Object[cachedMethodArguments.length];
            for (int i = 0; i < arguments.length; i++) {
                arguments[i] = resolvedCachedArgument(beanName, cachedMethodArguments[i]);
            }
            return arguments;
        }

        @Nullable
        private Object[] resolveMethodArguments(Method method, Object bean, @Nullable String beanName) {
            int argumentCount = method.getParameterCount();
            Object[] arguments = new Object[argumentCount];
            DependencyDescriptor[] descriptors = new DependencyDescriptor[argumentCount];
            Set<String> autowiredBeans = new LinkedHashSet<>(argumentCount);
            Assert.state(beanFactory != null, "No BeanFactory available");
            TypeConverter typeConverter = beanFactory.getTypeConverter();
            for (int i = 0; i < arguments.length; i++) {
                MethodParameter methodParam = new MethodParameter(method, i);
                DependencyDescriptor currDesc = new DependencyDescriptor(methodParam, this.required);
                currDesc.setContainingClass(bean.getClass());
                descriptors[i] = currDesc;
                try {
                    Object arg = beanFactory.resolveDependency(currDesc, beanName, autowiredBeans, typeConverter);
                    if (arg == null && !this.required) {
                        arguments = null;
                        break;
                    }
                    arguments[i] = arg;
                } catch (BeansException ex) {
                    throw new UnsatisfiedDependencyException(null, beanName, new InjectionPoint(methodParam), ex);
                }
            }
            synchronized (this) {
                if (!this.cached) {
                    if (arguments != null) {
                        DependencyDescriptor[] cachedMethodArguments = Arrays.copyOf(descriptors, arguments.length);
                        registerDependentBeans(beanName, autowiredBeans);
                        if (autowiredBeans.size() == argumentCount) {
                            Iterator<String> it = autowiredBeans.iterator();
                            Class<?>[] paramTypes = method.getParameterTypes();
                            for (int i = 0; i < paramTypes.length; i++) {
                                String autowiredBeanName = it.next();
                                if (beanFactory.containsBean(autowiredBeanName) && beanFactory.isTypeMatch(autowiredBeanName, paramTypes[i])) {
                                    cachedMethodArguments[i] = new ShortcutDependencyDescriptor(descriptors[i], autowiredBeanName, paramTypes[i]);
                                }
                            }
                        }
                        this.cachedMethodArguments = cachedMethodArguments;
                    } else {
                        this.cachedMethodArguments = null;
                    }
                    this.cached = true;
                }
            }
            return arguments;
        }
    }

    // =========================================

    private void registerDependentBeans(@Nullable String beanName, Set<String> autowiredBeanNames) {
        if (beanName != null) {
            for (String autowiredBeanName : autowiredBeanNames) {
                if (this.beanFactory != null && this.beanFactory.containsBean(autowiredBeanName)) {
                    this.beanFactory.registerDependentBean(autowiredBeanName, beanName);
                }
                if (logger.isTraceEnabled()) {
                    logger.trace("DynamicInjected by type from bean name '" + beanName + "' to bean named '" + autowiredBeanName + "'");
                }
            }
        }
    }

    @Nullable
    private Object resolvedCachedArgument(@Nullable String beanName, @Nullable Object cachedArgument) {
        if (cachedArgument instanceof DependencyDescriptor) {
            DependencyDescriptor descriptor = (DependencyDescriptor) cachedArgument;
            Assert.state(this.beanFactory != null, "No BeanFactory available");
            return this.beanFactory.resolveDependency(descriptor, beanName, null, null);
        } else {
            return cachedArgument;
        }
    }

    // =========================================

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // =========================================

    public void setDynamicInjectedAnnotationType(Class<? extends Annotation> dynamicInjectedAnnotationType) {
        Assert.notNull(dynamicInjectedAnnotationType, "'dynamicInjectedAnnotationType' must not be null");
        this.dynamicInjectedAnnotationTypes.clear();
        this.dynamicInjectedAnnotationTypes.add(dynamicInjectedAnnotationType);
    }

    public void setDynamicInjectedAnnotationTypes(Class<? extends Annotation> dynamicInjectedAnnotationTypes) {
        Assert.notNull(dynamicInjectedAnnotationTypes, "'dynamicInjectedAnnotationTypes' must not be null");
        this.dynamicInjectedAnnotationTypes.clear();
        this.dynamicInjectedAnnotationTypes.add(dynamicInjectedAnnotationTypes);
    }

    public void setRequiredParameterName(String requiredParameterName) {
        this.requiredParameterName = requiredParameterName;
    }

    public void setRequiredParameterValue(boolean requiredParameterValue) {
        this.requiredParameterValue = requiredParameterValue;
    }

    // =========================================

    private static class ShortcutDependencyDescriptor extends DependencyDescriptor {

        private final String shortcut;

        private final Class<?> requiredType;

        public ShortcutDependencyDescriptor(DependencyDescriptor original, String shortcut, Class<?> requiredType) {
            super(original);
            this.shortcut = shortcut;
            this.requiredType = requiredType;
        }

        @Override
        public Object resolveShortcut(BeanFactory beanFactory) {
            return beanFactory.getBean(this.shortcut, this.requiredType);
        }
    }
}
