package com.photowey.translator.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * {@code TrustAnyHostnameVerifier}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public class TrustAnyHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}