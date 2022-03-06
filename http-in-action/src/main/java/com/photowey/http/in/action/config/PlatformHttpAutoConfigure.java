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
package com.photowey.http.in.action.config;

import com.photowey.http.in.action.TrustAnyHostnameVerifier;
import com.photowey.http.in.action.TrustAnyTrustManager;
import com.photowey.http.in.action.candidate.okhttp.OkHttpRequestExecutorImpl;
import com.photowey.http.in.action.executor.RequestExecutor;
import com.photowey.http.in.action.interceptor.DefaultRequestInterceptor;
import com.photowey.http.in.action.interceptor.RequestInterceptor;
import com.photowey.http.in.action.property.PlatformHttpProperties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/**
 * {@code PlatformHttpAutoConfigure}
 * 平台-{@code http} 自动配置
 *
 * @author weichangjun
 * @date 2022/02/28
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = {PlatformHttpProperties.class})
public class PlatformHttpAutoConfigure {

    @Autowired
    private PlatformHttpProperties httpProperties;

    /**
     * 重命名-避免和其他冲突
     *
     * @return {@link OkHttpClient}
     */
    @Bean("platformOkHttpClient")
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .sslSocketFactory(this.sslSocketFactory(), this.trustAnyTrustManager())
                .retryOnConnectionFailure(false)
                .connectionPool(this.okHttpConnectionPool())
                .connectTimeout(this.httpProperties.getOkHttp().getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(this.httpProperties.getOkHttp().getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(this.httpProperties.getOkHttp().getWriteTimeout(), TimeUnit.SECONDS)
                .hostnameVerifier(this.trustAnyHostnameVerifier())
                .build();
    }

    @Bean
    public X509TrustManager trustAnyTrustManager() {
        return new TrustAnyTrustManager();
    }

    @Bean
    public HostnameVerifier trustAnyHostnameVerifier() {
        return new TrustAnyHostnameVerifier();
    }

    @Bean
    @ConditionalOnMissingBean
    public SSLSocketFactory sslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{this.trustAnyTrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("retrieve the ssl context instance exception", e);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionPool okHttpConnectionPool() {
        return new ConnectionPool(this.httpProperties.getOkHttp().getMaxIdleConnections(),
                this.httpProperties.getOkHttp().getKeepAliveDuration(), TimeUnit.SECONDS);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "hicoo.http.okHttp", name = "enabled", havingValue = "true", matchIfMissing = true)
    public RequestExecutor requestExecutor() {
        return new OkHttpRequestExecutorImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestInterceptor requestInterceptor() {
        return new DefaultRequestInterceptor();
    }
}
