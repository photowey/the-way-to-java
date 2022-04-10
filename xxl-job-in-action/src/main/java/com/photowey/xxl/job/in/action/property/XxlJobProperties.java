package com.photowey.xxl.job.in.action.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code XxlJobProperties}
 *
 * @author photowey
 * @date 2022/04/11
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "xxl.job", ignoreUnknownFields = true)
public class XxlJobProperties {

    private String accessToken;
    private Admin admin = new Admin();
    private Executor executor = new Executor();

    @Data
    public static class Admin {
        private String addresses;
    }

    @Data
    public static class Executor {
        private String address;
        private String appName;
        private String ip;
        private String logPath;
        private Integer logRetentionDays = 30;
        private Integer port = 9999;
    }
}
