package com.photowey.minio.in.action.config;

import com.photowey.minio.in.action.property.MinIOProperties;
import io.minio.MinioAsyncClient;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code MinIOAutoConfigurer}
 *
 * @author photowey
 * @date 2022/06/06
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(MinIOProperties.class)
public class MinIOAutoConfigurer {

    @Autowired
    private MinIOProperties properties;

    /**
     * MinioClient
     * <p>
     * {@literal @}ConditionalOnExpression("#{'true'.equals(environment['spring.minio.sync.enabled'])}")
     *
     * @return {@link MinioClient}
     */
    @Bean
    @ConditionalOnExpression("${spring.minio.sync.enabled:true}")
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }

    /**
     * MinioAsyncClient
     *
     * @return {@link MinioAsyncClient}
     * {@literal @}ConditionalOnExpression("#{'true'.equals(environment['spring.minio.async.enabled'])}")
     */
    @Bean
    @ConditionalOnExpression("${spring.minio.async.enabled:false}")
    public MinioAsyncClient minioAsyncClient() {
        return MinioAsyncClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }

}
