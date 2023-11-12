package io.github.photowey.proguard.in.action.config;

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
 * @date 2023/11/12
 * @since 1.0.0
 */
@Configuration
@MapperScan("io.github.photowey.proguard.in.action.repository")
public class MybatisConfigure {

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
}
