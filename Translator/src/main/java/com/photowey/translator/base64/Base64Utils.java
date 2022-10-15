package com.photowey.translator.base64;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

/**
 * {@code Base64Utils}
 *
 * @author photowey
 * @date 2022/10/16
 * @since 1.0.0
 */
public final class Base64Utils {

    private Base64Utils() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static String encrypt(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    public static String encrypt(String data) {
        return Base64.encodeBase64String(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String decryptBase64(String base64Str) {
        return new String(decrypt(base64Str), StandardCharsets.UTF_8);
    }

    public static byte[] decrypt(String base64) {
        return Base64.decodeBase64(base64.getBytes(StandardCharsets.UTF_8));
    }

}