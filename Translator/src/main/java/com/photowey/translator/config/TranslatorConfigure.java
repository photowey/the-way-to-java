package com.photowey.translator.config;

import com.photowey.translator.handler.TranslateHandler;
import com.photowey.translator.handler.TranslateHandlerImpl;
import com.photowey.translator.http.RequestExecutor;
import com.photowey.translator.http.TrustAnyHostnameVerifier;
import com.photowey.translator.http.TrustAnyTrustManager;
import com.photowey.translator.http.okhttp.OkhttpRequestExecutorImpl;
import com.photowey.translator.property.TranslatorHttpProperties;
import com.photowey.translator.property.TranslatorProperties;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/**
 * {@code TranslatorConfigure}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public class TranslatorConfigure {

    private final TranslatorProperties translatorProperties;
    private final TranslatorHttpProperties translatorHttpProperties;
    private final RequestExecutor requestExecutor;
    private final TranslateHandler translateHandler;

    public TranslatorConfigure() {
        this.translatorProperties = new TranslatorProperties();
        this.translatorHttpProperties = new TranslatorHttpProperties();
        this.requestExecutor = new OkhttpRequestExecutorImpl(this.buildOkHttpClient());
        this.translateHandler = new TranslateHandlerImpl(this.requestExecutor, this.translatorHttpProperties);
    }

    public TranslatorProperties getTranslatorProperties() {
        return translatorProperties;
    }

    public TranslatorHttpProperties getTranslatorHttpProperties() {
        return translatorHttpProperties;
    }

    public RequestExecutor getRequestExecutor() {
        return requestExecutor;
    }

    public TranslateHandler getTranslateHandler() {
        return translateHandler;
    }

    private OkHttpClient buildOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder.sslSocketFactory(this.sslSocketFactory(), this.trustAnyTrustManager())
                .retryOnConnectionFailure(true)
                .connectionPool(this.okHttpConnectionPool())
                .connectTimeout(this.translatorHttpProperties.getOkHttp().getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(this.translatorHttpProperties.getOkHttp().getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(this.translatorHttpProperties.getOkHttp().getWriteTimeout(), TimeUnit.SECONDS)
                .hostnameVerifier(this.trustAnyHostnameVerifier())
                .build();
    }

    private X509TrustManager trustAnyTrustManager() {
        return new TrustAnyTrustManager();
    }

    private HostnameVerifier trustAnyHostnameVerifier() {
        return new TrustAnyHostnameVerifier();
    }

    private SSLSocketFactory sslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustAnyTrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("retrieve the ssl context instance exception", e);
        }
    }

    private ConnectionPool okHttpConnectionPool() {
        return new ConnectionPool(this.translatorHttpProperties.getOkHttp().getMaxIdleConnections(),
                this.translatorHttpProperties.getOkHttp().getKeepAliveDuration(), TimeUnit.SECONDS);
    }

}
