/*
 *  Copyright 1999-2021 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.photowey.spring.cloud.alibaba.seata.in.action.mysql.support.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Program Name: base-framework-mysql-support
 * <p>
 * Description: MyBatisConfig
 * <p>
 *
 * @author zhangjianwei
 * @author photowey
 * @version 1.0
 * @date 2019/6/12 5:17 PM
 */
@Configuration
@AutoConfigureBefore(value = {MybatisPlusAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class MyBatisConfig {

    /**
     * @param sqlSessionFactory SqlSessionFactory
     * @return SqlSessionTemplate
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 从配置文件获取属性构造datasource，注意前缀，这里用的是druid，根据自己情况配置,
     * 原生datasource前缀取"spring.datasource"
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    /**
     * 构造datasource代理对象，替换原来的datasource
     * <p>
     *
     * @param druidDataSource
     * @return
     * @author photowey
     * @date 2022/03/02
     */
    @Primary
    @Bean("dataSource")
    // public DataSourceProxy dataSourceProxy(DataSource druidDataSource) {
    public DataSource dataSourceProxy(DataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    /**
     * @param dataSourceProxy
     * @return
     * @throws Exception
     * @author photowey
     * @date 2022/03/02
     */
    @Bean(name = "sqlSessionFactory")
    // public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSourceProxy) throws Exception {
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSourceProxy) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSourceProxy);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // bean.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
        bean.setMapperLocations(resolver.getResources("classpath*:mybatis/**/*-mapper.xml"));

        SqlSessionFactory factory = null;
        try {
            factory = bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return factory;
    }

    /**
     * MP 自带分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
}
