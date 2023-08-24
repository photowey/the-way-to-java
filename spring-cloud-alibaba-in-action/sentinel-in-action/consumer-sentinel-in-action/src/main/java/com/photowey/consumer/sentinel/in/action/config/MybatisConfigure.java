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
package com.photowey.consumer.sentinel.in.action.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * {@code MybatisConfigure}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
@Configuration
@MapperScan("com.photowey.consumer.sentinel.in.action.repository")
public class MybatisConfigure {

    /**
     * {@link DataSource}
     *
     * @return {@link DataSource}
     */
    @Bean
    public DataSource druidDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * {@link PlatformTransactionManager}
     *
     * @param druidDataSource {@link DataSource}
     * @return {@link PlatformTransactionManager}
     */
    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource druidDataSource) {
        return new DataSourceTransactionManager(druidDataSource);
    }

    /**
     * {@link SqlSessionTemplate}
     *
     * @param sqlSessionFactory {@link SqlSessionFactory}
     * @return {@link SqlSessionTemplate}
     */
    @Bean
    @ConditionalOnMissingBean(SqlSessionTemplate.class)
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * {@link TransactionTemplate}
     *
     * @param transactionManager {@link PlatformTransactionManager}
     * @return {@link TransactionTemplate}
     */
    @Bean
    @ConditionalOnMissingBean(TransactionTemplate.class)
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    /**
     * {@link MybatisPlusInterceptor}
     *
     * @return {@link MybatisPlusInterceptor}
     */
//    @Bean
//    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
//    public MybatisPlusInterceptor mybatisPlusInterceptor(SqlSessionFactory sqlSessionFactory) {
//        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
//        // default: MYSQL
//        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
//        mybatisPlusInterceptor.addInnerInterceptor(paginationInterceptor);
//        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
//        configuration.addInterceptor(mybatisPlusInterceptor);
//
//        return mybatisPlusInterceptor;
//    }
}
