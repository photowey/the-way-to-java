package com.photowey.translator.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * {@code Hash}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public class Hash {

    public static final String ALGORITHM_MD5 = "MD5";

    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
    private static final char[] CHAR_ARRAY = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static class MD5 {

        private MD5() {
            throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
        }

        public static String md5(String content) {
            return hash(ALGORITHM_MD5, content);
        }
    }

    public static String hash(String algorithm, String content) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(content.getBytes(StandardCharsets.UTF_8));
            return toHex(bytes);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
}
