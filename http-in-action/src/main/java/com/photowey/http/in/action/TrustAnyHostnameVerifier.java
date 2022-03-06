package com.photowey.http.in.action;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * {@code TrustAnyHostnameVerifier}
 *
 * @author weichangjun
 * @date 2022/02/28
 * @since 1.0.0
 */
public class TrustAnyHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}