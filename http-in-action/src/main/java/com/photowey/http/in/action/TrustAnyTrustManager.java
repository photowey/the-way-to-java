package com.photowey.http.in.action;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * {@code TrustAnyTrustManager}
 *
 * @author weichangjun
 * @date 2022/02/28
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