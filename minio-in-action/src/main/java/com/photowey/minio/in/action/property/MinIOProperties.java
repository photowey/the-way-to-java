package com.photowey.minio.in.action.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code MinIOProperties}
 *
 * @author photowey
 * @date 2022/06/06
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.minio", ignoreUnknownFields = true)
public class MinIOProperties {

    private String endpoint;
    private String accessKey;
    private String secretKey;

    private Sync sync = new Sync();
    private Async async = new Async();

    @Data
    public static class Sync {
        private boolean enabled;
    }

    @Data
    public static class Async {
        private boolean enabled;
    }

}
