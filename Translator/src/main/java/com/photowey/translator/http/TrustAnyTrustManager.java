package com.photowey.translator.http;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * {@code TrustAnyTrustManager}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public class TrustAnyTrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}