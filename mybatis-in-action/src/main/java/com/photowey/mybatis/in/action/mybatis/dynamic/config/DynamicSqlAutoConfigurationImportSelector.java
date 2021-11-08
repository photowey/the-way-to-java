package com.photowey.mybatis.in.action.mybatis.dynamic.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * {@code DynamicSQLAutoConfigurationImportSelector}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public class DynamicSqlAutoConfigurationImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{DynamicSqlSelector.class.getName()};
    }
}
