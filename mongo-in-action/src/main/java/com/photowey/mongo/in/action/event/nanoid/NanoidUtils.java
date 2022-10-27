package com.photowey.mongo.in.action.event.nanoid;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * {@code NanoidUtils}
 *
 * @author photowey
 * @date 2022/10/27
 * @since 1.0.0
 */
public final class NanoidUtils {

    /**
     * 没有采用原始的 {@code NANOID} 字母表
     * -> 是为了排除 _ 和 - 这两个字符
     */


    public static final char[] NANOID_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static final char[] NANOID_LOWER_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static final char[] NANOID_NUMBER_ALPHABET = "0123456789".toCharArray();

    public static final SecureRandom DEFAULT_NUMBER_GENERATOR = new SecureRandom();

    private NanoidUtils() {
        // utils class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    // ----------------------------------------------------------------------------------------------------------------

    public static String randomNanoId() {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, NANOID_ALPHABET, NanoIdUtils.DEFAULT_SIZE);
    }

    public static String randomNanoId(int size) {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, NANOID_ALPHABET, size);
    }

    public static String randomNanoId(char[] alphabet, int size) {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, alphabet, size);
    }

// ----------------------------------------------------------------------------------------------------------------

    public static String randomLowerNanoId() {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, NANOID_LOWER_ALPHABET, NanoIdUtils.DEFAULT_SIZE);
    }

    public static String randomLowerNanoId(int size) {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, NANOID_LOWER_ALPHABET, size);
    }

    public static String randomLowerNanoId(char[] alphabet, int size) {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, alphabet, size);
    }

// ----------------------------------------------------------------------------------------------------------------

    public static String randomNumberNanoId() {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, NANOID_NUMBER_ALPHABET, NanoIdUtils.DEFAULT_SIZE);
    }

    public static String randomNumberNanoId(int size) {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, NANOID_NUMBER_ALPHABET, size);
    }

    public static String randomNumberNanoId(char[] alphabet, int size) {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, alphabet, size);
    }

    // ----------------------------------------------------------------------------------------------------------------

    public static String randomNanoId(final Random random, final char[] alphabet, final int size) {
        return NanoIdUtils.randomNanoId(random, alphabet, size);
    }

}
