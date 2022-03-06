package com.photowey.http.in.action.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * {@code PlatformHttpProperties}
 *
 * @author weichangjun
 * @date 2022/03/01
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "request.http")
public class PlatformHttpProperties implements Serializable {

    private static final long serialVersionUID = -5453604230161791240L;

    private HttpClient httpClient = new HttpClient();
    private OkHttp okHttp = new OkHttp();

    /**
     * 单位 统一为秒(s)
     */

    /**
     * {@code ApacheHttpClient}
     */
    @Data
    public static class HttpClient implements Serializable {

        private static final long serialVersionUID = 7282662461850242912L;

        private boolean enabled = false;
        private int connectTimeout = 5;
        private int readTimeout = 30;
        private int writeTimeout = 30;
        private int maxIdleConnections = 200;
        private long keepAliveDuration = 300L;
    }

    @Data
    public static class OkHttp implements Serializable {

        private static final long serialVersionUID = 474081402674142730L;

        private boolean enabled = true;
        private int connectTimeout = 5;
        private int readTimeout = 30;
        private int writeTimeout = 30;
        private int maxIdleConnections = 200;
        private long keepAliveDuration = 300L;
    }
}
