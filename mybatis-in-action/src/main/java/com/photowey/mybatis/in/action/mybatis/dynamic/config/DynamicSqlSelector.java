package com.photowey.mybatis.in.action.mybatis.dynamic.config;

import com.photowey.mybatis.in.action.mybatis.dynamic.annotation.EnableDynamicSql;
import com.photowey.mybatis.in.action.mybatis.dynamic.kernel.repository.CriteriaRepository;
import com.photowey.mybatis.in.action.mybatis.dynamic.kernel.repository.CrudRepository;
import com.photowey.mybatis.in.action.mybatis.dynamic.kernel.repository.Repository;
import com.photowey.mybatis.in.action.mybatis.dynamic.kernel.sql.DynamicSql;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@code DynamicSqlSelector}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Slf4j
public class DynamicSqlSelector implements InitializingBean, ImportAware, ResourceLoaderAware {

    private static final AtomicBoolean LOADED = new AtomicBoolean(false);

    private final SqlSessionFactory sessionFactory;
    private final MapperScannerConfigurer mapperScannerConfigurer;

    private ResourceLoader resourceLoader;

    public DynamicSqlSelector(SqlSessionFactory sessionFactory, MapperScannerConfigurer mapperScannerConfigurer) {
        this.sessionFactory = sessionFactory;
        this.mapperScannerConfigurer = mapperScannerConfigurer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!LOADED.get()) {
            try {
                Field basePackage = ReflectionUtils.findField(mapperScannerConfigurer.getClass(), "basePackage");
                ReflectionUtils.makeAccessible(Objects.requireNonNull(basePackage));

                String mapperScanBasePackage = (String) basePackage.get(mapperScannerConfigurer);
                this.doScan(sessionFactory, mapperScanBasePackage);

            } catch (IllegalAccessException e) {
                log.error("handle mapper-scan basePackage class-scan exception", e);
            }
        }

    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableDynamicSql.class.getName(), false));
        String[] basePackages = Objects.requireNonNull(annotationAttributes).getStringArray("basePackages");
        if (basePackages.length > 0) {
            for (String basePackage : basePackages) {
                this.doScan(sessionFactory, basePackage);
            }
        } else {
            try {
                Field basePackage = ReflectionUtils.findField(mapperScannerConfigurer.getClass(), "basePackage");
                ReflectionUtils.makeAccessible(Objects.requireNonNull(basePackage));
                String basePackageStr = (String) basePackage.get(mapperScannerConfigurer);
                this.doScan(sessionFactory, basePackageStr);
            } catch (IllegalAccessException e) {
                log.error("handle @EnableDynamicSql basePackage class-scan exception", e);
            }
        }

        LOADED.set(true);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void doScan(SqlSessionFactory sqlSessionFactory, String packageName) {
        this.scanClass(sqlSessionFactory, packageName);
    }

    private void scanClass(SqlSessionFactory sqlSessionFactory, String packageName) {
        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
        resolverUtil.find(new ResolverUtil.IsA(Repository.class), packageName);
        Set<Class<? extends Class<?>>> handlerSet = resolverUtil.getClasses();
        // SPI
        ServiceLoader<DynamicSql> dynamicSqlHandlers = ServiceLoader.load(DynamicSql.class);
        for (Class<? extends Class<?>> poClazz : handlerSet) {
            if (this.determineSkip(poClazz)) {
                continue;
            }
            Class<?> genericType = this.determineInterfaceGenericType(poClazz);
            for (DynamicSql dynamicSql : dynamicSqlHandlers) {
                dynamicSql.handleDynamicSql(sqlSessionFactory, Objects.requireNonNull(genericType), poClazz);
            }
        }
    }

    public boolean determineSkip(Class<?> poClazz) {
        return poClazz.getName().equals(Repository.class.getName())
                || poClazz.getName().equals(CrudRepository.class.getName())
                || poClazz.getName().equals(CriteriaRepository.class.getName());
    }

    private Class<?> determineInterfaceGenericType(Class<?> clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            // 由于模块化问题-导致可能获取不到: ParameterizedTypeImpl
            if ("sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl".equals(genericInterface.getClass().getName())) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                Type[] types = parameterizedType.getActualTypeArguments();
                return (Class<?>) types[0];
            }
        }

        return null;
    }
}

