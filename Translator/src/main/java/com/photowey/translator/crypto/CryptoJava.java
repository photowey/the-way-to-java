package com.photowey.translator.crypto;

import com.photowey.translator.base64.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * {@code CryptoJava}
 *
 * @author photowey
 * @date 2022/10/16
 * @since 1.0.0
 */
public final class CryptoJava {

    private final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private CryptoJava() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static final class AES implements Serializable {

        private static final String AES_ALGORITHM = "AES";
        private static final String AES_CBC_NO_PADDING = "AES/CBC/NoPadding";
        private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";

        private AES() {
            throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
        }

        public static final class NoPadding implements Serializable {

            private NoPadding() {
                throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
            }

            public static String encrypt(String rules, String context, String ivString) {
                return encrypt(rules, context, ivString, AES_CBC_NO_PADDING);
            }

            public static String decrypt(String rules, String encrypted, String ivString) {
                return decrypt(rules, encrypted, ivString, AES_CBC_NO_PADDING);
            }

            public static String encrypt(String secretKey, String context, String ivString, String cipherString) {
                byte[] iv = ivString.getBytes(StandardCharsets.UTF_8);
                try {
                    Cipher cipher = Cipher.getInstance(cipherString);
                    int blockSize = cipher.getBlockSize();
                    byte[] dataBytes = context.getBytes(StandardCharsets.UTF_8);
                    int length = dataBytes.length;
                    if (length % blockSize != 0) {
                        length = length + (blockSize - (length % blockSize));
                    }
                    byte[] plainText = new byte[length];
                    System.arraycopy(dataBytes, 0, plainText, 0, dataBytes.length);

                    SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
                    IvParameterSpec ivSpec = new IvParameterSpec(iv);
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
                    byte[] encrypted = cipher.doFinal(plainText);

                    return Base64Utils.encrypt(encrypted);

                } catch (Exception e) {
                    throw new SecurityException(String.format("handle aes:[%s] encrypt exception", cipherString), e);
                }
            }

            public static String decrypt(String secretKey, String encrypted, String ivString, String cipherString) {
                byte[] iv = ivString.getBytes(StandardCharsets.UTF_8);
                try {
                    byte[] dataBytes = Base64Utils.decrypt(encrypted);
                    Cipher cipher = Cipher.getInstance(cipherString);
                    SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
                    IvParameterSpec ivSpec = new IvParameterSpec(iv);
                    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
                    byte[] original = cipher.doFinal(dataBytes);

                    return new String(original, StandardCharsets.UTF_8).trim();
                } catch (Exception e) {
                    throw new SecurityException(String.format("handle aes:[%s] decrypt exception", cipherString), e);
                }
            }
        }

        public static final class PKCS5Padding implements Serializable {

            private static final int INIT_VECTOR_LENGTH = 16;

            public static String encrypt(String secretKey, String context) {
                return encrypt(secretKey, context, AES_CBC_PKCS5_PADDING);
            }

            public static String encrypt(String secretKey, String context, String cipherString) {
                if (!isKeyLengthValid(secretKey)) {
                    throw new RuntimeException("secret key's length must be 128, 192 or 256 bits");
                }
                try {
                    SecureRandom secureRandom = new SecureRandom();
                    byte[] iv = new byte[INIT_VECTOR_LENGTH / 2];
                    secureRandom.nextBytes(iv);
                    String initVector = bytesToHex(iv);
                    iv = initVector.getBytes(StandardCharsets.UTF_8);

                    IvParameterSpec ivSpec = new IvParameterSpec(iv);
                    SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);

                    Cipher cipher = Cipher.getInstance(cipherString);
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
                    byte[] plainText = context.getBytes(StandardCharsets.UTF_8);
                    byte[] encrypted = cipher.doFinal(plainText);

                    ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + encrypted.length);
                    byteBuffer.put(iv);
                    byteBuffer.put(encrypted);

                    return Base64Utils.encrypt(byteBuffer.array());
                } catch (Throwable e) {
                    throw new SecurityException(String.format("handle aes:[%s] encrypt exception", cipherString), e);
                }
            }

            public static String decrypt(String secretKey, String cipherText) {
                return decrypt(secretKey, cipherText, AES_CBC_PKCS5_PADDING);
            }

            public static String decrypt(String secretKey, String encrypted, String cipherString) {
                if (!isKeyLengthValid(secretKey)) {
                    throw new RuntimeException("secret key's length must be 128, 192 or 256 bits");
                }
                try {
                    byte[] cipherText = Base64Utils.decrypt(encrypted);

                    IvParameterSpec ivSpec = new IvParameterSpec(cipherText, 0, INIT_VECTOR_LENGTH);
                    SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);

                    Cipher cipher = Cipher.getInstance(cipherString);
                    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                    byte[] data = cipher.doFinal(cipherText, INIT_VECTOR_LENGTH, cipherText.length - INIT_VECTOR_LENGTH);

                    return new String(data, StandardCharsets.UTF_8);
                } catch (Throwable e) {
                    throw new SecurityException(String.format("handle aes:[%s] decrypt exception", cipherString), e);
                }
            }

            public static boolean isKeyLengthValid(String key) {
                return key.length() == 16 || key.length() == 24 || key.length() == 32;
            }
        }
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
